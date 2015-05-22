package com.fidelizacion;

/**
 * Created by Admin on 11/05/2015.
 */
public class Tarjeta {
    private String nombre;
    private int puntos,puntosporeuro,puntosporventa;

    public Tarjeta(String nombre, int puntos,int puntosporeuro,int puntosporventa) {
        this.nombre = nombre;
        this.puntos = puntos;
        this.puntosporeuro=puntosporeuro;
        this.puntosporventa=puntosporventa;
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

    public int getPpe() {
        return puntosporeuro;
    }

    public void setPpe(int ppe) {
        this.puntosporeuro = ppe;
    }

    public int getPpv() {
        return puntosporventa;
    }

    public void setPpv(int ppv) {
        this.puntosporventa = ppv;
    }

    public String formato(String uid) {
        return "{\"uid\":\""+uid+"\"}";
    }

    public String formatoPrecio(String uid) {
        return "{\"uid\":\""+uid+"\"" +
                "{\"ppe\":\"" + puntosporeuro + "\"," +
                "\"ppv\":\"" + puntosporventa + "\"}";
    }

}
