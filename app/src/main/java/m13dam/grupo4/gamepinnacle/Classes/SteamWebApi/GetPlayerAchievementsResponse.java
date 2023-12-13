package m13dam.grupo4.gamepinnacle.Classes.SteamWebApi;

import com.google.gson.annotations.SerializedName;

public class GetPlayerAchievementsResponse {

    @SerializedName("playerstats")
    private GetPlayerAchievements playerAchievements;

    public GetPlayerAchievements getPlayerAchievements() {
        return playerAchievements;
    }
}
