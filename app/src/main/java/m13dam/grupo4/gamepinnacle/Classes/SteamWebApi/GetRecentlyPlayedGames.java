package m13dam.grupo4.gamepinnacle.Classes.SteamWebApi;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetRecentlyPlayedGames {
    @SerializedName("total_count")
    private int total_count;
    @SerializedName("games")
    private List<Games> games;

    public int getTotal_count() {
        return total_count;
    }

    public List<Games> getGames() {
        return games;
    }
}
