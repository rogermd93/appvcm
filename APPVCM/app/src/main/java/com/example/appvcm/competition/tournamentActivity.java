package com.example.appvcm.competition;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import com.example.appvcm.MainActivity;
import com.example.appvcm.R;
import com.example.appvcm.adapters.CalendarioAdapter;
import com.example.appvcm.adapters.ClasificacionAdapter;
import com.example.appvcm.clases.Jugador;
import com.example.appvcm.clases.JugadorGrupo;
import com.example.appvcm.clases.Partido;
import com.example.appvcm.data.DatabaseManager;
import com.example.appvcm.player.EditPlayerActivity;
import com.example.appvcm.player.PlayerActivity;
import com.google.android.material.tabs.TabLayout;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class tournamentActivity extends AppCompatActivity {
    private List<JugadorGrupo> jugadores= new ArrayList<>();
    private List<Partido> partidos;
    private DatabaseManager databaseManager;
    private int pointsVictory, pointsDraw, pointsDefeat;
    private int promotionZone, relegationZone;
    private int idTorneo, idGrupo;
    ClasificacionAdapter clasificacionAdapter;
    CalendarioAdapter calendarioAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_tournament);
        Intent intent = getIntent();
        String nombreCampeonato = intent.getStringExtra("nombreCampeonato");
        String selectedFormat = intent.getStringExtra("selectedFormat");
        pointsVictory = intent.getIntExtra("pointsVictory", 0);
        pointsDraw = intent.getIntExtra("pointsDraw", 0);
        pointsDefeat = intent.getIntExtra("pointsDefeat", 0);
        promotionZone = intent.getIntExtra("promotionZone", 0);
        relegationZone = intent.getIntExtra("relegationZone", 0);
        boolean isOnlineCompetition = intent.getBooleanExtra("isOnlineCompetition", false);
        idTorneo = intent.getIntExtra("idTorneo", 0);  // Recibe idTorneo
        idGrupo = intent.getIntExtra("idGrupo", 1);    // Recibe idGrupo

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        databaseManager = new DatabaseManager(this);
        jugadores = databaseManager.cargarJugadoresDesdeBD(idTorneo);

        if (selectedFormat == null || selectedFormat.isEmpty()) {
            Cursor cursor = databaseManager.getConfiguracionByTorneo(idTorneo);
            if (cursor != null && cursor.moveToFirst()) {
                pointsVictory = cursor.getInt(cursor.getColumnIndexOrThrow("ptVictoria"));
                pointsDraw = cursor.getInt(cursor.getColumnIndexOrThrow("ptEmpate"));
                pointsDefeat = cursor.getInt(cursor.getColumnIndexOrThrow("ptDerrota"));
                promotionZone = cursor.getInt(cursor.getColumnIndexOrThrow("numClasifGrupo"));
                relegationZone = cursor.getInt(cursor.getColumnIndexOrThrow("pequeñaFinal"));
                cursor.close();
            } else {
                // Si no hay configuración en la base de datos, usar los valores por defecto
                pointsVictory = 0;
                pointsDraw = 0;
                pointsDefeat = 0;
                promotionZone = 0;
                relegationZone = 0;
            }
            partidos = databaseManager.obtenerPartidosDesdeBD(idTorneo);
        } else {
            // Si tiene datos, seguimos el flujo normal de generación de partidos
            partidos = generarCalendario(jugadores, selectedFormat.equalsIgnoreCase("Ida/Vuelta"));
        }
        //jugadores = databaseManager.cargarJugadoresDesdeBD(idTorneo);
        //partidos = generarCalendario(jugadores, selectedFormat.equalsIgnoreCase("Ida/Vuelta")); // true para ida y vuelta

        // Configurar TabLayout y ViewPager
        TabLayout tabLayout = (TabLayout)findViewById(R.id.tabLayout);
        ViewPager viewPager = findViewById(R.id.viewPager);

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter();
        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);


    }



    // Genera el calendario de partidos (ida y vuelta)
    private List<Partido> generarCalendario(List<JugadorGrupo> jugadores, boolean idaVuelta) {
        List<Partido> calendario = new ArrayList<>();
        int numJugadores = jugadores.size();

        for (int i = 0; i < numJugadores; i++) {
            for (int j = i + 1; j < numJugadores; j++) {
                calendario.add(new Partido(jugadores.get(i), jugadores.get(j)));
                if (idaVuelta) {
                    calendario.add(new Partido(jugadores.get(j), jugadores.get(i)));
                }
            }
        }
        return calendario;
    }

    private class ViewPagerAdapter extends androidx.viewpager.widget.PagerAdapter {

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            RecyclerView recyclerView = new RecyclerView(tournamentActivity.this);
            recyclerView.setLayoutManager(new LinearLayoutManager(tournamentActivity.this));

            if (position == 0) {
                // Clasificación
                clasificacionAdapter = new ClasificacionAdapter(jugadores);
                recyclerView.setAdapter(clasificacionAdapter);
            } else {
                // Calendario
                calendarioAdapter = new CalendarioAdapter(partidos);
                recyclerView.setAdapter(calendarioAdapter);
            }

            container.addView(recyclerView);
            return recyclerView;
        }

        @Override
        public int getCount() {
            return 2; // Dos pestañas: Clasificación y Calendario
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return position == 0 ? "Clasificación" : "Calendario";
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflar el menú del Toolbar
        getMenuInflater().inflate(R.menu.menu_toolbar, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            Intent intent = new Intent(this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
            return true;
        }
        if (id == R.id.action_eye) {
            Toast.makeText(this, "Ver acción seleccionada", Toast.LENGTH_SHORT).show();
            return true;
        } else if (id == R.id.action_clipboard) {
            //Toast.makeText(this, "Checklist acción seleccionada", Toast.LENGTH_SHORT).show();
            showAlertDialog();
            return true;
        } else if (id == R.id.action_share) {
            Toast.makeText(this, "Compartir acción seleccionada", Toast.LENGTH_SHORT).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    private void showAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setIcon(R.drawable.ic_menu_gallery);  // Reemplazar con el ícono de trofeo en res/drawable
        builder.setTitle("Simular");
        builder.setMessage("¿Simular todos los partidos restantes?");

        // Botón "NO"
        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Acción cuando el usuario presiona "NO"
                Toast.makeText(tournamentActivity.this, "No seleccionado", Toast.LENGTH_SHORT).show();
            }
        });

        // Botón "SÍ"
        builder.setPositiveButton("SÍ", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Acción cuando el usuario presiona "SÍ"
                new SimularPartidosTask().execute();
                Toast.makeText(tournamentActivity.this, "Sí seleccionado", Toast.LENGTH_SHORT).show();
            }
        });

        // Mostrar el diálogo
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void showTournamentEndAlert() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setIcon(R.drawable.ic_menu_gallery);  // Replace with the trophy icon
        builder.setTitle("¡Torneo acabado!");
        builder.setMessage("¿Terminar el campeonato?");

        // "NO" button
        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(tournamentActivity.this, "No seleccionado", Toast.LENGTH_SHORT).show();
            }
        });

        // "SÍ" button
        builder.setPositiveButton("SÍ", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                terminarCampeonato(); // Call the method to finish the tournament
                Toast.makeText(tournamentActivity.this, "Sí seleccionado", Toast.LENGTH_SHORT).show();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void terminarCampeonato() {
        String fechaActual = getCurrentDate();
        databaseManager.updateTorneo(idTorneo, fechaActual, 1, 1); // Set terminado=1, gruposTerminados=1
        Toast.makeText(this, "Campeonato finalizado", Toast.LENGTH_SHORT).show();
    }

    private String getCurrentDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        return sdf.format(new Date());
    }

    private class SimularPartidosTask extends AsyncTask<Void, Integer, Void> {
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Mostrar el ProgressDialog
            progressDialog = new ProgressDialog(tournamentActivity.this);
            progressDialog.setTitle("Simulando partidos");
            progressDialog.setMessage("Por favor, espera...");
            progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            progressDialog.setMax(100);
            progressDialog.setProgress(0);
            progressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            // Simular el progreso de 0 a 100 y asignar puntajes aleatorios
            for (int i = 0; i < partidos.size(); i++) {
                try {
                    Thread.sleep(100); // Simular trabajo
                    Partido partido = partidos.get(i);

                    // Simular el partido y actualizar la base de datos
                    partido.simularPartido(databaseManager, pointsVictory, pointsDraw, pointsDefeat);

                    // Actualizar la lista de jugadores con las nuevas estadísticas
                    JugadorGrupo jugador1 = partido.getJugador1();
                    JugadorGrupo jugador2 = partido.getJugador2();

                    // Actualiza los jugadores en la lista original
                    actualizarEstadisticasJugador(jugador1);
                    actualizarEstadisticasJugador(jugador2);

                    publishProgress((int) ((i / (float) partidos.size()) * 100));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            // Actualizar el progreso
            progressDialog.setProgress(values[0]);
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            // Cerrar el ProgressDialog al finalizar
            progressDialog.dismiss();
            clasificacionAdapter.notifyDataSetChanged();
            calendarioAdapter.notifyDataSetChanged();
            Toast.makeText(tournamentActivity.this, "Simulación completada", Toast.LENGTH_SHORT).show();
            // Aquí podrías actualizar la UI si fuera necesario
        }
    }

    private void actualizarEstadisticasJugador(JugadorGrupo jugadorActualizado) {
        for (JugadorGrupo jugador : jugadores) {
            if (jugador.getNombreJugador().equals(jugadorActualizado.getNombreJugador())) {
                jugador.setNumPuntos(jugadorActualizado.getNumPuntos());
                jugador.setVictoria(jugadorActualizado.getVictorias());
                jugador.setEmpate(jugadorActualizado.getEmpates());
                jugador.setDerrota(jugadorActualizado.getDerrotas());
                jugador.setGolesFavor(jugadorActualizado.getGolesFavor());
                jugador.setGolesContra(jugadorActualizado.getGolesContra());
                jugador.setDiferencia(jugadorActualizado.getDiferencia());
                jugador.setNumPartidosJugados(jugadorActualizado.getNumPartidosJugados());

                databaseManager.updateGrupo(
                        jugador.getIdTorneo(),
                        jugador.getIdGrupo(),
                        jugador.getNombreJugador(),
                        jugador.getNumPuntos(),
                        jugador.getVictorias(),
                        jugador.getEmpates(),
                        jugador.getDerrotas(),
                        jugador.getGolesFavor(),
                        jugador.getGolesContra(),
                        jugador.getDiferencia(),
                        jugador.getNumPartidosJugados()
                );
                break; // Una vez que encontramos el jugador, podemos salir del bucle
            }
        }
    }
}