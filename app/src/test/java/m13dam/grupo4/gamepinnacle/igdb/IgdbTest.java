package m13dam.grupo4.gamepinnacle.igdb;

import com.api.igdb.request.IGDBWrapper;

import org.junit.Assert;
import org.junit.Test;

public class IgdbTest {

    @Test
    public void GetWrapper() {

        IGDBWrapper wrapper = Igdb.GetWrapper();

        if (wrapper != null) {
            System.out.println(wrapper);
        }

        Assert.assertNotNull(wrapper);
    }

}
