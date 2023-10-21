package hn.uth.pm1e168.Models;

public class Personas {
    private String Nombre;
    private Integer Telefono;
    private String Nota;

    public Personas(String nombre, Integer Telefono, String Nota) {
        this.Nombre = nombre;
        Telefono = Telefono;
        Nota = Nota;
    }

    public Personas() {
    }
    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        this.Nombre = nombre;
    }

    public Integer getTelefono() {
        return Telefono;
    }

    public void setTelefono(Integer Telefono) {Telefono = Telefono;
    }

    public String getNota() {
        return Nota;
    }

    public void setNota(String Nota) {Nota = Nota;
    }
}
