package com.example.appvcm.competition;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.appvcm.R;

public class enlinea extends AppCompatActivity {
    private boolean isOnlineCompetition;
    int tipo;
    int pointsVictory,pointsDraw,pointsDefeat,promotionZone,relegationZone;
    String selectedFormat,fase,numerojg,nombrecp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_enlinea);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        Intent intent = getIntent();
        String nombreCampeonato = intent.getStringExtra("nombreCampeonato");
        tipo= intent.getIntExtra("tipo", 1);
        if(tipo==1){
            selectedFormat = intent.getStringExtra("selectedFormat");
            pointsVictory = intent.getIntExtra("pointsVictory", 0);
            pointsDraw = intent.getIntExtra("pointsDraw", 0);
            pointsDefeat = intent.getIntExtra("pointsDefeat", 0);
            promotionZone = intent.getIntExtra("promotionZone", 0);
            relegationZone = intent.getIntExtra("relegationZone", 0);
        } else if (tipo==2) {
            fase= intent.getStringExtra("tiposorteo");
            numerojg= intent.getStringExtra("numerojugadores");
            nombrecp=nombreCampeonato;
        }


        Button btnYes = findViewById(R.id.btn_yes);
        Button btnNo = findViewById(R.id.btn_no);
        ImageButton btnBack = findViewById(R.id.btn_back);
        ImageButton btnContinue = findViewById(R.id.btn_continue);

        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isOnlineCompetition = true;
                Toast.makeText(getApplicationContext(), "Concurso en línea seleccionado", Toast.LENGTH_SHORT).show();
            }
        });

        btnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isOnlineCompetition = false;
                Toast.makeText(getApplicationContext(), "Concurso sin línea seleccionado", Toast.LENGTH_SHORT).show();
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // Regresa a la pantalla anterior
            }
        });

        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Continuar a la siguiente actividad, pasando el valor de isOnlineCompetition
                Intent intent = null;
                if(tipo==1){
                    intent = new Intent(getApplicationContext(), InscripcionParticipantesActivity.class);
                    intent.putExtra("selectedFormat", selectedFormat);
                    intent.putExtra("pointsVictory", pointsVictory);
                    intent.putExtra("pointsDraw", pointsDraw);
                    intent.putExtra("pointsDefeat", pointsDefeat);
                    intent.putExtra("promotionZone", promotionZone);
                    intent.putExtra("relegationZone", relegationZone);
                    intent.putExtra("isOnlineCompetition", isOnlineCompetition);

                } else if (tipo==2) {
                    intent = new Intent(getApplicationContext(), InscripcionParticipantesActivity.class);
                    intent.putExtra("numerojugadores", numerojg);
                    intent.putExtra("tiposorteo", fase);
                    intent.putExtra("isOnlineCompetition", isOnlineCompetition);

                }
                intent.putExtra("nombreCampeonato", nombreCampeonato);
                intent.putExtra("tipo", tipo);

                startActivity(intent);
            }
        });
    }
}