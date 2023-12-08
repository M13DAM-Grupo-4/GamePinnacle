package m13dam.grupo4.gamepinnacle.Interfaces;

import m13dam.grupo4.gamepinnacle.BuildConfig;
import m13dam.grupo4.gamepinnacle.Types.GetNewsForAppResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface SteamWebApiService {

    String steamApiKey = BuildConfig.steamapikey;

    @GET("ISteamNews/GetNewsForApp/v0002/?key=" + steamApiKey)
    Call<GetNewsForAppResponse> getNewsForApp(
        @Query("steamid") String steamUserId,
        @Query("appid") int steamAppId,
        @Query("count") int count,
        @Query("maxlength") int maxLength,
        @Query("format") String format
    );

}
