package m13dam.grupo4.gamepinnacle.Fragments.Menus;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import m13dam.grupo4.gamepinnacle.Adapters.RecentlyPlayedGamesAdapter;
import m13dam.grupo4.gamepinnacle.Classes.Other.CurrentSession;
import m13dam.grupo4.gamepinnacle.Classes.SteamWebApi.Games;
import m13dam.grupo4.gamepinnacle.Classes.SteamWebApi.GetOwnedGamesResponse;
import m13dam.grupo4.gamepinnacle.Classes.SteamWebApi.SteamWebApi;
import m13dam.grupo4.gamepinnacle.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link GameListMenu#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GameListMenu extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private ArrayList<Games> listaJuegos = new ArrayList<>();

    public GameListMenu() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment GameListMenu.
     */
    // TODO: Rename and change types and number of parameters
    public static GameListMenu newInstance(String param1, String param2) {
        GameListMenu fragment = new GameListMenu();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_game_list_menu, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);




        SteamWebApi.getSteamWebApiService().getOwnedGamesByUser(
                CurrentSession.getSteamApiKey(),
                CurrentSession.getUsuario().getSteamid(),
                true,
                true,
                "json").enqueue(new Callback<GetOwnedGamesResponse>() {
            @Override
            public void onResponse(Call<GetOwnedGamesResponse> call, Response<GetOwnedGamesResponse> response) {
                System.out.println(call.request());
                System.out.println(response.code());

                if (response.code() == 200) {

                    if (response.body().getGetOwnedGames().getGames() == null) {
                        System.out.println("GetRecentlyPlayedGames is null");
                        return;
                    }

                    for (Games g : response.body().getGetOwnedGames().getGames()) {

                        listaJuegos.add(g);

                    }
                    listJuegosSteam();
                }
            }

            @Override
            public void onFailure(Call<GetOwnedGamesResponse> call, Throwable t) {
                System.out.println(t.getMessage());
            }
        });

    }

    private void listJuegosSteam () {
        RecentlyPlayedGamesAdapter recycleview_jvm = new RecentlyPlayedGamesAdapter(getActivity(), listaJuegos, "all");

        RecyclerView recyclerView = getActivity().findViewById(R.id.reciclePrueba);

        recyclerView.setAdapter(recycleview_jvm);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }
}