package m13dam.grupo4.gamepinnacle.Classes.SteamWebApi;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetOwnedGames {
    @SerializedName("game_count")
    private int game_count;
    @SerializedName("games")
    private List<Games> games;

    public int getGame_count() {
        return game_count;
    }

    public List<Games> getGames() {
        return games;
    }
}
