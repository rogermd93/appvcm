package com.example.appvcm.clases;

import com.example.appvcm.data.DatabaseManager;

public class Partido {
    private JugadorGrupo jugador1;
    private JugadorGrupo jugador2;
    private int golesJugador1;

    public void setGolesJugador2(int golesJugador2) {
        this.golesJugador2 = golesJugador2;
    }

    public void setGolesJugador1(int golesJugador1) {
        this.golesJugador1 = golesJugador1;
    }

    private int golesJugador2;

    public Partido(JugadorGrupo jugador1, JugadorGrupo jugador2) {
        this.jugador1 = jugador1;
        this.jugador2 = jugador2;
    }

    public void simularPartido(DatabaseManager dbManager, int pointsVictory, int pointsDraw, int pointsDefeat) {
        this.golesJugador1 = (int) (Math.random() * 6); // Goles aleatorios entre 0 y 5
        this.golesJugador2 = (int) (Math.random() * 6);

        // Determinar el resultado y actualizar estadísticas
        if (golesJugador1 > golesJugador2) {
            jugador1.actualizarEstadisticas(dbManager, golesJugador1, golesJugador2, 1, pointsVictory, pointsDraw, pointsDefeat);
            jugador2.actualizarEstadisticas(dbManager, golesJugador2, golesJugador1, -1, pointsVictory, pointsDraw, pointsDefeat);
        } else if (golesJugador1 < golesJugador2) {
            jugador1.actualizarEstadisticas(dbManager, golesJugador1, golesJugador2, -1, pointsVictory, pointsDraw, pointsDefeat);
            jugador2.actualizarEstadisticas(dbManager, golesJugador2, golesJugador1, 1, pointsVictory, pointsDraw, pointsDefeat);
        } else {
            // Empate
            jugador1.actualizarEstadisticas(dbManager, golesJugador1, golesJugador2, 0, pointsVictory, pointsDraw, pointsDefeat);
            jugador2.actualizarEstadisticas(dbManager, golesJugador2, golesJugador1, 0, pointsVictory, pointsDraw, pointsDefeat);
        }
    }
    public JugadorGrupo getJugador1() {
        return jugador1;
    }

    public JugadorGrupo getJugador2() {
        return jugador2;
    }
}
