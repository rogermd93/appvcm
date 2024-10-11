package com.example.appvcm.clases;

import java.io.Serializable;

public class JugadorDetalle extends Jugador implements Serializable {
    private int campeonatos;
    private int eliminatorias;
    private int championsLeague;
    private int copasMundo;
    private int partidosGanados;
    private int partidosEmpatados;
    private int partidosPerdidos;
    private int golesFavor;
    private int golesContra;
    private int partidosJugados;

    public JugadorDetalle(String apodo, String correo, String foto, int nota,
                          int campeonatos, int eliminatorias, int championsLeague, int copasMundo,
                          int partidosGanados, int partidosEmpatados, int partidosPerdidos,
                          int golesFavor, int golesContra, int partidosJugados) {
        super(apodo, correo, foto, nota);
        this.campeonatos = campeonatos;
        this.eliminatorias = eliminatorias;
        this.championsLeague = championsLeague;
        this.copasMundo = copasMundo;
        this.partidosGanados = partidosGanados;
        this.partidosEmpatados = partidosEmpatados;
        this.partidosPerdidos = partidosPerdidos;
        this.golesFavor = golesFavor;
        this.golesContra = golesContra;
        this.partidosJugados = partidosJugados;
    }

    public JugadorDetalle(String apodo, String correo, String foto, int nota) {
        super(apodo,correo,foto,nota);
        this.setApodo(apodo);
        this.setCorreo(correo);
        this.setFoto(foto);
        this.setNota(nota);
    }

    public int getCampeonatos() {
        return campeonatos;
    }

    public void setCampeonatos(int campeonatos) {
        this.campeonatos = campeonatos;
    }

    public int getEliminatorias() {
        return eliminatorias;
    }

    public void setEliminatorias(int eliminatorias) {
        this.eliminatorias = eliminatorias;
    }

    public int getChampionsLeague() {
        return championsLeague;
    }

    public void setChampionsLeague(int championsLeague) {
        this.championsLeague = championsLeague;
    }

    public int getCopasMundo() {
        return copasMundo;
    }

    public void setCopasMundo(int copasMundo) {
        this.copasMundo = copasMundo;
    }

    public int getPartidosGanados() {
        return partidosGanados;
    }

    public void setPartidosGanados(int partidosGanados) {
        this.partidosGanados = partidosGanados;
    }

    public int getPartidosEmpatados() {
        return partidosEmpatados;
    }

    public void setPartidosEmpatados(int partidosEmpatados) {
        this.partidosEmpatados = partidosEmpatados;
    }

    public int getPartidosPerdidos() {
        return partidosPerdidos;
    }

    public void setPartidosPerdidos(int partidosPerdidos) {
        this.partidosPerdidos = partidosPerdidos;
    }

    public int getGolesFavor() {
        return golesFavor;
    }

    public void setGolesFavor(int golesFavor) {
        this.golesFavor = golesFavor;
    }

    public int getGolesContra() {
        return golesContra;
    }

    public void setGolesContra(int golesContra) {
        this.golesContra = golesContra;
    }

    public int getPartidosJugados() {
        return partidosJugados;
    }

    public void setPartidosJugados(int partidosJugados) {
        this.partidosJugados = partidosJugados;
    }

    public float getGolesContraPorPartido() {
        return golesContra/partidosJugados;
    }

    public float getGolesFavorPorPartido() {
        return golesFavor/partidosJugados;
    }

    public int getDiferenciaGoles() {
        return  golesFavor-golesContra;
    }
}