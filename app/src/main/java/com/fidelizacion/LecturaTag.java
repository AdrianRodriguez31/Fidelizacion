package com.fidelizacion;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class LecturaTag extends Activity implements ComunicadorGestorDb {

    private Intent intent;
    private int ppv=0,ppe=0;
    private String modoCanjeo="Por Precio",uid="",tipoTransaccion="suma";
    private SharedPreferences settings;
    private TextView tvUsuario,tvPuntos,tvTipo;
    private EditText etPoint;
    public static final String PREFS_NAME = "PreferencesFile";
    public static String usuario="Usuario",puntos="Puntos";
    private String user,pass;
    SharedPreferences.OnSharedPreferenceChangeListener listener;
    private String IP_Server="192.168.1.56";
    private GestorDb gestor;
    private Tarjeta card;
    private ProgressDialog pDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        user=getResources().getString(R.string.user);
        pass=String.valueOf(R.string.pass);
        settings = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        modoCanjeo = settings.getString("modoCanjeo", "Por Precio");
        ppv = settings.getInt("ppv", 1);
        ppe = settings.getInt("ppe",1);
        listener=new SharedPreferences.OnSharedPreferenceChangeListener() {
            @Override
            public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {

                modoCanjeo = settings.getString("modoCanjeo", "Por Precio");
                ppv = settings.getInt("ppv", 1);
                ppe = settings.getInt("ppe",1);
                actualizaPrecios(ppe,ppv);
                asignarModo(modoCanjeo);

            }
        };
        settings.registerOnSharedPreferenceChangeListener(listener);
        if (getIntent().getAction().equals(NfcAdapter.ACTION_NDEF_DISCOVERED)) {
            uid = ByteArrayToHexString(getIntent().getByteArrayExtra(NfcAdapter.EXTRA_ID));
            if (uid != "") {
                pDialog = new ProgressDialog(this);
                pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                pDialog.setMessage("Actualizando Base de Datos...");
                pDialog.setCancelable(false);
                pDialog.setMax(100);
                gestor = new GestorDb(IP_Server, uid, getBaseContext(), pDialog);
                    setContentView(R.layout.activity_lectura_tag);
                    tvUsuario = (TextView) findViewById(R.id.tvUsuario);
                    tvPuntos = (TextView) findViewById(R.id.tvPuntos);
                    tvTipo = (TextView) findViewById(R.id.tvTipo);
                    etPoint= (EditText)findViewById(R.id.etPoint);
                actualizarDatos();
                //uid: 04F0A402272680
            } else {
                Toast.makeText(getApplicationContext(), "Tarjeta inexistente en base de datos ", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        } else {
            Toast.makeText(getApplicationContext(), "Tarjeta no legible", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }

    }

//Métodos para conectar con el servidor y actualizar info:

    public void actualizarDatos(){
        gestor.getTarjeta(this,user,pass);
    }

    @Override
    public void respuestaLecturaTag(Tarjeta t) {
        this.card = t;
        if(card!=null)
        {
            usuario=card.getNombre();
            tvUsuario.setText(usuario);
            puntos=String.valueOf(card.getPuntos());
            tvPuntos.setText(puntos);

            asignarModo(modoCanjeo);
        }
        else {
            asignarModo("");
        }
    }
    private void actualizaPrecios(int tppe,int tppv){
        ppe=tppe;
        ppv=tppv;
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt("ppe",tppe);
        editor.putInt("ppv",tppv);
        editor.commit();

        gestor.actualizaPreferencias(this,ppe,ppv,user,pass);
    }
    @Override
    public void actualizaPuntos(){
        etPoint.setText("");
        actualizarDatos();
    }

    private void asignarModo(String modo){

        etPoint.setText("");
        if(modo.equals("Por Precio"))
        {
            tvTipo.setText("Introduce Precio");
        }
        else if(modo.equals("Por Puntos"))
        {
            tvTipo.setText("Introduce Puntos");
        }
        else if(modo.equals("Por Ventas")) {
            tvTipo.setText("Introduce Ventas");
        }
        else
        {
            Toast.makeText(getApplicationContext(), "Error en lectura de datos ", Toast.LENGTH_LONG).show();
            Intent intent=new Intent(this,MainActivity.class);
            startActivity(intent);
            finish();
        }
    }
// Métodos de ejecución de Eventos:

    public void sumarPuntos(View v){
        tipoTransaccion="suma";
        if(etPoint.getText().toString().equals(""))
        {Toast.makeText(this,"Introduzca valor para Sumar",Toast.LENGTH_SHORT).show();}
        else
        {
            if (modoCanjeo.equals("Por Precio")) {
                int r = redondearPrecio(Double.valueOf(etPoint.getText().toString()));
                int p = r * ppe;
                crearTransaccion(p);
            } else if (modoCanjeo.equals("Por Puntos")) {
                int p = Integer.valueOf(etPoint.getText().toString());
                crearTransaccion(p);
            } else if (modoCanjeo.equals("Por Ventas")) {
                int p = Integer.valueOf(etPoint.getText().toString()) * ppv;
                crearTransaccion(p);
            }
        }
    }
    public void canjearPuntos(View v){
        tipoTransaccion="canjeo";
        if(etPoint.getText().toString().equals(""))
        {Toast.makeText(this,"Introduzca valor para Canjear",Toast.LENGTH_SHORT).show();}
        else
        {
            if (modoCanjeo.equals("Por Precio")) {
                int r = redondearPrecio(Double.valueOf(etPoint.getText().toString()));
                if (Integer.valueOf(puntos) < (r * ppe)) {
                    Toast.makeText(this, "No tiene puntos suficientes, " + r, Toast.LENGTH_LONG).show();
                } else {

                    int p = - (r * ppe);
                    crearTransaccion(p);
                }
            } else if (modoCanjeo.equals("Por Puntos")) {
                if (Integer.valueOf(puntos) < Integer.valueOf(etPoint.getText().toString())) {
                    Toast.makeText(this, "No tiene puntos suficientes", Toast.LENGTH_LONG).show();
                } else {
                    int p = - Integer.valueOf(etPoint.getText().toString());
                    crearTransaccion(p);
                }
            } else if (modoCanjeo.equals("Por Ventas")) {
                if (Integer.valueOf(puntos) < (Integer.valueOf(etPoint.getText().toString()) * ppv)) {
                    Toast.makeText(this, "No tiene puntos suficientes", Toast.LENGTH_LONG).show();
                } else {
                    int p = - (Integer.valueOf(etPoint.getText().toString()) * ppv);
                    crearTransaccion(p);
                }
            }
        }
    }
    public void crearTransaccion(int punt){
        Transaccion tran= new Transaccion(punt,uid,modoCanjeo);
        gestor.insertarTransaccion(this, tran);
    }

// Método de Configuración de la Aplicación:

    public void abrirPreferencias(View v){
        Intent intent=new Intent(this,Preferencias.class);
        intent.putExtra("modoCanjeo", modoCanjeo);
        startActivity(intent);
    }
// Métodos de cálculos matemáticos:

    private String ByteArrayToHexString(byte [] inarray) {
        int i, j, in;
        String [] hex = {"0","1","2","3","4","5","6","7","8","9","A","B","C","D","E","F"};
        String out= "";

        for(j = 0 ; j < inarray.length ; ++j)
        {
            in = (int) inarray[j] & 0xff;
            i = (in >> 4) & 0x0f;
            out += hex[i];
            i = in & 0x0f;
            out += hex[i];
        }
        return out;
    }
    private int redondearPrecio(Double pre){
        int p=0;
        Double x=Math.floor(pre);
        return Double.valueOf(x).intValue();
    }

}
