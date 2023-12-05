package m13dam.grupo4.gamepinnacle.Types;

public class Usuario {

    private String correo;
    private String usuario;
    private int  password;

    public Usuario(String correo, String usuario, int password) {
        this.correo = correo;
        this.usuario = usuario;
        this.password = password;
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

    public int getPassword() {
        return password;
    }

    public void setPassword(int password) {
        this.password = password;
    }
}
