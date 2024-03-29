package m13dam.grupo4.gamepinnacle.Fragments.Menus;



import android.content.Intent;
import android.media.Image;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;

import java.util.List;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CountDownLatch;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import m13dam.grupo4.gamepinnacle.Activities.MainActivity;
import m13dam.grupo4.gamepinnacle.Adapters.RecentlyPlayedGamesAdapter;
import m13dam.grupo4.gamepinnacle.Classes.Other.Juego;
import m13dam.grupo4.gamepinnacle.DataBases.DataBaseManager;
import m13dam.grupo4.gamepinnacle.R;
import m13dam.grupo4.gamepinnacle.Classes.SteamWebApi.SteamWebApi;
import m13dam.grupo4.gamepinnacle.Classes.Other.CurrentSession;
import m13dam.grupo4.gamepinnacle.Classes.SteamWebApi.Games;
import m13dam.grupo4.gamepinnacle.Classes.SteamWebApi.GetOwnedGamesResponse;
import m13dam.grupo4.gamepinnacle.Classes.SteamWebApi.GetPlayerSummariesResponse;
import m13dam.grupo4.gamepinnacle.Classes.SteamWebApi.GetRecentlyPlayedGamesResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PerfilUserMenu#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PerfilUserMenu extends Fragment {

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
    private ImageView loginOut;
    private ImageView ajustes;
    private Button buttonListGames;
    private Button buttonListFriend;

    private ArrayList <Juego> listaJuegos = new ArrayList<>();
    private ProgressBar barra;
    int contador = 4;


    public PerfilUserMenu() {
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
    public static PerfilUserMenu newInstance(String param1, String param2) {
        PerfilUserMenu fragment = new PerfilUserMenu();
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
        userMail.setText(CurrentSession.getUsuario().getCorreo());
        userName.setText(CurrentSession.getUsuario().getUsuario());
        loginOut = view.findViewById(R.id.prefil_user_logout);
        ajustes = view.findViewById(R.id.prefil_user_settings);
        buttonListGames = view.findViewById(R.id.perfil_user_games_button);
        buttonListFriend = view.findViewById(R.id.perfil_user_friends_button);
        barra = view.findViewById(R.id.progressBar2);

        disableButtons();




        new Thread(() -> {
            int friendListSize = DataBaseManager.getFriendList(CurrentSession.getUsuario().getId()).size();

            requireActivity().runOnUiThread(() -> {
                numberOfFriends.setText(String.valueOf(friendListSize));
            });

        }).start();

        loginOut.setOnClickListener(v -> {

            Animation anim = AnimationUtils.loadAnimation(getActivity(), R.anim.blink);
            loginOut.startAnimation(anim);

            new Thread(() -> {

                DataBaseManager.unLoginRemember(getActivity());
                FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.main_fragment_container, LoginMenu.class, null)
                        .commit();

            }).start();
        });

        ajustes.setOnClickListener(v -> {

                Animation anim = AnimationUtils.loadAnimation(getActivity(), R.anim.blink);
                ajustes.startAnimation(anim);

                FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.main_fragment_container, SettingsMenu.class, null)
                        .commit();


        });

        buttonListGames.setOnClickListener(v -> {

            Animation anim = AnimationUtils.loadAnimation(getActivity(), R.anim.blink);
            buttonListGames.startAnimation(anim);

            FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.main_fragment_container, GameListMenu.class, null)
                    .commit();

                });

        buttonListFriend.setOnClickListener(v -> {

            Animation anim = AnimationUtils.loadAnimation(getActivity(), R.anim.blink);
            buttonListFriend.startAnimation(anim);

            FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.main_fragment_container, FriendListMenu.class, null)
                    .commit();

                });


            SteamWebApi.getSteamWebApiService().getRecentlyGamesByUser(
                    CurrentSession.getSteamApiKey(),
                    CurrentSession.getUsuario().getSteamid(),
                    3,
                    "json").enqueue(new Callback<GetRecentlyPlayedGamesResponse>() {
                @Override
                public void onResponse(Call<GetRecentlyPlayedGamesResponse> call, Response<GetRecentlyPlayedGamesResponse> response) {
                    System.out.println(call.request());
                    System.out.println(response.code());

                    if (response.code() == 200) {

                        if (response.body().getRecentGames().getGames() == null) {
                            System.out.println("GetRecentlyPlayedGames is null");
                            contador-=1;
                            return;
                        }

                        for (Games g : response.body().getRecentGames().getGames()) {

                            Juego j = new Juego();
                            j.setNombre(g.getName());
                            j.setSteamID(Integer.valueOf(g.getAppid()));
                            j.setImagen(g.getImg_icon_url());
                            j.setPlayTime2Weeks(Integer.parseInt(g.getPlaytime_2weeks_on_hours()));
                            j.setSteamImagen("https://media.steampowered.com/steamcommunity/public/images/apps/"+g.getAppid()+"/"+g.getImg_icon_url()+".jpg");

                            listaJuegos.add(j);

                        }
                        listaJuegosRecientes();
                        contador-=1;
                    }
                }

                @Override
                public void onFailure(Call<GetRecentlyPlayedGamesResponse> call, Throwable t) {
                    System.out.println(t.getMessage());
                    contador-=1;
                }
            });


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
                            System.out.println("GetOwnedGames is null");
                            contador-=1;
                            return;
                        }

                        totalGames.setText(String.valueOf(response.body().getGetOwnedGames().getGame_count()));


                        long playedTimeCount = 0L;
                        long playedTimeHours;

                        for (Games g : response.body().getGetOwnedGames().getGames()) {
                            playedTimeCount += Long.parseLong(g.getPlaytime_forever());
                        }
                        playedTimeHours = playedTimeCount / 60;

                        played_hours.setText(String.valueOf(playedTimeHours));
                        contador-=1;

                    }
                }

                @Override
                public void onFailure(Call<GetOwnedGamesResponse> call, Throwable t) {
                    System.out.println(t.getMessage());
                    contador-=1;
                }
            });

            SteamWebApi.getSteamWebApiService().getPlayerSummaries(
                    CurrentSession.getSteamApiKey(),
                    CurrentSession.getUsuario().getSteamid(),
                    "json"
            ).enqueue(new Callback<GetPlayerSummariesResponse>() {
                @Override
                public void onResponse(Call<GetPlayerSummariesResponse> call, Response<GetPlayerSummariesResponse> response) {
                    System.out.println(call.request());
                    System.out.println(response.code());

                    if (response.code() == 200) {

                        if (response.body().getPlayerSummaries().getPlayers() == null) {
                            System.out.println("GetPlayerSummaries is null");
                            contador-=1;
                            return;
                        }

                        System.out.println(response.body().getPlayerSummaries().getPlayers().get(0).getAvatar());
                        Picasso.get().load(response.body().getPlayerSummaries().getPlayers().get(0).getAvatar()).into(avatarUsuario);
                        contador-=1;
                    }

                }

                @Override
                public void onFailure(Call<GetPlayerSummariesResponse> call, Throwable t) {
                    System.out.println(t.getMessage());
                    contador-=1;
                }
            });



        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (getActivity() != null) { // Verifica que la actividad no sea nula
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (contador == 1) {
                                enableButtons();
                                timer.cancel(); // Cancelar el temporizador después de activar los botones
                            }
                        }
                    });
                }
            }
        }, 0, 1000);

        }

    private void listaJuegosRecientes () {
        RecentlyPlayedGamesAdapter recycleview_jvm = new RecentlyPlayedGamesAdapter(getActivity(), new ArrayList<>(), "2weeks");
        RecentlyPlayedGamesAdapter.listaJuegosSteam = listaJuegos;

        RecentlyPlayedGamesAdapter.listaJuegos = RecentlyPlayedGamesAdapter.listaJuegosSteam;

        //

        RecyclerView recyclerView = getActivity().findViewById(R.id.perfil_user_lista_juegos_recientes_recycler);

        recyclerView.setAdapter(recycleview_jvm);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    private void disableButtons() {
        barra.setVisibility(View.VISIBLE);
        buttonListGames.setEnabled(false);
        buttonListFriend.setEnabled(false);
        loginOut.setEnabled(false);
        ajustes.setEnabled(false);
        ((MainActivity) requireActivity()).disableBackButton();
        // Deshabilitar otros botones según sea necesario
    }

    private void enableButtons() {
        if (isAdded()) {
            barra.setVisibility(View.INVISIBLE);
            buttonListGames.setEnabled(true);
            buttonListFriend.setEnabled(true);
            loginOut.setEnabled(true);
            ajustes.setEnabled(true);
            ((MainActivity) requireActivity()).enableBackButton();
            // Habilitar otros botones según sea necesario
        }
    }

    }