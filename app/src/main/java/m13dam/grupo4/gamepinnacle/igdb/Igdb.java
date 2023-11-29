package m13dam.grupo4.gamepinnacle.igdb;

import com.api.igdb.request.IGDBWrapper;
import com.api.igdb.utils.TwitchToken;

import java.util.HashMap;
import java.util.Map;

import m13dam.grupo4.gamepinnacle.BuildConfig;
import m13dam.grupo4.gamepinnacle.twitch.Twitch;

public class Igdb {

    public static IGDBWrapper GetWrapper() {
        TwitchToken token = Twitch.GetToken();
        if (token == null){
            return null;
        }
        IGDBWrapper wrapper = IGDBWrapper.INSTANCE;
        wrapper.setCredentials(BuildConfig.twitchclientid, token.getAccess_token());
        return wrapper;
    }

}
