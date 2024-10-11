package com.example.appvcm.clases;

public class Campeon {
    private int idTorneo;
    private String nombreJugador;

    // Constructor
    public Campeon(int idTorneo, String nombreJugador) {
        this.idTorneo = idTorneo;
        this.nombreJugador = nombreJugador;
    }

    // Getters y Setters
    public int getIdTorneo() {
        return idTorneo;
    }

    public void setIdTorneo(int idTorneo) {
        this.idTorneo = idTorneo;
    }

    public String getNombreJugador() {
        return nombreJugador;
    }

    public void setNombreJugador(String nombreJugador) {
        this.nombreJugador = nombreJugador;
    }
}