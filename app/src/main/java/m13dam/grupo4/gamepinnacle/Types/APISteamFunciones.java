package m13dam.grupo4.gamepinnacle.Types;

import com.lukaspradel.steamapi.core.exception.SteamApiException;
import com.lukaspradel.steamapi.data.json.ownedgames.Game;
import com.lukaspradel.steamapi.data.json.ownedgames.GetOwnedGames;
import com.lukaspradel.steamapi.data.json.recentlyplayedgames.GetRecentlyPlayedGames;
import com.lukaspradel.steamapi.webapi.client.SteamWebApiClient;
import com.lukaspradel.steamapi.webapi.request.GetOwnedGamesRequest;
import com.lukaspradel.steamapi.webapi.request.GetRecentlyPlayedGamesRequest;

import java.util.ArrayList;
import java.util.List;

import m13dam.grupo4.gamepinnacle.BuildConfig;
import m13dam.grupo4.gamepinnacle.Types.Juegos;

public class APISteamFunciones {

   static SteamWebApiClient client = new SteamWebApiClient.SteamWebApiClientBuilder(BuildConfig.steamapikey).build();
   static GetOwnedGamesRequest juegosUsuarios = new GetOwnedGamesRequest.GetOwnedGamesRequestBuilder("76561198008676395").includeAppInfo(true).includePlayedFreeGames(true).buildRequest();
   static GetRecentlyPlayedGamesRequest juegosUsuarioReciente = new GetRecentlyPlayedGamesRequest.GetRecentlyPlayedGamesRequestBuilder("76561198008676395").count(3).buildRequest();

   static ArrayList <Juegos> recientesTres;
   static ArrayList <Juegos> totalJuegos;

    public static ArrayList<Juegos> UltimosTresJuegos () throws SteamApiException {

        recientesTres = new ArrayList<>();
        GetRecentlyPlayedGames juegosRecientes = client.processRequest(juegosUsuarioReciente);
        List<com.lukaspradel.steamapi.data.json.recentlyplayedgames.Game> jj = juegosRecientes.getResponse().getGames();

        for ( com.lukaspradel.steamapi.data.json.recentlyplayedgames.Game j : jj) {
            recientesTres.add(new Juegos (j.getImgIconUrl(),j.getName(),j.getPlaytimeForever().toString(),j.getPlaytime2weeks().toString()));
        }
        return recientesTres;
    }

    public static ArrayList<Juegos> TodosLosJuegos () throws SteamApiException {

        totalJuegos = new ArrayList<>();

        GetOwnedGames juegosPoseidos = client.processRequest(juegosUsuarios);
        List<Game> jj = juegosPoseidos.getResponse().getGames();

        for(Game j : jj) {
            totalJuegos.add(new Juegos(j.getImgIconUrl(),j.getName(),j.getPlaytimeForever().toString()));
        }


        return totalJuegos;
    }
    
}