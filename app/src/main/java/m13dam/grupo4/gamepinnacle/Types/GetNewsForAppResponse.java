package m13dam.grupo4.gamepinnacle.Types;

import com.google.gson.annotations.SerializedName;

public class GetNewsForAppResponse {

    @SerializedName("appnews")
    private GetNewsForApp appNews;

    public GetNewsForApp getAppNews() {
        return appNews;
    }
}
