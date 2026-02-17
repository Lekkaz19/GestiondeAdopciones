package com.refugio.controlador;

import com.google.gson.Gson;
import com.refugio.dao.AdopcionDAO;
import com.refugio.modelo.Adopcion;
import java.io.BufferedReader;
import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/api/adopciones")
public class AdopcionServlet extends HttpServlet {

    private AdopcionDAO adopcionDAO;
    private Gson gson;

    @Override
    public void init() {
        adopcionDAO = new AdopcionDAO();
        gson = new Gson();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        try {
            BufferedReader reader = req.getReader();
            Adopcion nuevaAdopcion = gson.fromJson(reader, Adopcion.class);

            if (nuevaAdopcion.getIdMascota() <= 0 || nuevaAdopcion.getNombreAdoptante() == null
                    || nuevaAdopcion.getTelefono() == null) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                resp.getWriter().write(
                        "{\"error\": \"Datos inválidos: id_mascota, nombre_adoptante y telefono son obligatorios\"}");
                return;
            }

            boolean exito = adopcionDAO.registrarAdopcion(nuevaAdopcion);

            if (exito) {
                resp.setStatus(HttpServletResponse.SC_CREATED);
                resp.getWriter().write("{\"mensaje\": \"Adopción registrada con éxito\"}");
            } else {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST); // Probablemente la mascota no existe o ya está
                                                                    // adoptada
                resp.getWriter().write(
                        "{\"error\": \"No se pudo procesar la adopción. Verifique que la mascota existe y no estara adoptada.\"}");
            }
        } catch (SQLException e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write("{\"error\": \"Error en la base de datos\"}");
            e.printStackTrace();
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("{\"error\": \"JSON mal formado\"}");
        }
    }
}
