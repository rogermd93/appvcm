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

public class zonas extends AppCompatActivity {
    private EditText editTextPromotionZone;
    private EditText editTextRelegationZone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_zonas);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        Intent intent = getIntent();
        String nombreCampeonato = intent.getStringExtra("nombreCampeonato");
        String selectedFormat = intent.getStringExtra("selectedFormat");
        int pointsVictory = intent.getIntExtra("pointsVictory", 0);
        int pointsDraw = intent.getIntExtra("pointsDraw", 0);
        int pointsDefeat = intent.getIntExtra("pointsDefeat", 0);

        editTextPromotionZone = findViewById(R.id.edittext_promotion_zone);
        editTextRelegationZone = findViewById(R.id.edittext_relegation_zone);
        ImageButton btnBack = findViewById(R.id.btn_back);
        ImageButton btnContinue = findViewById(R.id.btn_continue);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String promotionZone = editTextPromotionZone.getText().toString().trim();
                String relegationZone = editTextRelegationZone.getText().toString().trim();

                if (TextUtils.isEmpty(promotionZone) || TextUtils.isEmpty(relegationZone)) {
                    Toast.makeText(getApplicationContext(), "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show();
                } else {
                    // Continuar a la siguiente actividad, pasando las zonas como extras
                    Intent intent = new Intent(getApplicationContext(), enlinea.class);
                    intent.putExtra("nombreCampeonato", nombreCampeonato);
                    intent.putExtra("selectedFormat", selectedFormat);
                    intent.putExtra("pointsVictory", pointsVictory);
                    intent.putExtra("pointsDraw", pointsDraw);
                    intent.putExtra("pointsDefeat", pointsDefeat);
                    intent.putExtra("promotionZone", Integer.parseInt(promotionZone));
                    intent.putExtra("relegationZone", Integer.parseInt(relegationZone));
                    startActivity(intent);
                }
            }
        });
    }
}