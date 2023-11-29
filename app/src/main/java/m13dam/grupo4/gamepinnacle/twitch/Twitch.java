package m13dam.grupo4.gamepinnacle.twitch;

import com.api.igdb.request.TwitchAuthenticator;
import com.api.igdb.utils.TwitchToken;

import m13dam.grupo4.gamepinnacle.BuildConfig;

public class Twitch {

    public static TwitchToken GetToken() {
        String TwitchClientId = BuildConfig.twitchclientid;
        String TwitchClientSecret = BuildConfig.twitchclientsecret;

        TwitchAuthenticator tAuth = TwitchAuthenticator.INSTANCE;
        TwitchToken token = tAuth.requestTwitchToken(TwitchClientId, TwitchClientSecret);

        return token;
    }

}
