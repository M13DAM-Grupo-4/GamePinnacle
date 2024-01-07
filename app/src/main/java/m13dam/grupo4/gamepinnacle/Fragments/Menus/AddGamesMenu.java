package m13dam.grupo4.gamepinnacle.Fragments.Menus;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.api.igdb.apicalypse.APICalypse;
import com.api.igdb.apicalypse.Sort;
import com.api.igdb.exceptions.RequestException;
import com.api.igdb.request.IGDBWrapper;
import com.api.igdb.request.ProtoRequestKt;

import java.util.ArrayList;
import java.util.List;

import m13dam.grupo4.gamepinnacle.Adapters.AddGameListAdapter;
import m13dam.grupo4.gamepinnacle.Adapters.RecentlyPlayedGamesAdapter;
import m13dam.grupo4.gamepinnacle.BuildConfig;
import m13dam.grupo4.gamepinnacle.Classes.Other.CurrentSession;
import m13dam.grupo4.gamepinnacle.Classes.Other.Juego;
import m13dam.grupo4.gamepinnacle.Classes.SteamWebApi.Games;
import m13dam.grupo4.gamepinnacle.R;
import proto.Game;

public class AddGamesMenu extends Fragment {

    public AddGamesMenu() {
        // Required empty public constructor
    }

    public static AddGamesMenu newInstance(String param1, String param2) {
        AddGamesMenu fragment = new AddGamesMenu();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_game_menu, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        List<Juego> juegos = new ArrayList<>();

        // Get Data

        Thread getData = new Thread(() -> {

            try {
                IGDBWrapper wrapper = IGDBWrapper.INSTANCE;
                wrapper.setCredentials(BuildConfig.twitchclientid, CurrentSession.getTwitchToken());

                APICalypse apicalypse = new APICalypse().fields("*, websites.*, cover.*").limit(10).sort("aggregated_rating", Sort.DESCENDING);
                List<Game> games = ProtoRequestKt.games(wrapper, apicalypse);

                for (Game g : games) {
                    juegos.add(new Juego(1, g.getName(), g.getSummary(), "https:" + g.getCover().getUrl()));
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        });
        getData.start();

        // Set UI

        new Thread(() -> {
            try {
                getData.join();

                if (juegos.size() < 1){
                    return;
                }

                getActivity().runOnUiThread(() -> {

                    EditText tv = view.findViewById(R.id.add_games_input);
                    RecyclerView rv = view.findViewById(R.id.add_games_menu_recycler);

                    AddGameListAdapter addGameAdapter = new AddGameListAdapter(getActivity(), juegos);
                    rv.setAdapter(addGameAdapter);
                    rv.setLayoutManager(new LinearLayoutManager(getActivity()));

                    tv.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {


                        }

                        @Override
                        public void afterTextChanged(Editable s) {

                            new Thread(() -> {

                                try {
                                    IGDBWrapper wrapper = IGDBWrapper.INSTANCE;
                                    wrapper.setCredentials(BuildConfig.twitchclientid, CurrentSession.getTwitchToken());

                                    APICalypse apicalypse = new APICalypse().fields("*, websites.*, cover.*").search(tv.getText().toString()).limit(10);
                                    List<Game> games = ProtoRequestKt.games(wrapper, apicalypse);

                                    List<Juego> juegos2 = new ArrayList<>();

                                    for (Game g : games) {
                                        juegos2.add(new Juego(1, g.getName(), g.getSummary(), "https:" + g.getCover().getUrl()));
                                    }

                                    getActivity().runOnUiThread(() -> {
                                        AddGameListAdapter addGameAdapter = new AddGameListAdapter(getActivity(), juegos2);
                                        rv.setAdapter(addGameAdapter);
                                        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
                                    });

                                } catch (RequestException e) {
                                    System.out.println(e.getStatusCode());
                                    e.printStackTrace();
                                }

                            }).start();
                        }
                    });

                });

            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).start();

    }
}