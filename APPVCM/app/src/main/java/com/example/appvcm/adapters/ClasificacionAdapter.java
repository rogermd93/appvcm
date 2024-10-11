package com.example.appvcm.adapters;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appvcm.R;
import com.example.appvcm.clases.JugadorGrupo;

import java.util.ArrayList;
import java.util.List;

public class ClasificacionAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int VIEW_TYPE_HEADER = 0;
    private static final int VIEW_TYPE_ITEM = 1;

    private List<JugadorGrupo> jugadores = new ArrayList<>();

    public ClasificacionAdapter(List<JugadorGrupo> jugadores) {
        this.jugadores = jugadores;
    }

    @Override
    public int getItemViewType(int position) {
        // La primera posición será el encabezado
        return (position == 0) ? VIEW_TYPE_HEADER : VIEW_TYPE_ITEM;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_HEADER) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.header_clasificacion, parent, false);
            return new HeaderViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_jugador_tabla, parent, false);
            return new ClasificacionViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ClasificacionViewHolder) {
            // Ajustamos los datos solo para las filas de jugadores
            JugadorGrupo jugador = jugadores.get(position - 1); // -1 porque la primera fila es el encabezado

            ClasificacionViewHolder clasificacionHolder = (ClasificacionViewHolder) holder;
            clasificacionHolder.posicion.setText(String.valueOf(position)); // La posición real
            clasificacionHolder.nombreJugador.setText(jugador.getNombreJugador());
            clasificacionHolder.puntos.setText(String.valueOf(jugador.getNumPuntos()));
            clasificacionHolder.partidosJugados.setText(String.valueOf(jugador.getNumPartidosJugados()));
            clasificacionHolder.partidosGanados.setText(String.valueOf(jugador.getVictorias()));
            clasificacionHolder.partidosEmpatados.setText(String.valueOf(jugador.getEmpates()));
            clasificacionHolder.partidosPerdidos.setText(String.valueOf(jugador.getDerrotas()));
            clasificacionHolder.golesFavor.setText(String.valueOf(jugador.getGolesFavor()));
            clasificacionHolder.golesContra.setText(String.valueOf(jugador.getGolesContra()));
            clasificacionHolder.diferenciaGoles.setText(String.valueOf(jugador.getDiferencia()));

            // Alternar color de fondo basado en la posición
            if (position % 2 == 0) {
                holder.itemView.setBackgroundColor(Color.parseColor("#FFF9C4")); // Amarillo claro
            } else {
                holder.itemView.setBackgroundColor(Color.parseColor("#E0E0E0")); // Gris claro
            }
        }
    }

    @Override
    public int getItemCount() {
        return jugadores.size() + 1; // +1 porque agregamos el encabezado
    }

    // ViewHolder para las filas de jugadores
    static class ClasificacionViewHolder extends RecyclerView.ViewHolder {
        TextView posicion, nombreJugador, puntos, partidosJugados, partidosGanados, partidosEmpatados, partidosPerdidos, golesFavor, golesContra, diferenciaGoles;

        ClasificacionViewHolder(@NonNull View itemView) {
            super(itemView);
            posicion = itemView.findViewById(R.id.posicion);
            nombreJugador = itemView.findViewById(R.id.nombreJugador);
            puntos = itemView.findViewById(R.id.puntos);
            partidosJugados = itemView.findViewById(R.id.partidosJugados);
            partidosGanados = itemView.findViewById(R.id.partidosGanados);
            partidosEmpatados = itemView.findViewById(R.id.partidosEmpatados);
            partidosPerdidos = itemView.findViewById(R.id.partidosPerdidos);
            golesFavor = itemView.findViewById(R.id.golesFavor);
            golesContra = itemView.findViewById(R.id.golesContra);
            diferenciaGoles = itemView.findViewById(R.id.diferenciaGoles);
        }
    }

    // ViewHolder para el encabezado
    static class HeaderViewHolder extends RecyclerView.ViewHolder {
        HeaderViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
