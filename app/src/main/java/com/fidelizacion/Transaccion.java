package com.fidelizacion;

import java.util.Date;

/**
 * Created by Admin on 15/05/2015.
 */
public class Transaccion {
    int puntos,tipo;
    String tipoPuntos,idTarjeta;
    public Transaccion(int puntos, String idTarjeta, String tipoPuntos) {
        this.puntos = puntos;
        this.idTarjeta = idTarjeta;
        this.tipoPuntos = tipoPuntos;
        tipo=1;
    }

    public int getPuntos() {
        return puntos;
    }

    public void setPuntos(int puntos) {
        this.puntos = puntos;
    }

    public String getTipoPuntos() {
        return tipoPuntos;
    }

    public void setTipoPuntos(String tipoPuntos) {
        this.tipoPuntos = tipoPuntos;
    }

    public String getIdTarjeta() {
        return idTarjeta;
    }

    public void setIdTarjeta(String idTarjeta) {
        this.idTarjeta = idTarjeta;
    }

    public String formato(String uid) {
        idTarjeta=uid;
        String sql="";
        if(tipoPuntos.equals("Por Puntos"))
            tipo=1;
        else if(tipoPuntos.equals("Por Precio"))
            tipo=2;
        if(tipoPuntos.equals("Por Ventas"))
            tipo=3;
        sql= "{\"puntos\":\"" + puntos + "\"," +
                "\"idTarjeta\":\"" + idTarjeta + "\"," +
                "\"tipoPuntos\":\"" + tipo + "\"}";
        return sql;
    }
}
