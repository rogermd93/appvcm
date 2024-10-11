package com.example.appvcm.competition;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.appvcm.R;

public class puntajes extends AppCompatActivity {
    private EditText editTextPointsVictory;
    private EditText editTextPointsDraw;
    private EditText editTextPointsDefeat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_puntajes);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        Intent intent = getIntent();
        String nombreCampeonato = intent.getStringExtra("nombreCampeonato"); // Recibir el nombre del campeonato
        String selectedFormat = intent.getStringExtra("selectedFormat"); // Recibir el formato seleccionado


        editTextPointsVictory = findViewById(R.id.edittext_points_victory);
        editTextPointsDraw = findViewById(R.id.edittext_points_draw);
        editTextPointsDefeat = findViewById(R.id.edittext_points_defeat);
        ImageButton btnBack = findViewById(R.id.btn_back);
        ImageButton btnContinue = findViewById(R.id.btn_continue);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // Regresa a la pantalla anterior
            }
        });

        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pointsVictory = editTextPointsVictory.getText().toString().trim();
                String pointsDraw = editTextPointsDraw.getText().toString().trim();
                String pointsDefeat = editTextPointsDefeat.getText().toString().trim();

                if (TextUtils.isEmpty(pointsVictory) || TextUtils.isEmpty(pointsDraw) || TextUtils.isEmpty(pointsDefeat)) {
                    Toast.makeText(getApplicationContext(), "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show();
                } else {
                    // Continuar a la siguiente actividad, pasando los puntos como extras
                    Intent intent = new Intent(getApplicationContext(), zonas.class);
                    intent.putExtra("pointsVictory", Integer.parseInt(pointsVictory));
                    intent.putExtra("pointsDraw", Integer.parseInt(pointsDraw));
                    intent.putExtra("pointsDefeat", Integer.parseInt(pointsDefeat));
                    intent.putExtra("nombreCampeonato", nombreCampeonato);
                    intent.putExtra("selectedFormat", selectedFormat);
                    startActivity(intent);
                }
            }
        });
    }
}