package com.example.appvcm.player;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appvcm.R;
import com.example.appvcm.adapters.JugadoresAdapter;
import com.example.appvcm.clases.Jugador;
import com.example.appvcm.clases.JugadorDetalle;
import com.example.appvcm.data.DatabaseManager;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class PlayerActivity extends AppCompatActivity {
    private static final int REQUEST_CODE_ADD_PLAYER = 1;
    private RecyclerView recyclerViewJugadores;
    private JugadoresAdapter jugadoresAdapter;
    private List<JugadorDetalle> jugadores = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_player);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        // Activar el botón de "atrás"

        recyclerViewJugadores = findViewById(R.id.recycler_view_jugadores);
        recyclerViewJugadores.setLayoutManager(new LinearLayoutManager(this));

        jugadoresAdapter = new JugadoresAdapter();
        recyclerViewJugadores.setAdapter(jugadoresAdapter);

        // Load players from the database or source here
        loadPlayersFromDatabase();

        FloatingActionButton fabAddJugador = findViewById(R.id.btn_add_jugador);
        fabAddJugador.setOnClickListener(v -> {
            Intent intent = new Intent(PlayerActivity.this, EditPlayerActivity.class);
            startActivityForResult(intent, REQUEST_CODE_ADD_PLAYER);
        });
    }

    private void loadPlayersFromDatabase() {
        // This should load players from the database and set them to the adapter
        DatabaseManager dbManager = new DatabaseManager(this);
        jugadores = dbManager.getAllPlayerDetails();
        jugadoresAdapter.setJugadores(jugadores);
        jugadoresAdapter.notifyDataSetChanged();
        dbManager.close();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_ADD_PLAYER && resultCode == RESULT_OK) {
            JugadorDetalle newPlayer = (JugadorDetalle) data.getSerializableExtra("jugador");
            if (newPlayer != null) {
                JugadorDetalle jugadorDetalle = new JugadorDetalle(newPlayer.getApodo(), newPlayer.getCorreo(), newPlayer.getFoto(), newPlayer.getNota());
                //jugadores.add(jugadorDetalle);
                loadPlayersFromDatabase();
                //jugadoresAdapter.notifyDataSetChanged();
            }
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Manejar el botón de "atrás"
        if (item.getItemId() == android.R.id.home) {
            finish(); // Cerrar la actividad y regresar a la anterior
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}