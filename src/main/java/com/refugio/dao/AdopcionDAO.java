package com.refugio.dao;

import com.refugio.bd.ConexionBD;
import com.refugio.modelo.Adopcion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AdopcionDAO {

    public boolean registrarAdopcion(Adopcion adopcion) throws SQLException {
        String sqlInsert = "INSERT INTO adopciones (nombre_adoptante, telefono, email, id_mascota) VALUES (?, ?, ?, ?)";
        String sqlUpdate = "UPDATE mascotas SET adoptado = TRUE WHERE id_mascota = ?";

        Connection conn = null;
        PreparedStatement psInsert = null;
        PreparedStatement psUpdate = null;

        try {
            conn = ConexionBD.getConnection();
            // Iniciar transacción
            conn.setAutoCommit(false);

            // 1. Registrar Adopción
            psInsert = conn.prepareStatement(sqlInsert);
            psInsert.setString(1, adopcion.getNombreAdoptante());
            psInsert.setString(2, adopcion.getTelefono());
            psInsert.setString(3, adopcion.getEmail());
            psInsert.setInt(4, adopcion.getIdMascota());
            int filasAdopcion = psInsert.executeUpdate();

            if (filasAdopcion == 0) {
                conn.rollback();
                return false;
            }

            // 2. Actualizar estado de la mascota
            psUpdate = conn.prepareStatement(sqlUpdate);
            psUpdate.setInt(1, adopcion.getIdMascota());
            int filasMascota = psUpdate.executeUpdate();

            if (filasMascota == 0) {
                conn.rollback(); // Mascota no existe o error
                return false;
            }

            // Confirmar transacción
            conn.commit();
            return true;

        } catch (SQLException e) {
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            throw e;
        } finally {
            if (psInsert != null)
                psInsert.close();
            if (psUpdate != null)
                psUpdate.close();
            if (conn != null) {
                conn.setAutoCommit(true); // Restaurar autocommit
                conn.close();
            }
        }
    }

    public java.util.List<java.util.Map<String, Object>> listarAdopciones() throws SQLException {
        String sql = "SELECT a.id_adopcion, a.nombre_adoptante, a.telefono, a.email, a.fecha_adopcion, " +
                "m.id_mascota, m.nombre, m.edad, m.raza, m.descripcion, m.id_especie " +
                "FROM adopciones a " +
                "INNER JOIN mascotas m ON a.id_mascota = m.id_mascota " +
                "ORDER BY a.fecha_adopcion DESC";

        java.util.List<java.util.Map<String, Object>> lista = new java.util.ArrayList<>();

        try (Connection conn = ConexionBD.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                java.sql.ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                java.util.Map<String, Object> adopcion = new java.util.HashMap<>();
                adopcion.put("id_adopcion", rs.getInt("id_adopcion"));
                adopcion.put("nombre_adoptante", rs.getString("nombre_adoptante"));
                adopcion.put("telefono", rs.getString("telefono"));
                adopcion.put("email", rs.getString("email"));
                adopcion.put("fecha_adopcion", rs.getTimestamp("fecha_adopcion").toString());
                adopcion.put("id_mascota", rs.getInt("id_mascota"));
                adopcion.put("nombre_mascota", rs.getString("nombre"));
                adopcion.put("edad", rs.getInt("edad"));
                adopcion.put("raza", rs.getString("raza"));
                adopcion.put("descripcion", rs.getString("descripcion"));
                adopcion.put("id_especie", rs.getInt("id_especie"));
                lista.add(adopcion);
            }
        }

        return lista;
    }
}
