package m13dam.grupo4.gamepinnacle.Interfaces;

import m13dam.grupo4.gamepinnacle.BuildConfig;
import m13dam.grupo4.gamepinnacle.Types.GetNewsForAppResponse;
import m13dam.grupo4.gamepinnacle.Types.GetOwnedGamesResponse;
import m13dam.grupo4.gamepinnacle.Types.GetPlayerSummariesResponse;
import m13dam.grupo4.gamepinnacle.Types.GetRecentlyPlayedGamesResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface SteamWebApiService {

    String steamApiKey = BuildConfig.steamapikey;

    @GET("ISteamNews/GetNewsForApp/v0002/?key=" + steamApiKey)
    Call<GetNewsForAppResponse> getNewsForApp(
        @Query("appid") int steamAppId,
        @Query("count") int count,
        @Query("maxlength") int maxLength,
        @Query("format") String format
    );
    @GET("IPlayerService/GetOwnedGames/v0001/?key=" + steamApiKey)
    Call<GetOwnedGamesResponse> getOwnedGamesByUser(
            @Query("steamid") String userId,
            @Query("include_appinfo") Boolean infoGames,
            @Query("include_played_free_games") Boolean freeGames,
            @Query("format") String format
    );

    @GET("IPlayerService/GetRecentlyPlayedGames/v0001/?key=" + steamApiKey)
    Call<GetRecentlyPlayedGamesResponse> getRecentlyGamesByUser(
            @Query("steamid") String userId,
            @Query("count") int numeroJuegos,
            @Query("format") String format
    );

    @GET("ISteamUser/GetPlayerSummaries/v0002/?key=" + steamApiKey)
    Call<GetPlayerSummariesResponse> getPlayerSummaries(
            @Query("steamids") String steamPlayerids,
            @Query("format") String format
    );

}
