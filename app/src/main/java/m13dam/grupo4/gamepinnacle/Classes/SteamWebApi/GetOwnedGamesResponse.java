package m13dam.grupo4.gamepinnacle.Classes.SteamWebApi;

import com.google.gson.annotations.SerializedName;

public class GetOwnedGamesResponse {
    @SerializedName("response")
    private GetOwnedGames getOwnedGames;

    public GetOwnedGames getGetOwnedGames() {
        return getOwnedGames;
    }
}
