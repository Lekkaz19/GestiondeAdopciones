package com.refugio.controlador;

import com.google.gson.Gson;
import com.refugio.dao.MascotaDAO;
import com.refugio.modelo.Mascota;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/api/mascotas")
public class MascotaServlet extends HttpServlet {

    private MascotaDAO mascotaDAO;
    private Gson gson;

    @Override
    public void init() {
        mascotaDAO = new MascotaDAO();
        gson = new Gson();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        try {
            List<Mascota> mascotas = mascotaDAO.listarNoAdoptadas();
            String json = gson.toJson(mascotas);
            PrintWriter out = resp.getWriter();
            out.print(json);
            out.flush();
        } catch (SQLException e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write("{\"error\": \"Error al listar mascotas\"}");
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        try {
            BufferedReader reader = req.getReader();
            Mascota nuevaMascota = gson.fromJson(reader, Mascota.class);

            if (nuevaMascota.getNombre() == null || nuevaMascota.getIdEspecie() <= 0) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                resp.getWriter().write("{\"error\": \"Datos inválidos\"}");
                return;
            }

            boolean registrado = mascotaDAO.registrar(nuevaMascota);
            if (registrado) {
                resp.setStatus(HttpServletResponse.SC_CREATED);
                resp.getWriter().write("{\"mensaje\": \"Mascota registrada exitosamente\"}");
            } else {
                resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                resp.getWriter().write("{\"error\": \"No se pudo registrar la mascota\"}");
            }
        } catch (SQLException e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write("{\"error\": \"Error de base de datos\"}");
            e.printStackTrace();
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("{\"error\": \"JSON mal formado\"}");
        }
    }
}
