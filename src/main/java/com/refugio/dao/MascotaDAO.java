package com.refugio.dao;

import com.refugio.bd.ConexionBD;
import com.refugio.modelo.Mascota;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MascotaDAO {

    public List<Mascota> listarNoAdoptadas() throws SQLException {
        List<Mascota> lista = new ArrayList<>();
        String sql = "SELECT * FROM mascotas WHERE adoptado = FALSE";

        try (Connection conn = ConexionBD.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Mascota m = new Mascota();
                m.setIdMascota(rs.getInt("id_mascota"));
                m.setNombre(rs.getString("nombre"));
                m.setEdad(rs.getInt("edad"));
                m.setRaza(rs.getString("raza"));
                m.setDescripcion(rs.getString("descripcion"));
                m.setIdEspecie(rs.getInt("id_especie"));
                m.setAdoptado(rs.getBoolean("adoptado"));
                lista.add(m);
            }
        } catch (SQLException e) {
            System.err.println("Error SQL en listarNoAdoptadas: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
        return lista;
    }

    public boolean registrar(Mascota mascota) throws SQLException {
        String sql = "INSERT INTO mascotas (nombre, edad, raza, descripcion, id_especie, adoptado) VALUES (?, ?, ?, ?, ?, FALSE)";

        try (Connection conn = ConexionBD.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, mascota.getNombre());
            ps.setInt(2, mascota.getEdad());
            ps.setString(3, mascota.getRaza());
            ps.setString(4, mascota.getDescripcion());
            ps.setInt(5, mascota.getIdEspecie());

            return ps.executeUpdate() > 0;
        }
    }
}
