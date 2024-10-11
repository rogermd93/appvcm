package com.example.appvcm.clases;

public class Tournament {
    private String nombreTorneo;
    private String fechaInicio;
    private String tipoTorneo;
    private String idTorneo;

    public Tournament(String nombreTorneo, String fechaInicio, String tipoTorneo, String idTorneo) {
        this.nombreTorneo = nombreTorneo;
        this.fechaInicio = fechaInicio;
        this.tipoTorneo = tipoTorneo;
        this.idTorneo= idTorneo;
    }

    public String getIdTorneo() {
        return idTorneo;
    }

    public void setIdTorneo(String idTorneo) {
        this.idTorneo = idTorneo;
    }

    public String getNombreTorneo() {
        return nombreTorneo;
    }

    public String getFechaInicio() {
        return fechaInicio;
    }

    public String getTipoTorneo() {
        return tipoTorneo;
    }
}
