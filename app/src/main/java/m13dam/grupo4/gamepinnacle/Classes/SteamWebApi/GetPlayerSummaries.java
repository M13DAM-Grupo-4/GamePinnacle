package m13dam.grupo4.gamepinnacle.Classes.SteamWebApi;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetPlayerSummaries {

    @SerializedName("players")
    private List<Player> players;

    public List<Player> getPlayers() {
        return players;
    }
}
