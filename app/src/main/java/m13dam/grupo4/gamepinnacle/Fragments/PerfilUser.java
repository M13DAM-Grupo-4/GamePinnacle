package m13dam.grupo4.gamepinnacle.Fragments;



import android.content.Intent;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import m13dam.grupo4.gamepinnacle.Adapters.AdaptadorPrincipal;
import m13dam.grupo4.gamepinnacle.R;
import m13dam.grupo4.gamepinnacle.SteamWebApi;
import m13dam.grupo4.gamepinnacle.Types.CurrentSession;
import m13dam.grupo4.gamepinnacle.Types.Games;
import m13dam.grupo4.gamepinnacle.Types.GetOwnedGamesResponse;
import m13dam.grupo4.gamepinnacle.Types.GetPlayerSummariesResponse;
import m13dam.grupo4.gamepinnacle.Types.GetRecentlyPlayedGamesResponse;
import m13dam.grupo4.gamepinnacle.Types.SteamUserId;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PerfilUser#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PerfilUser extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private ImageView avatarUsuario;
    private TextView userMail;
    private TextView userName;
    private TextView played_hours;
    private TextView totalGames;
    private TextView numberOfFriends;

    private ArrayList <Games> listaJuegos = new ArrayList<>();
    private ActivityResultLauncher<Intent> pickImageLauncher;


    public PerfilUser() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PerfilUser.
     */
    // TODO: Rename and change types and number of parameters
    public static PerfilUser newInstance(String param1, String param2) {
        PerfilUser fragment = new PerfilUser();
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
        return inflater.inflate(R.layout.fragment_perfil_user, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        avatarUsuario = view.findViewById(R.id.perfil_user_avatar);
        userMail = view.findViewById(R.id.perfil_user_email);
        userName= view.findViewById(R.id.perfil_user_username);
        totalGames = view.findViewById(R.id.perfil_user_juegos);
        played_hours = view.findViewById(R.id.perfil_user_horas_jugadas);
        numberOfFriends = view.findViewById(R.id.perfil_user_amigos);
        userMail.setText(CurrentSession.getMail());
        userName.setText(CurrentSession.getUserName());


        SteamWebApi.getSteamWebApiService().getRecentlyGamesByUser(
                CurrentSession.getSteamId(),
                3,
                "json").enqueue(new Callback<GetRecentlyPlayedGamesResponse>() {
            @Override
            public void onResponse(Call<GetRecentlyPlayedGamesResponse> call, Response<GetRecentlyPlayedGamesResponse> response) {
                System.out.println(call.request());
                if (response.code() == 200) {

                    for(Games g : response.body().getRecentGames().getGames()) {

                        listaJuegos.add(g);

                    }
                    listaJuegosRecientes();
                }
            }

            @Override
            public void onFailure(Call<GetRecentlyPlayedGamesResponse> call, Throwable t) {
                System.out.println(t.getMessage());
            }
        });

        SteamWebApi.getSteamWebApiService().getOwnedGamesByUser(
                CurrentSession.getSteamId(),
                true,
                true,
                "json").enqueue(new Callback<GetOwnedGamesResponse>() {
            @Override
            public void onResponse(Call<GetOwnedGamesResponse> call, Response<GetOwnedGamesResponse> response) {
                System.out.println(call.request());
                if (response.code() == 200) {

                    totalGames.setText(String.valueOf(response.body().getGetOwnedGames().getGame_count()));

                    long playedTimeCount = 0L;
                    long playedTimeHours;

                    for (Games g : response.body().getGetOwnedGames().getGames()) {
                        playedTimeCount += Long.parseLong(g.getPlaytime_forever());
                    }
                    playedTimeHours = playedTimeCount/60;

                    played_hours.setText(String.valueOf(playedTimeHours));

                }
            }

            @Override
            public void onFailure(Call<GetOwnedGamesResponse> call, Throwable t) {
                System.out.println(t.getMessage());
            }
        });

        SteamWebApi.getSteamWebApiService().getPlayerSummaries(
                CurrentSession.getSteamId(),
                "json"
        ).enqueue(new Callback<GetPlayerSummariesResponse>() {
            @Override
            public void onResponse(Call<GetPlayerSummariesResponse> call, Response<GetPlayerSummariesResponse> response) {
                System.out.println(call.request());

                if (response.code() == 200) {
                    System.out.println(response.body().getPlayerSummaries().getPlayers().get(0).getAvatar());
                    Picasso.get().load(response.body().getPlayerSummaries().getPlayers().get(0).getAvatar()).into(avatarUsuario);
                }

            }

            @Override
            public void onFailure(Call<GetPlayerSummariesResponse> call, Throwable t) {
                System.out.println(t.getMessage());
            }
        });

    }

    private void listaJuegosRecientes () {
        AdaptadorPrincipal recycleview_jvm = new AdaptadorPrincipal(getActivity(), listaJuegos);

        RecyclerView recyclerView = getActivity().findViewById(R.id.perfil_user_lista_juegos_recientes_recycler);

        recyclerView.setAdapter(recycleview_jvm);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }
}