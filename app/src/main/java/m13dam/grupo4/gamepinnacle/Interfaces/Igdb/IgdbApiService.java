package m13dam.grupo4.gamepinnacle.Interfaces.Igdb;

import java.util.List;

import m13dam.grupo4.gamepinnacle.Classes.IgdbApi.IgdbGame;
import m13dam.grupo4.gamepinnacle.Classes.TwitchApi.GetAccessToken;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface IgdbApiService {

    @POST("v4/games")
    Call<List<IgdbGame>> getGames(
            @Header("Client-ID") String TwitchClientID,
            @Header("Authorization") String TwitchToken,
            @Header("Accept") String DataType,
            @Body String params
    );

}
