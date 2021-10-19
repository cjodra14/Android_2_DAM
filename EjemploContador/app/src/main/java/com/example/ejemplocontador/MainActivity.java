package com.example.ejemplocontador;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.EditText;
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
        CheckBox cb_negativo = (CheckBox) findViewById(R.id.checkNegativos);
        if(cb_negativo.isChecked() && contadorPulsaciones<1){
        contadorPulsaciones--;
        visualizarContador();
        }else if (cb_negativo.isChecked()&&contadorPulsaciones>0){
            contadorPulsaciones--;
            visualizarContador();
        }if (!cb_negativo.isChecked()&& contadorPulsaciones>0){
            contadorPulsaciones--;
            visualizarContador();
        }
    }

    public void m_resetearContador(View vista1){
        EditText editText = (EditText) findViewById(R.id.editTextReset);
        try {
            contadorPulsaciones= Integer.parseInt(editText.getText().toString());
            ocultarTeclado(editText);
        }catch (Exception e){
            contadorPulsaciones= 0;
            ocultarTeclado(editText);
        }

        visualizarContador();
    }
    public void visualizarContador(){
        TextView contador =findViewById(R.id.tv_contador_numeros);
        contador.setText(String.valueOf(contadorPulsaciones));
    }
    public void ocultarTeclado(EditText ed){
        InputMethodManager miteclado = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        miteclado.hideSoftInputFromWindow(ed.getWindowToken(),0);
    }
}