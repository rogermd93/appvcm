package com.example.appvcm.competition;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.appvcm.R;
import com.google.android.material.snackbar.Snackbar;

public class numerojugadoresActivity extends AppCompatActivity {
    int tipo=0;
    String nombre;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_numerojugadores);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        Intent intent = getIntent();
        tipo = intent.getIntExtra("tipo",1);
        nombre= intent.getStringExtra("nombreCampeonato");

        EditText num=(EditText) findViewById(R.id.edittext_numero_jugadores);
        ImageButton btnBack = findViewById(R.id.btn_back);
        ImageButton btnContinue = findViewById(R.id.btn_continue);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // Cierra la actividad actual y regresa a la anterior
            }
        });

        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String numero= num.getText().toString();
                if(numero.isEmpty()){
                    Snackbar.make(v, "Debe ingresar numero de jugadores", Snackbar.LENGTH_LONG)
                            .setAction("Action", null)
                            .setAnchorView(R.id.btn_continue).show();
                } else {
                    int nu=Integer.parseInt(numero);
                    if(nu<=1){
                        Snackbar.make(v, "Debe ser al menos 2 jugadores", Snackbar.LENGTH_LONG)
                                .setAction("Action", null)
                                .setAnchorView(R.id.btn_continue).show();
                    }else {
                        if(nu>=4){
                            Intent intent = new Intent(getApplicationContext(), ida_vuelta.class);
                            intent.putExtra("nombreCampeonato", nombre);
                            intent.putExtra("numerojugadores", numero);
                            intent.putExtra("tipo", tipo);
                            startActivity(intent);
                        }else {
                            Intent intent = new Intent(getApplicationContext(), sorteofaseActivity.class);
                            intent.putExtra("nombreCampeonato", nombre);
                            intent.putExtra("numerojugadores", numero);
                            intent.putExtra("tipo", tipo);
                            startActivity(intent);
                        }

                    }
                }
            }
        });
    }
}