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
}
