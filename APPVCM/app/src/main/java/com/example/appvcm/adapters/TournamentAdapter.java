package com.example.appvcm.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appvcm.R;
import com.example.appvcm.clases.Tournament;
import com.example.appvcm.competition.tournamentActivity;

import java.util.List;

public class TournamentAdapter extends RecyclerView.Adapter<TournamentAdapter.TournamentViewHolder> {

    private List<Tournament> tournaments;
    private Context context;
    public TournamentAdapter(List<Tournament> tournaments) {
        this.tournaments = tournaments;
    }

    public TournamentAdapter(List<Tournament> tournaments, Context context) {
        this.tournaments = tournaments;
        this.context = context;  // Initialize context
    }

    @NonNull
    @Override
    public TournamentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tournament, parent, false);
        return new TournamentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TournamentViewHolder holder, int position) {
        Tournament tournament = tournaments.get(position);
        holder.tournamentName.setText(tournament.getNombreTorneo());
        holder.startDate.setText(tournament.getFechaInicio());
        holder.tournamentType.setText(tournament.getTipoTorneo());

        // Set OnClickListener on the item view
        holder.itemView.setOnClickListener(v -> {
            // Show the AlertDialog when an item is clicked
            showTournamentOptionsDialog(tournament);
        });
    }

    @Override
    public int getItemCount() {
        return tournaments.size();
    }

    static class TournamentViewHolder extends RecyclerView.ViewHolder {
        TextView tournamentName, startDate, tournamentType;

        public TournamentViewHolder(@NonNull View itemView) {
            super(itemView);
            tournamentName = itemView.findViewById(R.id.tournamentName);
            startDate = itemView.findViewById(R.id.startDate);
            tournamentType = itemView.findViewById(R.id.tournamentType);
        }
    }
    private void showTournamentOptionsDialog(Tournament tournament) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(tournament.getNombreTorneo())
                .setIcon(R.drawable.ic_menu_gallery)  // Replace with your trophy icon
                .setItems(new String[]{"Seguir", "Suprimir"}, (dialog, which) -> {
                    if (which == 0) {
                        Intent intent = new Intent(context, tournamentActivity.class);
                        intent.putExtra("idTorneo", Integer.parseInt(tournament.getIdTorneo()));
                        context.startActivity(intent);
                        Toast.makeText(context, "Seguir clicked", Toast.LENGTH_SHORT).show();
                    } else if (which == 1) {
                        // Action for "Suprimir"
                        Toast.makeText(context, "Suprimir clicked", Toast.LENGTH_SHORT).show();
                    }
                })
                .show();
    }
}