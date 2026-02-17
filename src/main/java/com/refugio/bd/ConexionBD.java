package com.refugio.bd;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBD {
    private static final String URL = "jdbc:mysql://localhost:3306/adopciones_db?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static final String USER = "refugio";
    private static final String PASSWORD = "refugio";

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException("Error al cargar el driver de MySQL: " + e.getMessage());
        }
    }

    public static Connection getConnection() throws SQLException {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            System.err.println("--- ERROR DE CONEXIÓN A BASE DE DATOS ---");
            System.err.println("URL: " + URL);
            System.err.println("Usuario: " + USER);
            System.err.println("Mensaje: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }
}
