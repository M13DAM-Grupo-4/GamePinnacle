package m13dam.grupo4.gamepinnacle.Classes.SteamWebApi;

import com.google.gson.annotations.SerializedName;

public class GetRecentlyPlayedGamesResponse {
    @SerializedName("response")
    private GetRecentlyPlayedGames getRecentGames;

    public GetRecentlyPlayedGames getRecentGames() {
        return getRecentGames;
    }
}
