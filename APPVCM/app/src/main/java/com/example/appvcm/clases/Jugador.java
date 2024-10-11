package com.example.appvcm.clases;

import java.io.Serializable;

public class Jugador  implements Serializable {
    private String apodo;
    private String correo;
    private String foto;
    private int nota;

    public Jugador(String apodo, String correo, String foto, int nota) {
        this.apodo = apodo;
        this.correo = correo;
        this.foto = foto;
        this.nota = nota;
    }

    public String getApodo() {
        return apodo;
    }

    public void setApodo(String apodo) {
        this.apodo = apodo;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public int getNota() {
        return nota;
    }

    public void setNota(int nota) {
        this.nota = nota;
    }
}
