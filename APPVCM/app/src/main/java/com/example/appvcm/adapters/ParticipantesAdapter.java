package com.example.appvcm.adapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appvcm.R;
import com.example.appvcm.clases.Jugador;
import com.example.appvcm.player.EditPlayerActivity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ParticipantesAdapter extends RecyclerView.Adapter<ParticipantesAdapter.JugadorViewHolder> {

    private List<Jugador> jugadores = new ArrayList<>();
    private Set<Jugador> selectedPlayers = new HashSet<>(); // Use Set to avoid duplicates
    private OnPlayerSelectedListener listener;

    public interface OnPlayerSelectedListener {
        void onPlayerSelectionChanged(int selectedCount);
    }

    public ParticipantesAdapter(List<Jugador> jugadores, OnPlayerSelectedListener listener) {
        this.jugadores = jugadores;
        this.listener = listener;
    }

    @NonNull
    @Override
    public JugadorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_player, parent, false);
        return new JugadorViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull JugadorViewHolder holder, int position) {
        Jugador currentJugador = jugadores.get(position);
        holder.textViewNombre.setText(currentJugador.getApodo());

        // Reset OnCheckedChangeListener to prevent incorrect behavior
        holder.checkBox.setOnCheckedChangeListener(null);

        // Set if the player is selected or not
        holder.checkBox.setChecked(selectedPlayers.contains(currentJugador));

        // Handle CheckBox change
        holder.checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                selectedPlayers.add(currentJugador); // Add player to the set
            } else {
                selectedPlayers.remove(currentJugador); // Remove player from the set
            }
            listener.onPlayerSelectionChanged(selectedPlayers.size()); // Notify the listener
        });

        // Edit player on item click
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(holder.itemView.getContext(), EditPlayerActivity.class);
            intent.putExtra("jugador", currentJugador); // Pass the player to edit
            holder.itemView.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return jugadores.size();
    }

    // Return the selected players
    public List<Jugador> getSelectedPlayers() {
        return new ArrayList<>(selectedPlayers); // Convert Set to List
    }

    // Set players and refresh RecyclerView
    public void setJugadores(List<Jugador> jugadores) {
        this.jugadores = jugadores;
        notifyDataSetChanged();
    }

    // ViewHolder class
    static class JugadorViewHolder extends RecyclerView.ViewHolder {
        private TextView textViewNombre;
        private ImageView imageViewAvatar;
        private CheckBox checkBox;

        public JugadorViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewNombre = itemView.findViewById(R.id.player_name);
            imageViewAvatar = itemView.findViewById(R.id.player_avatar);
            checkBox = itemView.findViewById(R.id.player_checkbox);
        }
    }
}
