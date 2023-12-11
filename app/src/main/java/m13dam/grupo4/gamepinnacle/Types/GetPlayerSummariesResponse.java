package m13dam.grupo4.gamepinnacle.Types;

import com.google.gson.annotations.SerializedName;

public class GetPlayerSummariesResponse {

    @SerializedName("response")
    private GetPlayerSummaries playerSummaries;

    public GetPlayerSummaries getPlayerSummaries() {
        return playerSummaries;
    }
}
