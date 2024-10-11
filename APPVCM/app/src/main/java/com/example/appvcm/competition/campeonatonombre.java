package com.example.appvcm.competition;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.appvcm.R;
import com.google.android.material.snackbar.Snackbar;

public class campeonatonombre extends AppCompatActivity {

    int tipo=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_campeonatonombre);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        Intent intent = getIntent();
        tipo = intent.getIntExtra("tipo",1);
        String titulo=intent.getStringExtra("tipoCampeonato");
        EditText ednombre=(EditText)findViewById(R.id.edittext_nombre_campeonato);
        ImageButton btnContinue = findViewById(R.id.btn_continue);
        ImageButton btnBack = findViewById(R.id.btn_back);
        TextView title=findViewById(R.id.label_nombre_campeonato);
        title.setText(titulo);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Continuar a la siguiente pantalla
                finish();
            }
        });
        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nombre= ednombre.getText().toString();
                if(nombre.isEmpty()){
                    Snackbar.make(v, "Debe ingresar nombre del campeonato", Snackbar.LENGTH_LONG)
                            .setAction("Action", null)
                            .setAnchorView(R.id.btn_continue).show();
                }else {
                    Intent intent=null;
                    if(tipo==1){
                        intent = new Intent(getApplicationContext(), ida_vuelta.class);
                    } else if (tipo==2) {
                        intent = new Intent(getApplicationContext(), numerojugadoresActivity.class);
                    }

                    intent.putExtra("nombreCampeonato", nombre);
                    intent.putExtra("tipo", tipo);
                    startActivity(intent);
                }

            }
        });
    }
}