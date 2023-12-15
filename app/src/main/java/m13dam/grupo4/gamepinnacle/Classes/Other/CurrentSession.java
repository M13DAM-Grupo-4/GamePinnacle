package m13dam.grupo4.gamepinnacle.Classes.Other;

import m13dam.grupo4.gamepinnacle.DataBases.DataBaseManager;

public class CurrentSession {

    private static Usuario usuario;
    private static String steamApiKey;

    public static Usuario getUsuario() {
        return usuario;
    }

    public static void setUsuario(Usuario usuario) {
        CurrentSession.usuario = usuario;
    }

    public static String getSteamApiKey() {

        return steamApiKey;
    }

    public static void UpdateSteamApiKey(){
        steamApiKey = DataBaseManager.getSteamApiKey(usuario.getId());
    }

    public static void setSteamApiKey(String steamApiKey) {
        CurrentSession.steamApiKey = steamApiKey;
    }
}
