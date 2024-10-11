package com.example.appvcm.player;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.appvcm.R;
import com.example.appvcm.clases.Jugador;
import com.example.appvcm.clases.JugadorDetalle;
import com.example.appvcm.data.DatabaseManager;

public class EditPlayerActivity extends AppCompatActivity {
    private EditText etPlayerName;
    private RatingBar ratingBar;
    private ImageView ivAvatar;
    private Button btnCancel, btnOk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //EdgeToEdge.enable(this);+
        setContentView(R.layout.activity_edit_player);
        etPlayerName = findViewById(R.id.et_player_name);
        ratingBar = findViewById(R.id.ratingBar);
        ivAvatar = findViewById(R.id.iv_avatar);
        btnCancel = findViewById(R.id.btn_cancel);
        btnOk = findViewById(R.id.btn_ok);

        // Obtener datos del jugador si se está editando
        Jugador jugador = (Jugador) getIntent().getSerializableExtra("jugador");
        if (jugador != null) {
            etPlayerName.setText(jugador.getApodo());
            ratingBar.setRating(jugador.getNota());
        }

        btnCancel.setOnClickListener(v -> finish());

        btnOk.setOnClickListener(v -> {
            String playerName = etPlayerName.getText().toString();
            int playerRating = Math.round(ratingBar.getRating());

            // Save to the database
            DatabaseManager dbManager = new DatabaseManager(this);
            long newPlayerId = dbManager.insertPlayer(playerName, "correo@example.com", "foto_url", playerRating);
            dbManager.close();

            // Create the player object to pass back
            JugadorDetalle newPlayer = new JugadorDetalle(playerName, "correo@example.com", "foto_url", playerRating);

            // Pass the player object back to PlayerActivity
            Intent resultIntent = new Intent();
            resultIntent.putExtra("jugador", newPlayer);
            setResult(RESULT_OK, resultIntent);

            // Finish the activity and go back
            finish();
        });
    }
}