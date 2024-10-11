package com.example.appvcm.competition;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.appvcm.R;

public class ida_vuelta extends AppCompatActivity {
    private String selectedFormat,nombre,numeroj;
    int tipo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_ida_vuelta);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        Intent intent = getIntent();
        String nombreCampeonato = intent.getStringExtra("nombreCampeonato");

        tipo=intent.getIntExtra("tipo",0);
        nombre=intent.getStringExtra("nombreCampeonato");
        numeroj=intent.getStringExtra("numerojugadores");
        if(tipo==2){
            TextView t=findViewById(R.id.label_match_format);
            t.setText("Fases Eliminatorias");
        }
        Button btnIda = findViewById(R.id.btn_ida);
        Button btnIdaVuelta = findViewById(R.id.btn_ida_vuelta);
        ImageButton btnBack = findViewById(R.id.btn_back);
        ImageButton btnContinue = findViewById(R.id.btn_continue);

        btnIda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedFormat = "Ida";
            }
        });

        btnIdaVuelta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedFormat = "Ida/Vuelta";
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // Cierra la actividad actual y regresa a la anterior
            }
        });

        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedFormat == null) {
                    Toast.makeText(getApplicationContext(), "Por favor, seleccione un formato de torneo", Toast.LENGTH_SHORT).show();
                } else {
                    if(tipo==1){
                        Intent intent = new Intent(getApplicationContext(), puntajes.class);
                        intent.putExtra("selectedFormat", selectedFormat);
                        intent.putExtra("nombreCampeonato", nombreCampeonato);
                        startActivity(intent);
                    } else if (tipo==2) {
                        Intent intent = null;
                        if(selectedFormat.equals("Ida")){
                            intent = new Intent(getApplicationContext(), puestoActivity.class);
                        }else if (selectedFormat.equals("Ida/Vuelta")){

                        }
                        intent.putExtra("selectedFormat", selectedFormat);
                        intent.putExtra("nombreCampeonato", nombreCampeonato);
                        intent.putExtra("numerojugadores",numeroj);
                        intent.putExtra("tipo",tipo);

                        startActivity(intent);
                    }

                }
            }
        });
    }
}