package m13dam.grupo4.gamepinnacle.Classes.IgdbApi;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class IgdbGame {

    @SerializedName("id")
    private int igdbid;

    @SerializedName("name")
    private String Nombre;

    @SerializedName("summary")
    private String Summary;

    @SerializedName("websites")
    private List<IgdbWebsite> websites;

    public int getIgdbid() {
        return igdbid;
    }

    public String getNombre() {
        return Nombre;
    }

    public String getSummary() {
        return Summary;
    }

    public List<IgdbWebsite> getWebsites() {
        return websites;
    }
}
