package m13dam.grupo4.gamepinnacle.Classes.Other;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public class Amigos {
    private String Nombre;
    private String ApellidoUno;
    private String ApellidoDos;
    private int Id;

    private String Picture;


    public Amigos(String nombre, String apellidoUno, String apellidoDos,int id) {
        Nombre = nombre;
        ApellidoUno = apellidoUno;
        ApellidoDos = apellidoDos;
        Id = id;
    }

    public Amigos(String nombre, String apellidoUno, String apellidoDos,String picture) {
        Nombre = nombre;
        ApellidoUno = apellidoUno;
        ApellidoDos = apellidoDos;
        Picture = picture;
    }


    public int getId() {
        return Id;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getApellidoUno() {
        return ApellidoUno;
    }

    public void setApellidoUno(String apellidoUno) {
        ApellidoUno = apellidoUno;
    }

    public String getApellidoDos() {
        return ApellidoDos;
    }

    public void setApellidoDos(String apellidoDos) {
        ApellidoDos = apellidoDos;
    }

    public String getPicture() {
        return Picture;
    }

    public void setPicture(String picture) {
        Picture = picture;
    }

    @NonNull
    @Override
    public String toString() {
        return getNombre();
    }
}
