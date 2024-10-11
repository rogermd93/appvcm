package com.example.appvcm.clases;

import com.example.appvcm.data.DatabaseManager;

public class JugadorGrupo {
    private int idTorneo;
    private int idGrupo;
    private String nombreJugador;
    private int numPuntos;
    private int victoria;
    private int empate;
    private int derrota;
    private int golesFavor;

    public int getIdTorneo() {
        return idTorneo;
    }

    public int getIdGrupo() {
        return idGrupo;
    }

    public int getVictoria() {
        return victoria;
    }

    public int getEmpate() {
        return empate;
    }

    public int getDerrota() {
        return derrota;
    }

    private int golesContra;
    private int diferencia;
    private int numPartidosJugados;
    private int posicion;

    public JugadorGrupo(int idTorneo, int idGrupo, String nombreJugador) {
        this.idTorneo = idTorneo;
        this.idGrupo = idGrupo;
        this.nombreJugador = nombreJugador;
        this.numPuntos = 0;
        this.victoria = 0;
        this.empate = 0;
        this.derrota = 0;
        this.golesFavor = 0;
        this.golesContra = 0;
        this.diferencia = 0;
        this.numPartidosJugados = 0;
        this.posicion = 1;
    }

    public JugadorGrupo(int idTorneo, int idGrupo, String nombreJugador, int numPuntos, int victoria, int empate,
                        int derrota, int golesFavor, int golesContra, int diferencia, int numPartidosJugados, int posicion) {
        this.idTorneo = idTorneo;
        this.idGrupo = idGrupo;
        this.nombreJugador = nombreJugador;
        this.numPuntos = numPuntos;
        this.victoria = victoria;
        this.empate = empate;
        this.derrota = derrota;
        this.golesFavor = golesFavor;
        this.golesContra = golesContra;
        this.diferencia = diferencia;
        this.numPartidosJugados = numPartidosJugados;
        this.posicion = posicion;
    }

    public JugadorGrupo(int torneoId, int grupoId, String nombreJugador, int numPuntos, int victoria, int empate, int derrota, int golesFavor, int golesContra, int diferencia, int numPartidosJugados) {
        this.idTorneo = torneoId;
        this.idGrupo = grupoId;
        this.nombreJugador = nombreJugador;
        this.numPuntos = numPuntos;
        this.victoria = victoria;
        this.empate = empate;
        this.derrota = derrota;
        this.golesFavor = golesFavor;
        this.golesContra = golesContra;
        this.diferencia = diferencia;
        this.numPartidosJugados = numPartidosJugados;
        this.posicion = 1; // Inicializar posición con un valor predeterminado si no se pasa como parámetro
    }



    public void actualizarEstadisticas(DatabaseManager dbManager, int golesFavor, int golesContra, int resultado, int pointsVictory, int pointsDraw, int pointsDefeat) {
        this.golesFavor += golesFavor;
        this.golesContra += golesContra;
        this.diferencia = this.golesFavor - this.golesContra;
        this.numPartidosJugados++;

        if (resultado == 1) {
            // Victoria
            this.victoria++;
            this.numPuntos += pointsVictory;
        } else if (resultado == 0) {
            // Empate
            this.empate++;
            this.numPuntos += pointsDraw;
        } else {
            // Derrota
            this.derrota++;
            this.numPuntos += pointsDefeat;
        }

        actualizarEnBaseDeDatos(dbManager);
    }

    private void actualizarEnBaseDeDatos(DatabaseManager dbManager) {
        dbManager.updateGrupo(idTorneo, idGrupo, nombreJugador, numPuntos, victoria, empate, derrota, golesFavor, golesContra, diferencia, numPartidosJugados);
    }

    public int getNumPuntos() {
        return numPuntos;
    }

    public int getVictorias() {
        return victoria;
    }

    public int getEmpates() {
        return empate;
    }

    public int getDerrotas() {
        return derrota;
    }

    public int getGolesFavor() {
        return golesFavor;
    }

    public int getGolesContra() {
        return golesContra;
    }

    public int getDiferencia() {
        return diferencia;
    }

    public int getNumPartidosJugados() {
        return numPartidosJugados;
    }

    public String getNombreJugador() {
        return nombreJugador;
    }
    // Setters
    public void setNumPuntos(int numPuntos) {
        this.numPuntos = numPuntos;
    }

    public void setVictoria(int victoria) {
        this.victoria = victoria;
    }

    public void setEmpate(int empate) {
        this.empate = empate;
    }

    public void setDerrota(int derrota) {
        this.derrota = derrota;
    }

    public void setGolesFavor(int golesFavor) {
        this.golesFavor = golesFavor;
    }

    public void setGolesContra(int golesContra) {
        this.golesContra = golesContra;
    }

    public void setDiferencia(int diferencia) {
        this.diferencia = diferencia;
    }

    public void setNumPartidosJugados(int numPartidosJugados) {
        this.numPartidosJugados = numPartidosJugados;
    }

    public void setNombreJugador(String nombreJugador) {
        this.nombreJugador = nombreJugador;
    }

    public void setIdTorneo(int idTorneo) {
        this.idTorneo = idTorneo;
    }

    public void setIdGrupo(int idGrupo) {
        this.idGrupo = idGrupo;
    }

    public void setPosicion(int posicion) {
        this.posicion = posicion;
    }

    public int getPosicion() {
        return posicion;
    }
}
