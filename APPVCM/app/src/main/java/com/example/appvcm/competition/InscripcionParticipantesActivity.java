package com.example.appvcm.competition;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appvcm.MainActivity;
import com.example.appvcm.R;
import com.example.appvcm.adapters.JugadoresAdapter;
import com.example.appvcm.adapters.ParticipantesAdapter;
import com.example.appvcm.clases.Jugador;
import com.example.appvcm.data.DatabaseManager;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class InscripcionParticipantesActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ParticipantesAdapter adapter;
    private TextView selectedPlayerCount;
    private FloatingActionButton fabContinue;
    int pointsVictory,pointsDraw,pointsDefeat,promotionZone,relegationZone,tipo;
    String nombreCampeonato,selectedFormat,numerojg,fase;
    boolean isonline=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_inscripcion_participantes);

        /*Toolbar toolbar = findViewById(R.id.toolbarply);
        setSupportActionBar(toolbar);*/
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        Intent intent = getIntent();
        tipo= intent.getIntExtra("tipo", 1);
        isonline=intent.getBooleanExtra("isOnlineCompetition", false);

        if(tipo==1){
            selectedFormat = intent.getStringExtra("selectedFormat");
            pointsVictory = intent.getIntExtra("pointsVictory", 0);
            pointsDraw = intent.getIntExtra("pointsDraw", 0);
            pointsDefeat = intent.getIntExtra("pointsDefeat", 0);
            promotionZone = intent.getIntExtra("promotionZone", 0);
            relegationZone = intent.getIntExtra("relegationZone", 0);
        } else if (tipo==2) {
            numerojg = intent.getStringExtra("numerojugadores");
            fase= intent.getStringExtra("tiposorteo");
        }
        nombreCampeonato = intent.getStringExtra("nombreCampeonato");

        boolean isOnlineCompetition = intent.getBooleanExtra("isOnlineCompetition", false);

        recyclerView = findViewById(R.id.recycler_view_players);
        selectedPlayerCount = findViewById(R.id.selected_player_count);
        fabContinue = findViewById(R.id.fab_continue);

        // Configurar el RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Cargar jugadores desde la base de datos o API
        List<Jugador> jugadores = loadPlayers();

        adapter = new ParticipantesAdapter(jugadores, selectedCount -> {
            if(tipo==1){
                selectedPlayerCount.setText(selectedCount + " participantes");
            } else if (tipo==2) {
                selectedPlayerCount.setText(selectedCount + "/"+numerojg+" participantes");
            }
        });
        recyclerView.setAdapter(adapter);

        // Botón para continuar a la siguiente actividad
        fabContinue.setOnClickListener(v -> {
            List<Jugador> selectedPlayers = adapter.getSelectedPlayers();
            if (!selectedPlayers.isEmpty()) {
                if (tipo==1){
                    showCreatingChampionshipDialog();
                } else if (tipo==2) {
                    int n = Integer.parseInt( numerojg);
                    if(n==selectedPlayers.size()){
                        if(fase.equals("Aleatorio")){
                            showTournamentFinishedAleatorioDialog();
                        }else {

                        }
                    }else {
                        Toast.makeText(this, "debe seleccionar la misma cantidad de jugadores seleccionados", Toast.LENGTH_SHORT).show();
                    }
                }
                /*Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                // Pasar la lista de jugadores seleccionados a la siguiente actividad
                startActivity(intent);*/
            } else {
                Toast.makeText(this, "Por favor, seleccione al menos un jugador", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private List<Jugador> loadPlayers() {
        // Cargar jugadores desde la base de datos
        DatabaseManager dbManager = new DatabaseManager(this);
        List<Jugador> jugadores = dbManager.getAllPlayers(); // Obtener todos los jugadores desde la base de datos
        dbManager.close(); // Cerrar la base de datos
        return jugadores;
    }

    private void showCreatingChampionshipDialog() {
        Dialog dialog = new Dialog(InscripcionParticipantesActivity.this);
        dialog.setContentView(R.layout.dialog_creating_championship);
        dialog.setCancelable(false); // Prevent dismissing by touching outside
        dialog.show();
        new Handler().postDelayed(() -> {
            dialog.dismiss(); // Dismiss the first dialog
            showTournamentFinishedDialog(); // Show the next dialog
        }, 200); // 5-second delay
    }

    private void showTournamentFinishedDialog() {
        Intent intentFromPrevious = getIntent();
        String nombreCampeonato = intentFromPrevious.getStringExtra("nombreCampeonato");
        String selectedFormat = intentFromPrevious.getStringExtra("selectedFormat");
        int pointsVictory = intentFromPrevious.getIntExtra("pointsVictory", 0);
        int pointsDraw = intentFromPrevious.getIntExtra("pointsDraw", 0);
        int pointsDefeat = intentFromPrevious.getIntExtra("pointsDefeat", 0);
        int promotionZone = intentFromPrevious.getIntExtra("promotionZone", 0);
        int relegationZone = intentFromPrevious.getIntExtra("relegationZone", 0);
        boolean isOnlineCompetition = intentFromPrevious.getBooleanExtra("isOnlineCompetition", false);
        List<Jugador> selectedPlayers = adapter.getSelectedPlayers();
        String fechaHoy = getCurrentDate();
        DatabaseManager dbManager = new DatabaseManager(this);
        long idTorneo = dbManager.insertTorneo(nombreCampeonato, selectedPlayers.size(), fechaHoy, null, 0, 0);
        boolean esIdaVuelta = selectedFormat.equalsIgnoreCase("Ida/Vuelta") ? true : false;

        dbManager.insertConfiguracion(
                (int) idTorneo,
                pointsVictory,          // ptVictoria
                pointsDraw,             // ptEmpate
                pointsDefeat,           // ptDerrota
                1,                      // numTv
                0,                      // golExterior (puedes ajustarlo según sea necesario)
                0,                      // diferenciaParticular
                1,                      // idTipoTorneo (por defecto 1)
                1,                      // idTipoCalendario (puedes cambiarlo si tienes más tipos)
                esIdaVuelta ? 1 : 0,    // idaVueltaGrupo (1 si es ida/vuelta, 0 si es solo ida)
                esIdaVuelta ? 1 : 0,    // idaVueltaEliminacion (mismo criterio para eliminatorias)
                esIdaVuelta ? 1 : 0,    // idaVueltaFinal (mismo criterio para finales)
                promotionZone,          // numClasifGrupo
                0                       // pequeñaFinal
        );
        int idGrupo = dbManager.getNextIdGrupo((int) idTorneo);
        for (Jugador jugador : selectedPlayers) {
            dbManager.insertInscripcion(jugador.getApodo(), (int) idTorneo);

            dbManager.insertGrupo(
                    (int) idTorneo,     // idTorneo
                    idGrupo,            // idGrupo
                    jugador.getApodo(), // nombreJugador
                    0,                  // numPuntos (inicializado en 0)
                    0,                  // victoria (inicializado en 0)
                    0,                  // empate (inicializado en 0)
                    0,                  // derrota (inicializado en 0)
                    0,                  // golesFavor (inicializado en 0)
                    0,                  // golesContra (inicializado en 0)
                    0,                  // diferencia (inicializado en 0)
                    0,                  // numPartidosJugados (inicializado en 0)
                    1                   // posicion (inicializado en 1 por defecto)
            );
        }
        dbManager.close();

        Dialog dialog = new Dialog(InscripcionParticipantesActivity.this);
        dialog.setContentView(R.layout.dialog_tournament_finished);
        dialog.setCancelable(false); // Prevent dismissing by touching outside
        Button btnFollow = dialog.findViewById(R.id.btn_follow);
        new Handler().postDelayed(() -> {
            btnFollow.setVisibility(View.VISIBLE); // Show the button
            btnFollow.setOnClickListener(v -> {
                Intent intent = new Intent(getApplicationContext(), tournamentActivity.class);
                intent.putExtra("nombreCampeonato", nombreCampeonato);
                intent.putExtra("selectedFormat", selectedFormat);
                intent.putExtra("pointsVictory", pointsVictory);
                intent.putExtra("pointsDraw", pointsDraw);
                intent.putExtra("pointsDefeat", pointsDefeat);
                intent.putExtra("promotionZone", promotionZone);
                intent.putExtra("relegationZone", relegationZone);
                intent.putExtra("isOnlineCompetition", isOnlineCompetition);
                intent.putExtra("idTorneo", (int) idTorneo);
                intent.putExtra("idGrupo", idGrupo);
                startActivity(intent);
                dialog.dismiss();
            });
        }, 1); // 5-second delay
        //dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.show();
    }

    private void showTournamentFinishedAleatorioDialog() {
        Intent intentFromPrevious = getIntent();
        String nombreCampeonato = intentFromPrevious.getStringExtra("nombreCampeonato");

        boolean isOnlineCompetition = intentFromPrevious.getBooleanExtra("isOnlineCompetition", false);
        List<Jugador> selectedPlayers = adapter.getSelectedPlayers();
        String fechaHoy = getCurrentDate();
        DatabaseManager dbManager = new DatabaseManager(this);
        long idTorneo = dbManager.insertTorneo(nombreCampeonato, selectedPlayers.size(), fechaHoy, null, 0, 0);

        dbManager.insertConfiguracion(
                (int) idTorneo,
                pointsVictory,          // ptVictoria
                pointsDraw,             // ptEmpate
                pointsDefeat,           // ptDerrota
                isonline?1:0,           // numTv
                0,                      // golExterior (puedes ajustarlo según sea necesario)
                0,                      // diferenciaParticular
                1,                      // idTipoTorneo (por defecto 1)
                1,                      // idTipoCalendario (puedes cambiarlo si tienes más tipos)
                0,    // idaVueltaGrupo (1 si es ida/vuelta, 0 si es solo ida)
                0,    // idaVueltaEliminacion (mismo criterio para eliminatorias)
                0,    // idaVueltaFinal (mismo criterio para finales)
                0,          // numClasifGrupo
                0                       // pequeñaFinal
        );
        int idGrupo = dbManager.getNextIdGrupo((int) idTorneo);
        for (Jugador jugador : selectedPlayers) {
            dbManager.insertInscripcion(jugador.getApodo(), (int) idTorneo);

            dbManager.insertGrupo(
                    (int) idTorneo,     // idTorneo
                    idGrupo,            // idGrupo
                    jugador.getApodo(), // nombreJugador
                    0,                  // numPuntos (inicializado en 0)
                    0,                  // victoria (inicializado en 0)
                    0,                  // empate (inicializado en 0)
                    0,                  // derrota (inicializado en 0)
                    0,                  // golesFavor (inicializado en 0)
                    0,                  // golesContra (inicializado en 0)
                    0,                  // diferencia (inicializado en 0)
                    0,                  // numPartidosJugados (inicializado en 0)
                    1                   // posicion (inicializado en 1 por defecto)
            );
        }
        dbManager.close();

        Dialog dialog = new Dialog(InscripcionParticipantesActivity.this);
        dialog.setContentView(R.layout.dialog_tournament_finished);
        dialog.setCancelable(false); // Prevent dismissing by touching outside
        Button btnFollow = dialog.findViewById(R.id.btn_follow);
        new Handler().postDelayed(() -> {
            btnFollow.setVisibility(View.VISIBLE); // Show the button
            btnFollow.setOnClickListener(v -> {
                Intent intent = new Intent(getApplicationContext(), tournamentActivity.class);
                intent.putExtra("nombreCampeonato", nombreCampeonato);
                intent.putExtra("isOnlineCompetition", isOnlineCompetition);
                intent.putExtra("idTorneo", (int) idTorneo);
                intent.putExtra("idGrupo", idGrupo);
                intent.putExtra("tipo", tipo);
                startActivity(intent);
                dialog.dismiss();
            });
        }, 1); // 5-second delay
        //dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.show();
    }

    private String getCurrentDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        return sdf.format(new Date());
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflar el menú del Toolbar
        getMenuInflater().inflate(R.menu.menu_player_selection, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_add_player) {
            Toast.makeText(this, "insctribisdsd", Toast.LENGTH_SHORT).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}