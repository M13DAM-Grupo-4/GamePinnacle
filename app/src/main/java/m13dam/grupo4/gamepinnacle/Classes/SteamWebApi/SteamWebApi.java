package m13dam.grupo4.gamepinnacle.Classes.SteamWebApi;

import m13dam.grupo4.gamepinnacle.Interfaces.SteamWebApi.SteamWebApiService;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SteamWebApi {

    private static Retrofit retrofit;
    private static SteamWebApiService steamWebApiService;

    private static Retrofit getRetrofit() {

        if (retrofit != null) {
            return retrofit;
        }

        retrofit = new Retrofit.Builder()
                .baseUrl("https://api.steampowered.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit;

    }

    public static SteamWebApiService getSteamWebApiService() {

        if (steamWebApiService != null) {
            return steamWebApiService;
        }

        steamWebApiService = getRetrofit().create(SteamWebApiService.class);

        return steamWebApiService;
    }

}
