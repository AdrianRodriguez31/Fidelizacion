package com.fidelizacion;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;


/**
 * Created by Admin on 13/05/2015.
 */
public class GestorDb {
    private ComunicadorGestorDb lectura;
    private Actualiza_Puntos tarea;
    private ProgressDialog pDialog;
    private String IP_Server,uid,URL_connect;
    Context context;
    private Tarjeta tarjeta;
    public GestorDb(String server,String uid,Context context,ProgressDialog pDialog) {
        this.IP_Server=server;
        this.uid=uid;
        this.context=context;
        this.pDialog=pDialog;
    }

    class Actualiza_Puntos extends AsyncTask< Object, String, String > {
        protected void onPreExecute() {

            pDialog.setProgress(0);
            pDialog.show();
        }
        protected String doInBackground(Object... params) {
            Log.e("param",params[0].toString());
            if(params[0].toString().equals("Consulta.php"))
            {
                try {
                    URL_connect="http://"+IP_Server+"/Fidelizacion/"+params[0]+"?ideTarjeta="+URLEncoder.encode( ((Tarjeta) params[1]).toString(uid), "UTF-8");

                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
            else if(params[0].toString().equals("InsertarTransaccion.php"))
            {
                try {
                   URL_connect= "http://" + IP_Server + "/Fidelizacion/" + params[0] + "?datos=" + URLEncoder.encode( ((Transaccion) params[1]).toString(uid), "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
            OkHttpClient client = new OkHttpClient();
            Request request = null;
            Response response = null;
            request = new Request.Builder().url(URL_connect).build();
            try {
                response = client.newCall(request).execute();
            } catch (Exception e) {
                //e.printStackTrace();
                Log.e("excepcion response", e.toString());
            }
            try {
                String json = response.body().string();
                return json;
            } catch (Exception e) {
                //e.printStackTrace();
                Log.e("excepcion json",e.toString());
            }
            return "";
        }
        protected void onPostExecute(String result) {
            pDialog.dismiss();
            if(result!="")
            {
                GsonBuilder builder = new GsonBuilder();
                Gson gson = builder.create();

                JsonElement je = new JsonParser().parse(result);
                if(je.getAsJsonObject().get("error").getAsString().equals("false"))
                {
                    String value = je.getAsJsonObject().get("datos").toString();
                    tarjeta = gson.fromJson(value, Tarjeta.class);
                    lectura.respuestaLecturaTag(tarjeta);
                }
                else
                    Toast.makeText(context, je.getAsJsonObject().get("error").toString(), Toast.LENGTH_LONG).show();
            }
            else
            {
                Toast.makeText(context, "Gson Fallido ", Toast.LENGTH_LONG).show();}
        }
    }
    public void getTarjeta(ComunicadorGestorDb lectura){
        this.lectura = lectura;
        tarea=new Actualiza_Puntos();
        tarea.execute("Consulta.php",new Tarjeta(uid,0));
    }

    public void insertarTransaccion(ComunicadorGestorDb lectura,Transaccion tr){
        this.lectura = lectura;
        tarea=new Actualiza_Puntos();
        tarea.execute("InsertarTransaccion.php",tr);
    }




}
