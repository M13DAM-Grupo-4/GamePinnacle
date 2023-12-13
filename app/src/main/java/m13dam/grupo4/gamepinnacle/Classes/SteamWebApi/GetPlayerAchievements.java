package m13dam.grupo4.gamepinnacle.Classes.SteamWebApi;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetPlayerAchievements {

    @SerializedName("steamID")
    private String steamID;

    @SerializedName("gameName")
    private String gameName;

    @SerializedName("achievements")
    List<Archievement> archivements;

    @SerializedName("success")
    private boolean success;

    public String getSteamID() {
        return steamID;
    }

    public String getGameName() {
        return gameName;
    }

    public List<Archievement> getArchivements() {
        return archivements;
    }

    public boolean isSuccess() {
        return success;
    }
}
