package m13dam.grupo4.gamepinnacle.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
    private static ArrayList<Games> listaJuegos;
    private String test;


    public RecentlyPlayedGamesAdapter(Context context, ArrayList<Games>listaJuegos, String test) {
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
            hJuego = itemView.findViewById(R.id.recently_playes_games_horas_juego);
            hJuego.setAlpha(0f);
            horasJugadasText = itemView.findViewById(R.id.recently_playes_games_horas_juego_text);
            horasJugadasText.setAlpha(0f);
            archivementPorgress = itemView.findViewById(R.id.recently_playes_games_archivement_progress);
            archivementPorgress.setAlpha(0f);
            archivementText = itemView.findViewById(R.id.recently_playes_games_archivement_text);
            archivementText.setAlpha(0f);

            itemView.setOnClickListener(v ->  {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {

                    Games selectedGame = listaJuegos.get(position);

                    Bundle bundle = new Bundle();
                    bundle.putString("gameId", selectedGame.getAppid());

                    GameInfo gameInfoFragment = new GameInfo();

                    new Thread(() -> {

                        try {
                            IGDBWrapper wrapper = IGDBWrapper.INSTANCE;
                            wrapper.setCredentials(BuildConfig.twitchclientid, CurrentSession.getTwitchToken());

                            APICalypse apicalypse = new APICalypse().fields("*, websites.*, cover.*").search(selectedGame.getName()).limit(1);
                            List<Game> games = ProtoRequestKt.games(wrapper, apicalypse);
                            if (games.size() < 1) {
                                return;
                            }
                            gameInfoFragment.setGame(new Juego((int)games.get(0).getId(), games.get(0).getName(), games.get(0).getSummary(), "https:"+games.get(0).getCover().getUrl()));
                        } catch (Exception e){
                            e.printStackTrace();
                        }


                        gameInfoFragment.setArguments(bundle);

                        FragmentManager fragmentManager = ((AppCompatActivity) v.getContext()).getSupportFragmentManager();
                        fragmentManager.beginTransaction()
                                .replace(R.id.main_fragment_container, gameInfoFragment)
                                .addToBackStack(null)
                                .commit();

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
        Games juego = listaJuegos.get(position);

        Picasso.get().load("https://media.steampowered.com/steamcommunity/public/images/apps/" +  juego.getAppid() + "/" +  juego.getImg_icon_url() +  ".jpg").into(holder.imagenJuego);
        holder.nJuego.setText(juego.getName());

        if(test.equals("all")){
            holder.hJuego.setText(juego.getPlaytime_forever_on_hours());
        }
        if(test.equals("2weeks")){
            holder.hJuego.setText(juego.getPlaytime_2weeks_on_hours());
        }

        SteamWebApi.getSteamWebApiService().getPlayerAchievements(
                CurrentSession.getSteamApiKey(),
                CurrentSession.getUsuario().getSteamid(),
                juego.getAppid(),
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

                    holder.archivementPorgress.setMax(NumberOfArchivements);
                    holder.archivementPorgress.setProgress(ArchivementsCompleted, true);
                    holder.archivementText.setText(ArchivementsCompleted + "/" + NumberOfArchivements);

                    holder.archivementPorgress.setAlpha(1f);
                    holder.archivementText.setAlpha(1f);
                }

                holder.imagenJuego.setAlpha(1f);
                holder.nJuego.setAlpha(1f);
                holder.hJuego.setAlpha(1f);
                holder.horasJugadasText.setAlpha(1f);
            }

            @Override
            public void onFailure(Call<GetPlayerAchievementsResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });

    }

    @Override
    public int getItemCount() {
        return listaJuegos.size();
    }

}
