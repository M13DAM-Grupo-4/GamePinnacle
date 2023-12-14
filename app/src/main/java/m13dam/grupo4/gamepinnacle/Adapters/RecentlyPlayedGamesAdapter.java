package m13dam.grupo4.gamepinnacle.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import m13dam.grupo4.gamepinnacle.Classes.Other.CurrentSession;
import m13dam.grupo4.gamepinnacle.Classes.SteamWebApi.Archievement;
import m13dam.grupo4.gamepinnacle.Classes.SteamWebApi.GetPlayerAchievements;
import m13dam.grupo4.gamepinnacle.Classes.SteamWebApi.GetPlayerAchievementsResponse;
import m13dam.grupo4.gamepinnacle.Classes.SteamWebApi.GetPlayerSummariesResponse;
import m13dam.grupo4.gamepinnacle.Classes.SteamWebApi.SteamWebApi;
import m13dam.grupo4.gamepinnacle.R;
import m13dam.grupo4.gamepinnacle.Classes.SteamWebApi.Games;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecentlyPlayedGamesAdapter extends RecyclerView.Adapter<RecentlyPlayedGamesAdapter.ViewHolder> {
    private Context mContext_jvm;
    private ArrayList<Games> listaJuegos;


    public RecentlyPlayedGamesAdapter(Context context, ArrayList<Games>listaJuegos) {
        this.mContext_jvm = context;
        this.listaJuegos = listaJuegos;

    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView nJuego;
        TextView hJuego;
        ImageView imagenJuego;
        ProgressBar archivementPorgress;
        TextView archivementText;

        public ViewHolder(View itemView) {
            super(itemView);

            imagenJuego = itemView.findViewById(R.id.imagen_multi);
            nJuego = itemView.findViewById(R.id.recently_playes_games_nombre_juego);
            hJuego = itemView.findViewById(R.id.recently_playes_games_horas_juego);
            archivementPorgress = itemView.findViewById(R.id.recently_playes_games_archivement_progress);
            archivementText = itemView.findViewById(R.id.recently_playes_games_archivement_text);

            itemView.setOnClickListener(v ->  {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    // TODO
                    // Abre la nueva actividad y pasa el array y la posición
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

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Games juego = listaJuegos.get(position);

        Picasso.get().load("https://media.steampowered.com/steamcommunity/public/images/apps/" +  juego.getAppid() + "/" +  juego.getImg_icon_url() +  ".jpg").into(holder.imagenJuego);
        holder.nJuego.setText(juego.getName());

        if(juego.getPlaytime_2weeks() != null){
            holder.hJuego.setText(juego.getPlaytime_2weeks_on_hours());
        }else {
            holder.hJuego.setText(juego.getPlaytime_forever());
        }


        SteamWebApi.getSteamWebApiService().getPlayerAchievements(
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
                        holder.archivementPorgress.setAlpha(0);
                        holder.archivementText.setAlpha(0);
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
                }

                if (response.code() == 400) {
                    holder.archivementPorgress.setAlpha(0);
                    holder.archivementText.setAlpha(0);
                }

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
