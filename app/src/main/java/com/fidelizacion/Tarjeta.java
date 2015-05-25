package com.fidelizacion;

/**
 * Created by Admin on 11/05/2015.
 */
public class Tarjeta {
    private String nombre,user,pass;
    private int puntos,puntosporeuro,puntosporventa;

    public Tarjeta(String nombre, int puntos,int puntosporeuro,int puntosporventa,String user,String pass) {
        this.nombre = nombre;
        this.puntos = puntos;
        this.puntosporeuro=puntosporeuro;
        this.puntosporventa=puntosporventa;
        this.user=user;
        this.pass=pass;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public int getPuntosporeuro() {
        return puntosporeuro;
    }

    public void setPuntosporeuro(int puntosporeuro) {
        this.puntosporeuro = puntosporeuro;
    }

    public int getPuntosporventa() {
        return puntosporventa;
    }

    public void setPuntosporventa(int puntosporventa) {
        this.puntosporventa = puntosporventa;
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
        return "{\"uid\":\""+uid+"\"," +
                "\"user\":\"" + user + "\"," +
                "\"ppv\":\"" + pass + "\"}";
    }

    public String formatoPrecio(String uid) {
        return "{\"uid\":\""+uid+"\"," +
                "\"ppe\":\"" + puntosporeuro + "\"," +
                "\"ppv\":\"" + puntosporventa + "\"}";
    }

}
