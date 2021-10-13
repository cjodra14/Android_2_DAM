package com.example.calculadoraoperandos;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    char operador;
    double resultado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void calcular(View view){
        try{
        EditText oper1 = findViewById(R.id.tvOper1);
        EditText oper2 = findViewById(R.id.tvOper2);
        double num1, num2;
        boolean datos_correctos;
        datos_correctos=comprobar();
        if (datos_correctos){
           num1=Double.parseDouble(oper1.getText().toString());
           num2=Double.parseDouble(oper2.getText().toString());
           switch (operador){
               case '+':
                resultado=num1+num2;
                break;
               case '-':
                   resultado=num1-num2;
                   break;
               case 'x':
                   resultado=num1*num2;
                   break;
               case '/':
                   resultado=num1/num2;
                   break;
           }
           visualizarContenido();}
        }catch(Exception e){
            Toast.makeText(this, "Operando no introducido",Toast.LENGTH_LONG).show();
        }
    }
    public void borrarDatos(View vista){
        EditText oper1 = findViewById(R.id.tvOper1);
        EditText oper2 = findViewById(R.id.tvOper2);
        oper1.setText(null);
        oper2.setText(null);
        resultado=0;
        visualizarContenido();
    }
    public void visualizarContenido(){
        TextView contador = findViewById(R.id.tvResultado);
        contador.setText(String.valueOf(resultado));
    }
   public boolean comprobar(){
        EditText oper2 = findViewById(R.id.tvOper2);
        boolean estado= false;

       if (operador=='/'&& Double.parseDouble(oper2.getText().toString())==0){
            Toast.makeText(this, "No se pueden dividir numeros entre 0",Toast.LENGTH_LONG).show();
            }else{
            estado= true;
        }
        return estado;
        }

        public void comprobarOperador(View vista){
        boolean checked = ((RadioButton)vista).isChecked();
            switch (vista.getId()){
                case R.id.rbtnSuma:
                    if (checked){
                        operador='+';
                    }
                    break;
                case R.id.rbtnResta:
                    if (checked){
                        operador='-';
                    }
                    break;
                case R.id.rbtnMultiplicacion:
                    if (checked){
                        operador='x';
                    }
                    break;
                case R.id.rbtnDivision:
                    if (checked){
                        operador='/';
                    }

            }
        }

}