package m13dam.grupo4.gamepinnacle.Fragments.Menus;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

import m13dam.grupo4.gamepinnacle.Classes.SteamWebApi.Games;
import m13dam.grupo4.gamepinnacle.R;
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

        ArrayList<Games> juegos = new ArrayList<>();

        // Get Data

        Thread getData = new Thread(() -> {

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

                });

            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

    }
}