package com.refugio.modelo;

public class Mascota {
    private int id_mascota;
    private String nombre;
    private int edad;
    private String raza;
    private String descripcion;
    private int id_especie;
    private boolean adoptado;

    // Constructores
    public Mascota() {
    }

    public Mascota(int id_mascota, String nombre, int edad, String raza, String descripcion, int id_especie,
            boolean adoptado) {
        this.id_mascota = id_mascota;
        this.nombre = nombre;
        this.edad = edad;
        this.raza = raza;
        this.descripcion = descripcion;
        this.id_especie = id_especie;
        this.adoptado = adoptado;
    }

    // Getters y Setters
    public int getIdMascota() {
        return id_mascota;
    }

    public void setIdMascota(int id_mascota) {
        this.id_mascota = id_mascota;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public String getRaza() {
        return raza;
    }

    public void setRaza(String raza) {
        this.raza = raza;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getIdEspecie() {
        return id_especie;
    }

    public void setIdEspecie(int id_especie) {
        this.id_especie = id_especie;
    }

    public boolean isAdoptado() {
        return adoptado;
    }

    public void setAdoptado(boolean adoptado) {
        this.adoptado = adoptado;
    }
}
