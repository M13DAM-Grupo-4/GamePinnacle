package m13dam.grupo4.gamepinnacle.Classes.SteamWebApi;

import com.google.gson.annotations.SerializedName;

public class NewsItems {

    @SerializedName("gid")
    private long gid;

    @SerializedName("title")
    private String title;

    @SerializedName("contents")
    private String contents;

    public long getGid() {
        return gid;
    }

    public String getTitle() {
        return title;
    }

    public String getContents() {
        return contents;
    }
}
