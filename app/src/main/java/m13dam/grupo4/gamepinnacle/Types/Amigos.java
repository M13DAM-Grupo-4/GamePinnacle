package m13dam.grupo4.gamepinnacle.Types;

import java.util.ArrayList;

public class Amigos {

    private String Nombre;
    private String ApellidoUno;
    private String ApellidoDos;
    private ArrayList<Games> listaJuegos;

    public Amigos(String nombre, String apellidoUno, String apellidoDos, ArrayList<Games> listaJuegos) {
        Nombre = nombre;
        ApellidoUno = apellidoUno;
        ApellidoDos = apellidoDos;
        this.listaJuegos = listaJuegos;
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

    public ArrayList<Games> getListaJuegos() {
        return listaJuegos;
    }

    public void setListaJuegos(ArrayList<Games> listaJuegos) {
        this.listaJuegos = listaJuegos;
    }
}
