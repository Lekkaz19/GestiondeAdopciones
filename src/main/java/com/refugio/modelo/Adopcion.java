package com.refugio.modelo;

import java.sql.Timestamp;

public class Adopcion {
    private int id_adopcion;
    private String nombre_adoptante;
    private String telefono;
    private String email;
    private Timestamp fecha_adopcion;
    private int id_mascota;

    // Constructores
    public Adopcion() {
    }

    public Adopcion(String nombre_adoptante, String telefono, String email, int id_mascota) {
        this.nombre_adoptante = nombre_adoptante;
        this.telefono = telefono;
        this.email = email;
        this.id_mascota = id_mascota;
    }

    public Adopcion(int id_adopcion, String nombre_adoptante, String telefono, String email, Timestamp fecha_adopcion,
            int id_mascota) {
        this.id_adopcion = id_adopcion;
        this.nombre_adoptante = nombre_adoptante;
        this.telefono = telefono;
        this.email = email;
        this.fecha_adopcion = fecha_adopcion;
        this.id_mascota = id_mascota;
    }

    // Getters y Setters
    public int getIdAdopcion() {
        return id_adopcion;
    }

    public void setIdAdopcion(int id_adopcion) {
        this.id_adopcion = id_adopcion;
    }

    public String getNombreAdoptante() {
        return nombre_adoptante;
    }

    public void setNombreAdoptante(String nombre_adoptante) {
        this.nombre_adoptante = nombre_adoptante;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Timestamp getFechaAdopcion() {
        return fecha_adopcion;
    }

    public void setFechaAdopcion(Timestamp fecha_adopcion) {
        this.fecha_adopcion = fecha_adopcion;
    }

    public int getIdMascota() {
        return id_mascota;
    }

    public void setIdMascota(int id_mascota) {
        this.id_mascota = id_mascota;
    }
}
