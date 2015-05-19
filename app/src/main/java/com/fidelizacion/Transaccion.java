package com.fidelizacion;

import java.util.Date;

/**
 * Created by Admin on 15/05/2015.
 */
public class Transaccion {
    int idTransaccion,puntos,ppe,ppv;
    String tipoPuntos,idTarjeta,fecha,tipoTransaccion;
    float precio;

    public Transaccion(int idTransaccion, String tipoTransaccion,String fecha, int puntos, String tipoPuntos, float precio, int ppe, int ppv, String idTarjeta) {
        this.idTransaccion = idTransaccion;
        this.tipoTransaccion=tipoTransaccion;
        this.puntos = puntos;
        this.ppe = ppe;
        this.ppv = ppv;
        this.tipoPuntos = tipoPuntos;
        this.idTarjeta = idTarjeta;
        this.precio = precio;
        this.fecha = fecha;
    }

    public int getIdTransaccion() {
        return idTransaccion;
    }

    public void setIdTransaccion(int idTransaccion) {
        this.idTransaccion = idTransaccion;
    }
    public String getTipoTransaccion() {
        return tipoTransaccion;
    }

    public void setTipoTransaccion(String tipoTransaccion) {
        this.tipoTransaccion = tipoTransaccion;
    }

    public int getPuntos() {
        return puntos;
    }

    public void setPuntos(int puntos) {
        this.puntos = puntos;
    }

    public int getPpe() {
        return ppe;
    }

    public void setPpe(int ppe) {
        this.ppe = ppe;
    }

    public int getPpv() {
        return ppv;
    }

    public void setPpv(int ppv) {
        this.ppv = ppv;
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

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public float getPrecio() {
        return precio;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }

    public String toString(String uid) {
        String sql="";
        if(tipoPuntos.equals("Por Puntos")) {
            sql= "{\"idTransaccion\":\"" + idTransaccion + "\"," +
                    "\"tipoTransaccion\":\"" + tipoTransaccion + "\"," +
                    "\"fecha\":\"" + fecha + "\"," +
                    "\"puntos\":\"" + puntos + "\"," +
                    "\"tipoPuntos\":\"" + tipoPuntos + "\"," +
                    "\"precio\":\"" + precio + "\"," +
                    "\"ppe\":\"null\"," +
                    "\"ppv\":\"null\"," +
                    "\"idTarjeta\":\"" + uid + "\"}";
        }
        else if(tipoPuntos.equals("Por Precio")) {
            sql= "{\"idTransaccion\":\"" + idTransaccion + "\"," +
                    "\"tipoTransaccion\":\"" + tipoTransaccion + "\"," +
                    "\"fecha\":\"" + fecha + "\"," +
                    "\"puntos\":\"" + puntos + "\"," +
                    "\"tipoPuntos\":\"" + tipoPuntos + "\"," +
                    "\"precio\":\"" + precio + "\"," +
                    "\"ppe\":\"" + ppe + "\"," +
                    "\"ppv\":\"null\"," +
                    "\"idTarjeta\":\"" + uid + "\"}";
        }
        else {
            sql= "{\"idTransaccion\":\"" + idTransaccion + "\"," +
                    "\"tipoTransaccion\":\"" + tipoTransaccion + "\"," +
                    "\"fecha\":\"" + fecha + "\"," +
                    "\"puntos\":\"" + puntos + "\"," +
                    "\"tipoPuntos\":\"" + tipoPuntos + "\"," +
                    "\"precio\":\"" + precio + "\"," +
                    "\"ppe\":\"null\"," +
                    "\"ppv\":\"" + ppv + "\"," +
                    "\"idTarjeta\":\"" + uid + "\"}";
        }

        return sql;
    }
}
