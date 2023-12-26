package m13dam.grupo4.gamepinnacle.Classes.SteamWebApi;

import com.google.gson.annotations.SerializedName;

public class Games {
    @SerializedName("appid")
    private String appid;
    @SerializedName("name")
    private String name;
    @SerializedName("playtime_forever")
    private String playtime_forever;
    @SerializedName("playtime_2weeks")
    private String playtime_2weeks;
    @SerializedName(("img_icon_url"))
    private String img_icon_url;


    public String getAppid() {
        return appid;
    }

    public String getName() {
        return name;
    }

    public String getPlaytime_forever() {
        return playtime_forever;
    }

    public String getImg_icon_url() {
        return img_icon_url;
    }
    public String getPlaytime_2weeks() {
        return playtime_2weeks;
    }

    public String getPlaytime_2weeks_on_hours() {
        return String.valueOf((Integer.parseInt(playtime_2weeks)/60));
    }

    public String getPlaytime_forever_on_hours() {
        return String.valueOf((Integer.parseInt(playtime_forever)/60));
    }

}
