package m13dam.grupo4.gamepinnacle.Types;

import com.google.gson.annotations.SerializedName;

import java.util.List;


public class GetNewsForApp {

    @SerializedName("appid")
    private int appid;
    @SerializedName("count")
    private int count;

    @SerializedName("newsitems")
    List<NewsItems> newsitems;

    public int getAppid() {
        return appid;
    }

    public int getCount() {
        return count;
    }

    public List<NewsItems> getNewsitems() {
        return newsitems;
    }
}
