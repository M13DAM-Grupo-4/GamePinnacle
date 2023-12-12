package m13dam.grupo4.gamepinnacle.Classes.Other;

public class CurrentSession {

    private static Usuario usuario;

    public static Usuario getUsuario() {
        return usuario;
    }

    public static void setUsuario(Usuario usuario) {
        CurrentSession.usuario = usuario;
    }
}
