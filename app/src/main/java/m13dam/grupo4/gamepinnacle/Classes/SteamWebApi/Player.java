package m13dam.grupo4.gamepinnacle.Classes.SteamWebApi;

import com.google.gson.annotations.SerializedName;

public class Player {

    @SerializedName("steamid")
    private String steamid;

    @SerializedName("personaname")
    private String name;

    @SerializedName("avatarfull")
    private String avatar;

    public String getSteamid() {
        return steamid;
    }

    public String getName() {
        return name;
    }

    public String getAvatar() {
        return avatar;
    }
}
