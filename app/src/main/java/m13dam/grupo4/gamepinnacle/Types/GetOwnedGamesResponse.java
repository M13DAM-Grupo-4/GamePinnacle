package m13dam.grupo4.gamepinnacle.Types;

import com.google.gson.annotations.SerializedName;

public class GetOwnedGamesResponse {
    @SerializedName("response")
    private GetOwnedGames getOwnedGames;

    public GetOwnedGames getGetOwnedGames() {
        return getOwnedGames;
    }
}
