package m13dam.grupo4.gamepinnacle.Classes.TwitchApi;

import m13dam.grupo4.gamepinnacle.Interfaces.TwitchApi.TwitchApiService;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TwitchApi {

    private static Retrofit retrofit;
    private static TwitchApiService twitchApiService;

    private static Retrofit getRetrofit() {

        if (retrofit != null) {
            return retrofit;
        }

        retrofit = new Retrofit.Builder()
                .baseUrl("https://id.twitch.tv")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit;

    }

    public static TwitchApiService getTwitchApiService() {

        if (twitchApiService != null) {
            return twitchApiService;
        }

        twitchApiService = getRetrofit().create(TwitchApiService.class);

        return twitchApiService;
    }

}
