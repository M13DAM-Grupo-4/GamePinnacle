package m13dam.grupo4.gamepinnacle.Classes.Other;

import java.util.ArrayList;

public class Amigos {
    private String Nombre;
    private String ApellidoUno;
    private String ApellidoDos;
    private int Id;


    public Amigos(String nombre, String apellidoUno, String apellidoDos,int id) {
        Nombre = nombre;
        ApellidoUno = apellidoUno;
        ApellidoDos = apellidoDos;
        Id = id;
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



}
