package m13dam.grupo4.gamepinnacle.Classes.SteamWebApi;

import com.google.gson.annotations.SerializedName;

public class GetNewsForAppResponse {

    @SerializedName("appnews")
    private GetNewsForApp appNews;

    public GetNewsForApp getAppNews() {
        return appNews;
    }
}
