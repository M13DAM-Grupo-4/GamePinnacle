package m13dam.grupo4.gamepinnacle.Classes.Other;

public class Usuario {

    private int id;

    private String correo;
    private String usuario;
    private String password;
    private String steamid;
    private String steamapikey;

    public Usuario(int id, String correo, String usuario, String password) {
        this.id = id;
        this.correo = correo;
        this.usuario = usuario;
        this.password = password;
    }

    public Usuario(int id, String correo, String usuario, String password, String steamid, String steamapikey) {
        this.id = id;
        this.correo = correo;
        this.usuario = usuario;
        this.password = password;
        this.steamid = steamid;
        this.steamapikey = steamapikey;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getSteamapikey() {
        return steamapikey;
    }

    public void setSteamapikey(String steamapikey) {
        this.steamapikey = steamapikey;
    }
}
