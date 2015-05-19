package com.fidelizacion;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;


public class Preferencias extends Activity {


    public static RadioButton pPrecio,pPuntos,pVentas;
    public static EditText etPuntosV,etPuntosE;
    private Intent intent;
    private int ppv=0,ppe=0;
    private String modoCanjeo;
    private SharedPreferences settings;
    private Bundle extras;
    public static final String PREFS_NAME = "PreferencesFile";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferencias);
        pPrecio=(RadioButton)findViewById(R.id.radioButton);
        pPuntos=(RadioButton)findViewById(R.id.radioButton2);
        pVentas=(RadioButton)findViewById(R.id.radioButton3);
        etPuntosV=(EditText)findViewById(R.id.etPuntosVenta);
        etPuntosE=(EditText)findViewById(R.id.etPuntosEuro);
        settings = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        extras=getIntent().getExtras();
        modoCanjeo=extras.getString("modoCanjeo");
        etPuntosV.setText(String.valueOf(settings.getInt("ppv", 1)));
        etPuntosV.setEnabled(false);
        etPuntosE.setText(String.valueOf(settings.getInt("ppe", 1)));
        etPuntosE.setEnabled(false);
        if(modoCanjeo.equals("Por Precio")) {
            pPrecio.setChecked(true);
            etPuntosE.setEnabled(true);
        }
        else if(modoCanjeo.equals("Por Puntos"))
            pPuntos.setChecked(true);
        else if(modoCanjeo.equals("Por Ventas"))
        {   pVentas.setChecked(true);
            etPuntosV.setEnabled(true);
        }
    }

    @Override
    public void onBackPressed() {
        SharedPreferences.Editor editor = settings.edit();
        if(pPrecio.isChecked()) {
            editor.putString("modoCanjeo", "Por Precio");
            if(etPuntosE.getText().toString().equals(""))
                {editor.putInt("ppe",1);}
            else
                {editor.putInt("ppe",Integer.valueOf(etPuntosE.getText().toString()));}
        }
        else if(pPuntos.isChecked()) {
            editor.putString("modoCanjeo", "Por Puntos");
        }
        else if(pVentas.isChecked())
        {
            editor.putString("modoCanjeo", "Por Ventas");
            if(etPuntosV.getText().toString().equals(""))
                {editor.putInt("ppv",1);}
            else
                {editor.putInt("ppv",Integer.valueOf(etPuntosV.getText().toString()));}
        }
        // Commit the edits!
        editor.commit();
        finish();
    }
    public void setRadioB(View v){
        etPuntosV.setEnabled(false);
        etPuntosE.setEnabled(false);
        if(v.equals(pVentas))
        {
            etPuntosV.setEnabled(true);
            etPuntosV.setText(String.valueOf(settings.getInt("ppv", 1)));
        }
        else if(v.equals(pPrecio))
        {
            etPuntosE.setEnabled(true);
            etPuntosE.setText(String.valueOf(settings.getInt("ppe", 1)));
        }
    }
}
