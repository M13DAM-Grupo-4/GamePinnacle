package m13dam.grupo4.gamepinnacle.Types;

public class Juegos {
    private String url;
    private String nombre;
    private String horas;
    private String sesion;

    public Juegos(String url, String nombre, String horas, String sesion) {
        this.url = url;
        this.nombre = nombre;
        this.horas = horas;
        this.sesion = sesion;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getHoras() {
        return horas;
    }

    public void setHoras(String horas) {
        this.horas = horas;
    }

    public String getSesion() {
        return sesion;
    }

    public void setSesion(String sesion) {
        this.sesion = sesion;
    }
}
