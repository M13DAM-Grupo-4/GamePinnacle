package m13dam.grupo4.gamepinnacle.Classes.Other;

public class Usuario {

    private String correo;
    private String usuario;
    private String password;
    private String steamid;

    public Usuario(String correo, String usuario, String password) {
        this.correo = correo;
        this.usuario = usuario;
        this.password = password;
    }

    public Usuario(String correo, String usuario, String password, String steamid) {
        this.correo = correo;
        this.usuario = usuario;
        this.password = password;
        this.steamid = steamid;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSteamid() {
        return steamid;
    }

    public void setSteamid(String steamid) {
        this.steamid = steamid;
    }
}
