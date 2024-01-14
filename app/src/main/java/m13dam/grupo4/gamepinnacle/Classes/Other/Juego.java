package m13dam.grupo4.gamepinnacle.Classes.Other;

public class Juego {

    private static String nombreJuego;
    private static int id_juegoSeleccionado;
    private int Id;

    private String Nombre;

    private String Descripcion;

    private String Imagen;
    private int friend_id;

    private int Año;
    private String horas;

    private int playTime;

    private int playTime2Weeks;

    private int steamID;

    private int igdbID;
    private Boolean winLose;

    private String steamImagen;

    public Juego(){

    }

    public Juego(int id, int friend_id, int playTime, Boolean winLose) {
        Id = id;
        this.friend_id = friend_id;
        this.playTime = playTime;
        this.winLose = winLose;
    }

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

    public static int getId_juegoSeleccionado() {
        return id_juegoSeleccionado;
    }

    public static void setId_juegoSeleccionado(int id_juegoSeleccionado) {
        Juego.id_juegoSeleccionado = id_juegoSeleccionado;
    }

    public static String getNombreJuego() {
        return nombreJuego;
    }

    public static void setNombreJuego(String nombreJuego) {
        Juego.nombreJuego = nombreJuego;
    }

    public Boolean getWinLose() {
        return winLose;
    }

    public int getFriend_id() {
        return friend_id;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String descripcion) {
        Descripcion = descripcion;
    }

    public String getImagen() {
        return Imagen;
    }

    public void setImagen(String imagen) {
        Imagen = imagen;
    }

    public int getAño() {
        return Año;
    }

    public void setAño(int año) {
        Año = año;
    }

    public String getHoras() {
        return horas;
    }

    public void setHoras(String horas) {
        this.horas = horas;
    }

    public int getPlayTime() {
        return playTime;
    }

    public void setPlayTime(int playTime) {
        this.playTime = playTime;
    }

    public int getPlayTime2Weeks() {
        return playTime2Weeks;
    }

    public void setPlayTime2Weeks(int playTime2Weeks) {
        this.playTime2Weeks = playTime2Weeks;
    }

    public int getSteamID() {
        return steamID;
    }

    public void setSteamID(int steamID) {
        this.steamID = steamID;
    }

    public int getIgdbID() {
        return igdbID;
    }

    public void setIgdbID(int igdbID) {
        this.igdbID = igdbID;
    }

    public String getSteamImagen() {
        return steamImagen;
    }

    public void setSteamImagen(String steamImagen) {
        this.steamImagen = steamImagen;
    }
}
