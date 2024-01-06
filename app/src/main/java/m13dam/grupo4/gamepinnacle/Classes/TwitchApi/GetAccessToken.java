package m13dam.grupo4.gamepinnacle.Classes.TwitchApi;

import com.google.gson.annotations.SerializedName;

public class GetAccessToken {

    @SerializedName("access_token")
    private String accessToken;

    @SerializedName("expires_in")
    private int expiresIn;

    @SerializedName("token_type")
    private String tokenType;

    public String getAccessToken() {
        return accessToken;
    }

    public int getExpiresIn() {
        return expiresIn;
    }

    public String getTokenType() {
        return tokenType;
    }
}
