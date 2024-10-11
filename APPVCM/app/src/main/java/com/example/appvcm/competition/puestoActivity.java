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

public class puestoActivity extends AppCompatActivity {
    Button btnsi,btnno;
    int tipo=0;
    String numero="",fase,nombre,selectedFormat;
    int puesto3 = 0;
    int sw=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_puesto);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        Intent intent = getIntent();
        tipo = intent.getIntExtra("tipo",1);
        nombre = intent.getStringExtra("nombreCampeonato");
        numero = intent.getStringExtra("numerojugadores");
        selectedFormat=intent.getStringExtra("selectedFormat");
        btnno=findViewById(R.id.btn_no);
        btnsi=findViewById(R.id.btn_si);
        btnno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                puesto3=0;
                sw=1;
            }
        });
        btnsi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                puesto3=1;
                sw=1;
            }
        });
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
                if (sw == 0) {
                    Toast.makeText(getApplicationContext(), "Por favor, seleccione opcion", Toast.LENGTH_SHORT).show();
                } else {
                        Intent intent = new Intent(getApplicationContext(), sorteofaseActivity.class);
                        intent.putExtra("selectedFormat", selectedFormat);
                        intent.putExtra("nombreCampeonato", nombre);
                        intent.putExtra("numerojugadores",numero);
                        intent.putExtra("puesto",puesto3);
                        intent.putExtra("tipo",tipo);
                        startActivity(intent);

                }
            }
        });
    }
}