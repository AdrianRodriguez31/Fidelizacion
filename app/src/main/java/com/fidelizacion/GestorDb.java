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
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

/**
 * Created by Admin on 13/05/2015.
 */
public class GestorDb {
    private ComunicadorGestorDb lectura;
    private Actualiza_Puntos tarea;
    private ProgressDialog pDialog;
    private String IP_Server,uid,URL_connect="";
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
            String valor="";
            String x="";
            if(params[0].toString().equals("Consulta.php")) {
                Tarjeta t=(Tarjeta)params[1];
                x=t.formato(uid);
            }
            else if(params[0].toString().equals("InsertarTransaccion.php"))
            {
                Transaccion transac=(Transaccion) params[1];
                x=transac.formato(uid);
            }
            else if(params[0].toString().equals("ActualizaPreferencias.php"))
            {
                Tarjeta t=(Tarjeta)params[1];
                x=t.formatoPrecio(uid);
            }

            Crypto cr = null;

            try {
                cr=new Crypto("encriptator12345");
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                valor=cr.encrypt(x);

            } catch (Exception e) {
                e.printStackTrace();
            }
            URL_connect = "http://" + IP_Server + "/Fidelizacion/" + params[0];

            OkHttpClient client = new OkHttpClient();
            Request request = null;
            RequestBody body=null;
            Response response = null;
            body= new FormEncodingBuilder().add("datos", valor).build();
            request = new Request.Builder().url(URL_connect).post(body).build();

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
            Log.e("url",URL_connect);
            Decrypto desc=null;
            String desenc="";
            try {
                desc=new Decrypto("encriptator12345");
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                desenc=desc.decrypt(result);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if(desenc!="")
            {
                GsonBuilder builder = new GsonBuilder();
                Gson gson = builder.create();

                JsonElement je = new JsonParser().parse(desenc);
                if(je.getAsJsonObject().get("error").getAsString().equals("false_cons"))
                {
                    String value = je.getAsJsonObject().get("datos").toString();
                    tarjeta = gson.fromJson(value, Tarjeta.class);
                    lectura.respuestaLecturaTag(tarjeta);
                }
                else if(je.getAsJsonObject().get("error").getAsString().equals("false_trans"))
                {
                    Toast.makeText(context,je.getAsJsonObject().get("datos").toString(), Toast.LENGTH_LONG).show();
                    lectura.actualizaPuntos();
                }
                else if(je.getAsJsonObject().get("error").getAsString().equals("false_pref"))
                {
                    Toast.makeText(context,je.getAsJsonObject().get("datos").toString(), Toast.LENGTH_LONG).show();

                }
                else
                    Toast.makeText(context, je.getAsJsonObject().get("error").toString(), Toast.LENGTH_LONG).show();
            }
            else
            {
                Toast.makeText(context, "Error al obtener datos del servidor ", Toast.LENGTH_LONG).show();}
        }
    }
    public void getTarjeta(ComunicadorGestorDb lectura){
        this.lectura = lectura;
        tarea=new Actualiza_Puntos();
        tarea.execute("Consulta.php",new Tarjeta(uid,0,0,0));
    }

    public void insertarTransaccion(ComunicadorGestorDb lectura,Transaccion tr){
        this.lectura = lectura;
        tarea=new Actualiza_Puntos();
        tarea.execute("InsertarTransaccion.php", tr);
    }

    public void actualizaPreferencias(ComunicadorGestorDb lectura,int ppe,int ppv){
        this.lectura = lectura;
        tarea=new Actualiza_Puntos();
        tarea.execute("ActualizaPreferencias.php", new Tarjeta(uid,0,ppe,ppv),ppe,ppv);
    }

}
