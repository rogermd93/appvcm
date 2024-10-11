package com.example.appvcm.clases;

public class Configuracion {
    private int idTorneo;
    private int ptVictoria;
    private int ptEmpate;
    private int ptDerrota;
    private int numTv;
    private int golExterior;
    private int diferenciaParticular;
    private int idTipoTorneo;
    private int idTipoCalendario;
    private int idaVueltaGrupo;
    private int idaVueltaEliminacion;
    private int idaVueltaFinal;
    private int numClasifGrupo;
    private int pequeñaFinal;

    // Constructor
    public Configuracion(int idTorneo, int ptVictoria, int ptEmpate, int ptDerrota, int numTv, int golExterior, int diferenciaParticular, int idTipoTorneo, int idTipoCalendario, int idaVueltaGrupo, int idaVueltaEliminacion, int idaVueltaFinal, int numClasifGrupo, int pequeñaFinal) {
        this.idTorneo = idTorneo;
        this.ptVictoria = ptVictoria;
        this.ptEmpate = ptEmpate;
        this.ptDerrota = ptDerrota;
        this.numTv = numTv;
        this.golExterior = golExterior;
        this.diferenciaParticular = diferenciaParticular;
        this.idTipoTorneo = idTipoTorneo;
        this.idTipoCalendario = idTipoCalendario;
        this.idaVueltaGrupo = idaVueltaGrupo;
        this.idaVueltaEliminacion = idaVueltaEliminacion;
        this.idaVueltaFinal = idaVueltaFinal;
        this.numClasifGrupo = numClasifGrupo;
        this.pequeñaFinal = pequeñaFinal;
    }

    // Getters y Setters
    public int getIdTorneo() {
        return idTorneo;
    }

    public void setIdTorneo(int idTorneo) {
        this.idTorneo = idTorneo;
    }

    public int getPtVictoria() {
        return ptVictoria;
    }

    public void setPtVictoria(int ptVictoria) {
        this.ptVictoria = ptVictoria;
    }

    public int getPtEmpate() {
        return ptEmpate;
    }

    public void setPtEmpate(int ptEmpate) {
        this.ptEmpate = ptEmpate;
    }

    public int getPtDerrota() {
        return ptDerrota;
    }

    public void setPtDerrota(int ptDerrota) {
        this.ptDerrota = ptDerrota;
    }

    public int getNumTv() {
        return numTv;
    }

    public void setNumTv(int numTv) {
        this.numTv = numTv;
    }

    public int getGolExterior() {
        return golExterior;
    }

    public void setGolExterior(int golExterior) {
        this.golExterior = golExterior;
    }

    public int getDiferenciaParticular() {
        return diferenciaParticular;
    }

    public void setDiferenciaParticular(int diferenciaParticular) {
        this.diferenciaParticular = diferenciaParticular;
    }

    public int getIdTipoTorneo() {
        return idTipoTorneo;
    }

    public void setIdTipoTorneo(int idTipoTorneo) {
        this.idTipoTorneo = idTipoTorneo;
    }

    public int getIdTipoCalendario() {
        return idTipoCalendario;
    }

    public void setIdTipoCalendario(int idTipoCalendario) {
        this.idTipoCalendario = idTipoCalendario;
    }

    public int getIdaVueltaGrupo() {
        return idaVueltaGrupo;
    }

    public void setIdaVueltaGrupo(int idaVueltaGrupo) {
        this.idaVueltaGrupo = idaVueltaGrupo;
    }

    public int getIdaVueltaEliminacion() {
        return idaVueltaEliminacion;
    }

    public void setIdaVueltaEliminacion(int idaVueltaEliminacion) {
        this.idaVueltaEliminacion = idaVueltaEliminacion;
    }

    public int getIdaVueltaFinal() {
        return idaVueltaFinal;
    }

    public void setIdaVueltaFinal(int idaVueltaFinal) {
        this.idaVueltaFinal = idaVueltaFinal;
    }

    public int getNumClasifGrupo() {
        return numClasifGrupo;
    }

    public void setNumClasifGrupo(int numClasifGrupo) {
        this.numClasifGrupo = numClasifGrupo;
    }

    public int getPequeñaFinal() {
        return pequeñaFinal;
    }

    public void setPequeñaFinal(int pequeñaFinal) {
        this.pequeñaFinal = pequeñaFinal;
    }
}