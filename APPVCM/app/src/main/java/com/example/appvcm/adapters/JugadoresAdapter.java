package com.example.appvcm.adapters;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appvcm.R;
import com.example.appvcm.clases.Jugador;
import com.example.appvcm.clases.JugadorDetalle;
import com.example.appvcm.player.EditPlayerActivity;
import com.example.appvcm.player.PlayerDetailActivity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class JugadoresAdapter extends RecyclerView.Adapter<JugadoresAdapter.JugadorViewHolder> {

    private List<JugadorDetalle> jugadores = new ArrayList<>();

    @NonNull
    @Override
    public JugadorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_jugador, parent, false);
        return new JugadorViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull JugadorViewHolder holder, int position) {
        JugadorDetalle currentJugador = jugadores.get(position);
        holder.textViewNombre.setText(currentJugador.getApodo().toString());
        holder.textViewTorneos.setText(currentJugador.getCampeonatos() + " torneo(s)");
        holder.itemView.setOnClickListener(v -> {
            // Navegar al activity de edición de jugador
            Intent intent = new Intent(holder.itemView.getContext(), PlayerDetailActivity.class);
            intent.putExtra("jugador", currentJugador); // Pasar el jugador seleccionado
            holder.itemView.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return jugadores.size();
    }

    public void setJugadores(List<JugadorDetalle> jugadores) {
        this.jugadores = jugadores;
        notifyDataSetChanged();
    }


    static class JugadorViewHolder extends RecyclerView.ViewHolder {
        private TextView textViewNombre;
        private TextView textViewTorneos;
        private ImageView imageViewAvatar;

        public JugadorViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewNombre = itemView.findViewById(R.id.text_view_nombre);
            textViewTorneos = itemView.findViewById(R.id.text_view_torneos);
            imageViewAvatar = itemView.findViewById(R.id.image_view_avatar);
        }
    }
}
