package m13dam.grupo4.gamepinnacle.Classes.SteamWebApi;

import com.google.gson.annotations.SerializedName;

public class GetPlayerAchievements {

    @SerializedName("steamID")
    private String steamID;

    @SerializedName("gameName")
    private String gameName;

    @SerializedName("success")
    private boolean success;

    public String getSteamID() {
        return steamID;
    }

    public String getGameName() {
        return gameName;
    }

    public boolean isSuccess() {
        return success;
    }
}
