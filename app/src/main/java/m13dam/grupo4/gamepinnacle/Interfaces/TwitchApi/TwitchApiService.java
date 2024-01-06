package m13dam.grupo4.gamepinnacle.Interfaces.TwitchApi;

import m13dam.grupo4.gamepinnacle.Classes.TwitchApi.GetAccessToken;
import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface TwitchApiService {

    @POST("oauth2/token")
    Call<GetAccessToken> getAccessToken(
        @Query("client_id") String clientId,
        @Query("client_secret") String clientSecret,
        @Query("grant_type") String grantType
    );

}
