package com.example.appvcm.player;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

//import com.example.appvcm.R;
import com.example.appvcm.R;
import com.example.appvcm.clases.JugadorDetalle;

import java.util.Locale;

public class PlayerDetailActivity extends AppCompatActivity {
    private TextView textViewApodo, textViewVisión, textViewCampeonatos, textViewEstadísticas;
    private ImageView imageViewAvatar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_player_detail);

        // Enlazar los componentes de la vista
        ImageView imageViewAvatar = findViewById(R.id.image_view_avatar);
        TextView textViewApodo = findViewById(R.id.dttext_view_apodo);
        TextView textViewVision = findViewById(R.id.dttext_view_vision_conjunto);
        TextView textViewPercentage = findViewById(R.id.dttext_view_percentage);
        TextView textViewCampeonatos = findViewById(R.id.dttext_view_palmares);
        TextView textViewPartidosJugados = findViewById(R.id.dttext_view_partidos_jugados);
        TextView textViewGolesFavorPartido = findViewById(R.id.dttext_view_goles_favor_partido);
        TextView textViewGolesContraPartido = findViewById(R.id.dttext_view_goles_contra_partido);
        TextView textViewGolesFavor = findViewById(R.id.dttext_view_goles_favor);
        TextView textViewGolesContra = findViewById(R.id.dttext_view_goles_contra);
        TextView textViewDiferenciaGoles = findViewById(R.id.dttext_view_diferencia_goles);
        TextView textViewHistorico = findViewById(R.id.dttext_view_historico);

        Intent intent = getIntent();
        JugadorDetalle jugadorDetalle = (JugadorDetalle) intent.getSerializableExtra("jugador");

        if (jugadorDetalle != null) {
            // Asignar los valores a los componentes de la vista
            textViewApodo.setText(jugadorDetalle.getApodo());
            textViewVision.setText(jugadorDetalle.getPartidosGanados() + "G - " + jugadorDetalle.getPartidosEmpatados() + "E - " + jugadorDetalle.getPartidosPerdidos() + "P");

            // Calcular porcentajes de victorias, empates y derrotas
            int totalPartidos = jugadorDetalle.getPartidosJugados();
            if (totalPartidos > 0) {
                double winPercentage = (jugadorDetalle.getPartidosGanados() / (double) totalPartidos) * 100;
                double drawPercentage = (jugadorDetalle.getPartidosEmpatados() / (double) totalPartidos) * 100;
                double lossPercentage = (jugadorDetalle.getPartidosPerdidos() / (double) totalPartidos) * 100;
                textViewPercentage.setText(String.format(Locale.getDefault(), "%.2f%% - %.2f%% - %.2f%%", winPercentage, drawPercentage, lossPercentage));
            }

            textViewCampeonatos.setText(jugadorDetalle.getCampeonatos() + " campeonato(s)");
            textViewPartidosJugados.setText(String.valueOf(jugadorDetalle.getPartidosJugados()));
            textViewGolesFavorPartido.setText(String.format(Locale.getDefault(), "%.2f", jugadorDetalle.getGolesFavorPorPartido()));
            textViewGolesContraPartido.setText(String.format(Locale.getDefault(), "%.2f", jugadorDetalle.getGolesContraPorPartido()));
            textViewGolesFavor.setText(String.valueOf(jugadorDetalle.getGolesFavor()));
            textViewGolesContra.setText(String.valueOf(jugadorDetalle.getGolesContra()));
            textViewDiferenciaGoles.setText(String.valueOf(jugadorDetalle.getDiferenciaGoles()));

            // Cargar histórico
            textViewHistorico.setText("Jugador 1 - 2 Jugador 2");
        }
    }
}