package m13dam.grupo4.gamepinnacle.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.api.igdb.apicalypse.APICalypse;
import com.api.igdb.request.IGDBWrapper;
import com.api.igdb.request.ProtoRequestKt;
import com.google.common.collect.BoundType;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Array;
import java.security.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import m13dam.grupo4.gamepinnacle.BuildConfig;
import m13dam.grupo4.gamepinnacle.Classes.Other.CurrentSession;
import m13dam.grupo4.gamepinnacle.Classes.Other.Juego;
import m13dam.grupo4.gamepinnacle.Classes.SteamWebApi.Archievement;
import m13dam.grupo4.gamepinnacle.Classes.SteamWebApi.GetPlayerAchievements;
import m13dam.grupo4.gamepinnacle.Classes.SteamWebApi.GetPlayerAchievementsResponse;
import m13dam.grupo4.gamepinnacle.Classes.SteamWebApi.GetPlayerSummariesResponse;
import m13dam.grupo4.gamepinnacle.Classes.SteamWebApi.SteamWebApi;
import m13dam.grupo4.gamepinnacle.DataBases.DataBaseManager;
import m13dam.grupo4.gamepinnacle.Fragments.Menus.GameInfo;
import m13dam.grupo4.gamepinnacle.Fragments.Menus.GameListMenu;
import m13dam.grupo4.gamepinnacle.R;
import m13dam.grupo4.gamepinnacle.Classes.SteamWebApi.Games;
import proto.Game;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecentlyPlayedGamesAdapter extends RecyclerView.Adapter<RecentlyPlayedGamesAdapter.ViewHolder> {
    private Context mContext_jvm;
    public static ArrayList<Juego> listaJuegos = new ArrayList<>();
    public static ArrayList<Juego> listaJuegosSteam = new ArrayList<>();
    public static ArrayList<Juego> listaJuegosIgdb = new ArrayList<>();
    private String test;
    private static boolean isDatabaseOperationInProgress = false;

    public RecentlyPlayedGamesAdapter(Context context, ArrayList<Juego>listaJuegos, String test) {
        this.mContext_jvm = context;
        this.listaJuegos = listaJuegos;
        this.test = test;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView nJuego;
        TextView hJuego;
        TextView horasJugadasText;
        ImageView imagenJuego;
        ProgressBar archivementPorgress;
        TextView archivementText;

        public ViewHolder(View itemView) {
            super(itemView);

            imagenJuego = itemView.findViewById(R.id.imagen_multi);
            imagenJuego.setAlpha(0f);
            nJuego = itemView.findViewById(R.id.recently_playes_games_nombre_juego);
            nJuego.setAlpha(0f);
            nJuego.setSelected(true);
            hJuego = itemView.findViewById(R.id.recently_playes_games_horas_juego);
            hJuego.setAlpha(0f);
            horasJugadasText = itemView.findViewById(R.id.recently_playes_games_horas_juego_text);
            horasJugadasText.setAlpha(0f);
            archivementPorgress = itemView.findViewById(R.id.recently_playes_games_archivement_progress);
            archivementPorgress.setAlpha(0f);
            archivementText = itemView.findViewById(R.id.recently_playes_games_archivement_text);
            archivementText.setAlpha(0f);

            itemView.setOnClickListener(v ->  {

                Animation anim = AnimationUtils.loadAnimation(itemView.getContext(), R.anim.blink);
                itemView.startAnimation(anim);

                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION && !isDatabaseOperationInProgress) {

                    isDatabaseOperationInProgress = true;

                    Juego selectedGame = listaJuegos.get(position);

                    GameInfo gameInfoFragment = new GameInfo();

                    new Thread(() -> {

                        try {
                            IGDBWrapper wrapper = IGDBWrapper.INSTANCE;
                            wrapper.setCredentials(BuildConfig.twitchclientid, CurrentSession.getTwitchToken());

                            APICalypse apicalypse = new APICalypse().fields("*, websites.*, cover.*").search(selectedGame.getNombre()).limit(1);
                            List<Game> games = ProtoRequestKt.games(wrapper, apicalypse);
                            if (games.size() < 1) {
                                return;
                            }
                            gameInfoFragment.setGame(new Juego((int)games.get(0).getId(), games.get(0).getName(), games.get(0).getSummary(), "https:"+games.get(0).getCover().getUrl()));
                        } catch (Exception e){
                            e.printStackTrace();
                        }
                        if (DataBaseManager.listaJuegosRegistrados(selectedGame.getNombre()) != 1){
                                DataBaseManager.RegistrarJuego(new Juego (1,selectedGame.getNombre(),"Steam",""));
                        }

                        Juego.setId_juegoSeleccionado(selectedGame.getId());
                        Juego.setNombreJuego(selectedGame.getNombre());
                        System.out.println(Juego.getNombreJuego());

                        FragmentManager fragmentManager = ((AppCompatActivity) v.getContext()).getSupportFragmentManager();
                        fragmentManager.beginTransaction()
                                .replace(R.id.main_fragment_container, gameInfoFragment)
                                .addToBackStack(null)
                                .commit();

                        isDatabaseOperationInProgress = false;

                    }).start();


                }
            });

        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext_jvm);
        View view = inflater.inflate(R.layout.recycler_recently_played_games, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("ResourceType")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Juego juego = listaJuegos.get(position);

        if (juego.getSteamID() != 0){
            showSteamGame(holder, juego);
            return;
        }

        showIdDbGame(holder, juego);

    }

    private void showIdDbGame(ViewHolder holder, Juego juego) {
        new Thread(() -> {

            String coverUrl = "";

            try {
                IGDBWrapper wrapper = IGDBWrapper.INSTANCE;
                wrapper.setCredentials(BuildConfig.twitchclientid, CurrentSession.getTwitchToken());

                APICalypse apicalypse = new APICalypse().fields("*, websites.*, cover.*").search(juego.getNombre()).limit(1);
                List<Game> games = ProtoRequestKt.games(wrapper, apicalypse);
                if (games.size() > 0) {
                    coverUrl = "https:" + games.get(0).getCover().getUrl();
                }
            } catch (Exception e){
                e.printStackTrace();
            }

            String finalCoverUrl = coverUrl;
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    Picasso.get().load(finalCoverUrl).into(holder.imagenJuego);
                    holder.nJuego.setText(juego.getNombre());

                    if(test.equals("all")){
                        holder.hJuego.setText(String.valueOf(juego.getPlayTime()));
                    }
                }
            });

            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    holder.imagenJuego.setAlpha(1f);
                    holder.nJuego.setAlpha(1f);
                    holder.hJuego.setAlpha(1f);
                    holder.horasJugadasText.setAlpha(1f);
                }
            });

        }).start();
    }

    public void showSteamGame(ViewHolder holder, Juego juego){

        new Thread(() -> {

            String coverUrl = juego.getSteamImagen();

            try {
                IGDBWrapper wrapper = IGDBWrapper.INSTANCE;
                wrapper.setCredentials(BuildConfig.twitchclientid, CurrentSession.getTwitchToken());

                APICalypse apicalypse = new APICalypse().fields("*, websites.*, cover.*").search(juego.getNombre()).limit(1);
                List<Game> games = ProtoRequestKt.games(wrapper, apicalypse);
                if (games.size() > 0) {
                    coverUrl = "https:" + games.get(0).getCover().getUrl();
                }
            } catch (Exception e){
                e.printStackTrace();
            }

            String finalCoverUrl = coverUrl;
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    Picasso.get().load(finalCoverUrl).into(holder.imagenJuego);
                    holder.nJuego.setText(juego.getNombre());

                    if(test.equals("all")){
                        holder.hJuego.setText(String.valueOf(juego.getPlayTime()));
                    }
                    if(test.equals("2weeks")){
                        holder.hJuego.setText(String.valueOf(juego.getPlayTime2Weeks()));
                    }
                }
            });

            SteamWebApi.getSteamWebApiService().getPlayerAchievements(
                    CurrentSession.getSteamApiKey(),
                    CurrentSession.getUsuario().getSteamid(),
                    String.valueOf(juego.getSteamID()),
                    "spanish",
                    "json"
            ).enqueue(new Callback<GetPlayerAchievementsResponse>() {
                @Override
                public void onResponse(Call<GetPlayerAchievementsResponse> call, Response<GetPlayerAchievementsResponse> response) {
                    System.out.println(call.request());

                    if (response.code() == 200) {

                        List<Archievement> archievements = response.body().getPlayerAchievements().getArchivements();

                        if (archievements == null) {
                            return;
                        }



                        int NumberOfArchivements = archievements.size();
                        int ArchivementsCompleted = 0;

                        for (Archievement a : archievements) {
                            if (a.getAchieved() == 1){
                                ArchivementsCompleted++;
                            }
                        }

                        int finalArchivementsCompleted = ArchivementsCompleted;
                        new Handler(Looper.getMainLooper()).post(new Runnable() {
                            @Override
                            public void run() {
                                holder.archivementPorgress.setMax(NumberOfArchivements);
                                holder.archivementPorgress.setProgress(finalArchivementsCompleted, true);
                                holder.archivementText.setText(finalArchivementsCompleted + "/" + NumberOfArchivements);

                                holder.archivementPorgress.setAlpha(1f);
                                holder.archivementText.setAlpha(1f);
                            }
                        });


                    }

                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            holder.imagenJuego.setAlpha(1f);
                            holder.nJuego.setAlpha(1f);
                            holder.hJuego.setAlpha(1f);
                            holder.horasJugadasText.setAlpha(1f);
                        }
                    });

                }

                @Override
                public void onFailure(Call<GetPlayerAchievementsResponse> call, Throwable t) {
                    t.printStackTrace();
                }
            });

        }).start();
    }

    @Override
    public int getItemCount() {
        return listaJuegos.size();
    }

}
