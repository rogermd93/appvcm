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

public class sorteofaseActivity extends AppCompatActivity {
    int tipo=0;
    String numero="",fase,nombre;
    Button btnalt,btnmanual;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sorteofase);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        Intent intent = getIntent();
        tipo = intent.getIntExtra("tipo",1);
        nombre = intent.getStringExtra("nombreCampeonato");
        numero = intent.getStringExtra("numerojugadores");
        btnalt=(Button)findViewById(R.id.btn_Aleatorio);
        btnmanual=(Button)findViewById(R.id.btn_Manual);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        ImageButton btnBack = findViewById(R.id.btn_back);
        ImageButton btnContinue = findViewById(R.id.btn_continue);

        btnalt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fase = "Aleatorio";
            }
        });

        btnmanual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fase = "Manual";
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
                if (fase == null) {
                    Toast.makeText(getApplicationContext(), "Por favor, seleccione una opcion", Toast.LENGTH_SHORT).show();
                } else {
                    // Continuar a la siguiente actividad, pasando el formato seleccionado
                    Intent intent = new Intent(getApplicationContext(), enlinea.class);
                    intent.putExtra("tiposorteo", fase);
                    intent.putExtra("numerojugadores", numero);
                    intent.putExtra("tipo", tipo);
                    intent.putExtra("nombreCampeonato", nombre);
                    startActivity(intent);
                }
            }
        });
    }
}