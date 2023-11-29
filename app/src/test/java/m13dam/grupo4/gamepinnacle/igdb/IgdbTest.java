package m13dam.grupo4.gamepinnacle.igdb;

import com.api.igdb.apicalypse.APICalypse;
import com.api.igdb.apicalypse.Sort;
import com.api.igdb.exceptions.RequestException;
import com.api.igdb.request.IGDBWrapper;
import com.api.igdb.request.ProtoRequestKt;
import com.api.igdb.utils.Endpoints;
import com.google.protobuf.InvalidProtocolBufferException;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;

import proto.Game;
import proto.GameResult;

public class IgdbTest {

    @Test
    public void GetWrapper() {

        IGDBWrapper wrapper = Igdb.GetWrapper();

        if (wrapper != null) {
            System.out.println(wrapper);
        }

        Assert.assertNotNull(wrapper);
    }

    @Test
    public void GetGames() {
        IGDBWrapper wrapper = null;
        List<Game> games = null;

        try {
            wrapper = Igdb.GetWrapper();
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (wrapper == null) {
            Assert.fail();
        }

        APICalypse apiCalypse = new APICalypse();

        try {
            games = ProtoRequestKt.games(wrapper, apiCalypse);
        } catch (RequestException e) {
            e.printStackTrace();
        }

        if (games == null) {
            Assert.fail();
        }

        System.out.println(games);

        Assert.assertNotNull(games);
    }

}
