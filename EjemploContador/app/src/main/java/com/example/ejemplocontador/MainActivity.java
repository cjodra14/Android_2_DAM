package com.example.ejemplocontador;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;


public class MainActivity extends Activity {

    public int contadorPulsaciones;
    public final int ZERO= 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        contadorPulsaciones=ZERO;
        visualizarContador();
    }



   public void m_incrementar(View vista1){
        contadorPulsaciones++;
       visualizarContador();
    }

    public void m_decrementar(View vista1){
        contadorPulsaciones--;
        visualizarContador();
    }

    public void m_resetearContador(View vista1){
        contadorPulsaciones=ZERO;
        visualizarContador();
    }
    public void visualizarContador(){
        TextView contador =findViewById(R.id.tv_contador_numeros);
        contador.setText(Integer.toString(contadorPulsaciones));
    }
}