package m13dam.grupo4.gamepinnacle.Classes.SteamWebApi;

import com.google.gson.annotations.SerializedName;

public class Achievement {

    @SerializedName("apiname")
    private String apiName;

    @SerializedName("achieved")
    private int achieved;

    @SerializedName("unlocktime")
    private int unlockTime;

    @SerializedName("name")
    private String name;

    @SerializedName("description")
    private String description;

    public String getApiName() {
        return apiName;
    }

    public int getAchieved() {
        return achieved;
    }

    public int getUnlockTime() {
        return unlockTime;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
