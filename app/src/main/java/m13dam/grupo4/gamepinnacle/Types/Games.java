package m13dam.grupo4.gamepinnacle.Types;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Games {
    @SerializedName("appid")
    private String appid;
    @SerializedName(("name"))
    private String name;
    @SerializedName("playtime_forever")
    private String playtime_forever;
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

}
