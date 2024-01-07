package m13dam.grupo4.gamepinnacle.Classes.IgdbApi;

import m13dam.grupo4.gamepinnacle.Interfaces.Igdb.IgdbApiService;
import m13dam.grupo4.gamepinnacle.Interfaces.TwitchApi.TwitchApiService;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class IgdbApi {

    private static Retrofit retrofit;
    private static IgdbApiService igdbApiService;

    private static Retrofit getRetrofit() {

        if (retrofit != null) {
            return retrofit;
        }

        retrofit = new Retrofit.Builder()
                .baseUrl("https://api.igdb.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit;

    }

    public static IgdbApiService getIgdbApiService() {

        if (igdbApiService != null) {
            return igdbApiService;
        }

        igdbApiService = getRetrofit().create(IgdbApiService.class);

        return igdbApiService;
    }

}
