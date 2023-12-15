package m13dam.grupo4.gamepinnacle.Interfaces.SteamWebApi;

import m13dam.grupo4.gamepinnacle.BuildConfig;
import m13dam.grupo4.gamepinnacle.Classes.Other.CurrentSession;
import m13dam.grupo4.gamepinnacle.Classes.SteamWebApi.GetNewsForAppResponse;
import m13dam.grupo4.gamepinnacle.Classes.SteamWebApi.GetOwnedGamesResponse;
import m13dam.grupo4.gamepinnacle.Classes.SteamWebApi.GetPlayerAchievementsResponse;
import m13dam.grupo4.gamepinnacle.Classes.SteamWebApi.GetPlayerSummariesResponse;
import m13dam.grupo4.gamepinnacle.Classes.SteamWebApi.GetRecentlyPlayedGamesResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface SteamWebApiService {

    @GET("ISteamNews/GetNewsForApp/v0002/")
    Call<GetNewsForAppResponse> getNewsForApp(
        @Query("key") String steamApiKey,
        @Query("appid") int steamAppId,
        @Query("count") int count,
        @Query("maxlength") int maxLength,
        @Query("format") String format
    );
    @GET("IPlayerService/GetOwnedGames/v0001/")
    Call<GetOwnedGamesResponse> getOwnedGamesByUser(
            @Query("key") String steamApiKey,
            @Query("steamid") String userId,
            @Query("include_appinfo") Boolean infoGames,
            @Query("include_played_free_games") Boolean freeGames,
            @Query("format") String format
    );

    @GET("IPlayerService/GetRecentlyPlayedGames/v0001/")
    Call<GetRecentlyPlayedGamesResponse> getRecentlyGamesByUser(
            @Query("key") String steamApiKey,
            @Query("steamid") String userId,
            @Query("count") int numeroJuegos,
            @Query("format") String format
    );

    @GET("ISteamUser/GetPlayerSummaries/v0002/")
    Call<GetPlayerSummariesResponse> getPlayerSummaries(
            @Query("key") String steamApiKey,
            @Query("steamids") String steamPlayerids,
            @Query("format") String format
    );

    @GET("ISteamUserStats/GetPlayerAchievements/v0001/")
    Call<GetPlayerAchievementsResponse> getPlayerAchievements(
            @Query("key") String steamApiKey,
            @Query("steamid") String userId,
            @Query("appid") String appId,
            @Query("l") String languaje,
            @Query("format") String format
    );

}
