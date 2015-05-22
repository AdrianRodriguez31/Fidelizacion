package com.fidelizacion;

/**
 * Created by Admin on 11/05/2015.
 */
public class Tarjeta {
    private String nombre;
    private int puntos;

    public Tarjeta(String nombre, int puntos) {
        this.nombre = nombre;
        this.puntos = puntos;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getPuntos() {
        return puntos;
    }

    public void setPuntos(int puntos) {
        this.puntos = puntos;
    }


    public String formato(String uid) {
        return "{\"uid\":\""+uid+"\"}";
    }
}
