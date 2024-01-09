package m13dam.grupo4.gamepinnacle.Classes.Other;

public class Juego {

    private int Id;

    private String Nombre;

    private String Descripcion;

    private String Imagen;

    private int Año;

    public Juego(int id, String nombre, String descripcion, String imagen) {
        Id = id;
        Nombre = nombre;
        Descripcion = descripcion;
        Imagen = imagen;
    }

    public Juego(int id, String nombre, String descripcion, String imagen, int año) {
        Id = id;
        Nombre = nombre;
        Descripcion = descripcion;
        Imagen = imagen;
        Año = año;
    }

    public int getAño() {
        return Año;
    }

    public int getId() {
        return Id;
    }

    public String getNombre() {
        return Nombre;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public String getImagen() {
        return Imagen;
    }
}
