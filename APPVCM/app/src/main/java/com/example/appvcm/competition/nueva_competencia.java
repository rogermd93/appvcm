package com.example.appvcm.competition;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.appvcm.R;

public class nueva_competencia extends AppCompatActivity {
    int tipo=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_nueva_competencia);
        tipo=1;
        Button btnCampeonato = findViewById(R.id.btn_campeonato);
        Button btnEliminacionDirecta = findViewById(R.id.btn_eliminacion_directa);
        Button btnChampionsLeague = findViewById(R.id.btn_champions_league);
        Button btnCopaMundo = findViewById(R.id.btn_copa_mundo);
        ImageButton btnContinue = findViewById(R.id.btn_continue);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        btnCampeonato.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tipo=1;
                Intent intent = new Intent(getApplicationContext(), campeonatonombre.class);
                intent.putExtra("tipoCampeonato", "Nuevo Campeonato");
                intent.putExtra("tipo", tipo);
                startActivity(intent);
            }
        });

        btnEliminacionDirecta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tipo=2;
                Intent intent = new Intent(getApplicationContext(), campeonatonombre.class);
                intent.putExtra("tipoCampeonato", "Nombre de la copa");
                intent.putExtra("tipo", tipo);
                startActivity(intent);
            }
        });

        btnChampionsLeague.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tipo=3;
                Intent intent = new Intent(getApplicationContext(), campeonatonombre.class);
                intent.putExtra("tipoCampeonato", "Nombre de la copa");
                intent.putExtra("tipo", tipo);
                startActivity(intent);
            }
        });

        btnCopaMundo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tipo=4;
                Intent intent = new Intent(getApplicationContext(), campeonatonombre.class);
                intent.putExtra("tipoCampeonato", "Nombre de la copa");
                intent.putExtra("tipo", tipo);
                startActivity(intent);
            }
        });

        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Continuar a la siguiente pantalla
                Intent intent = new Intent(getApplicationContext(), campeonatonombre.class);
                intent.putExtra("tipoCampeonato", "Nombre de la copa");
                intent.putExtra("tipo", tipo);
                startActivity(intent);
            }
        });
    }
}