package com.example.primeraapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button boton1 = findViewById(R.id.boton1);
        Button boton2 = findViewById(R.id.boton2);
        Button botonSalir = findViewById(R.id.botonSalir);

        boton1.setOnClickListener(view -> Toast.makeText(getApplicationContext(), "este es el boton 1", Toast.LENGTH_LONG).show());
        boton2.setOnClickListener(view -> Toast.makeText(getApplicationContext(), "este es el boton 2", Toast.LENGTH_LONG).show());

        botonSalir.setOnClickListener( view-> this.finishAndRemoveTask());
    }
}