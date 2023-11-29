package m13dam.grupo4.gamepinnacle.twitch;

import com.api.igdb.utils.TwitchToken;

import org.junit.Assert;
import org.junit.Test;

public class TwitchTest {

    @Test
    public void GetTwitchToken() {
        TwitchToken token = Twitch.GetTwitchToken();

        if (token != null) {
            System.out.println(token);
        }

        Assert.assertNotNull(token);
    }

}
