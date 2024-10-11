package com.example.appvcm.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appvcm.R;
import com.example.appvcm.clases.Partido;

import java.util.List;

public class CalendarioAdapter extends RecyclerView.Adapter<CalendarioAdapter.PartidoViewHolder> {

    private List<Partido> partidos;

    public CalendarioAdapter(List<Partido> partidos) {
        this.partidos = partidos;
    }

    @NonNull
    @Override
    public PartidoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_partido_calendario, parent, false);
        return new PartidoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PartidoViewHolder holder, int position) {
        Partido partido = partidos.get(position);

        // Asignar el texto de la jornada, por ejemplo, Jornada 1
        holder.jornada.setText("Jornada " + (position + 1));

        // Seteamos los nombres de los jugadores enfrentados
        holder.jugador1.setText(partido.getJugador1().getNombreJugador());  // Obtener el nombre del Jugador 1
        holder.jugador2.setText(partido.getJugador2().getNombreJugador());  // Obtener el nombre del Jugador 2
        holder.resultado1.setText(String.valueOf(partido.getJugador1().getGolesFavor())); // Convertir a String
        holder.resultado2.setText(String.valueOf(partido.getJugador2().getGolesFavor())); // Convertir a String
        // Asignar icono de TV o cualquier otro que desees
        holder.iconoTv.setImageResource(R.drawable.ic_menu_camera); // Icono de TV, asegúrate de tener este recurso en tu carpeta `drawable`
    }

    @Override
    public int getItemCount() {
        return partidos.size();
    }

    static class PartidoViewHolder extends RecyclerView.ViewHolder {
        TextView jornada, jugador1, jugador2, resultado1, resultado2;
        ImageView iconoTv;

        public PartidoViewHolder(@NonNull View itemView) {
            super(itemView);
            jornada = itemView.findViewById(R.id.text_jornada);
            jugador1 = itemView.findViewById(R.id.text_jugador1);
            jugador2 = itemView.findViewById(R.id.text_jugador2);
            resultado1 = itemView.findViewById(R.id.text_resultado);
            resultado2 = itemView.findViewById(R.id.text_resultado2);
            iconoTv = itemView.findViewById(R.id.image_tv_icon);
        }
    }
}