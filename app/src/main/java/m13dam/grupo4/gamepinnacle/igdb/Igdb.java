package m13dam.grupo4.gamepinnacle.igdb;

import com.api.igdb.request.IGDBWrapper;

import java.util.HashMap;
import java.util.Map;

import m13dam.grupo4.gamepinnacle.twitch.Twitch;

public class Igdb {

    public static IGDBWrapper GetWrapper() {
        IGDBWrapper wrapper = IGDBWrapper.INSTANCE;
        Map<String, String> proxyHeaders = new HashMap<String, String>() {{
            put("x-api-key", Twitch.GetToken().getAccess_token());
        }};
        wrapper.setupProxy("api.igdb.com/v4", proxyHeaders);
        return wrapper;
    }

}
