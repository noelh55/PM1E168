package hn.uth.pm1e168.Models;

public class Personas {
    private String Pais;
    private String Nombre;
    private Integer Telefono;
    private String Nota;

    public Personas(String pais, String nombre, Integer Telefono, String Nota) {
        this.Pais = pais;
        this.Nombre = nombre;
        this.Telefono = Telefono;
        this.Nota = Nota;
    }

    public Personas() {
    }
    public String getPais(){return Pais;}
    public void setPais(String pais) {this.Pais = pais;}

    public String getNombre() {
        return Nombre;
    }
    public void setNombre(String nombre) {
        this.Nombre = nombre;
    }

    public Integer getTelefono() {
        return Telefono;
    }
    public void setTelefono(Integer Telefono) {this.Telefono = Telefono;
    }

    public String getNota() {
        return Nota;
    }
    public void setNota(String Nota) {this.Nota = Nota;
    }
}
