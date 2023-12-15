package m13dam.grupo4.gamepinnacle.Classes.Other;

import m13dam.grupo4.gamepinnacle.Classes.SteamWebApi.Games;

public class CurrentSession {

    private static Usuario usuario;

    public static Usuario getUsuario() {
        return usuario;
    }

    public static void setUsuario(Usuario usuario) {
        CurrentSession.usuario = usuario;
    }
    private static Games juego;
}
