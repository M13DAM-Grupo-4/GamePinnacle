package m13dam.grupo4.gamepinnacle.twitch;

import com.api.igdb.utils.TwitchToken;

import org.junit.Assert;
import org.junit.Test;

public class TwitchTest {

    @Test
    public void GetToken() {
        TwitchToken token = Twitch.GetToken();

        if (token == null) {
            Assert.fail();
        }

        System.out.println(token);
    }

}
