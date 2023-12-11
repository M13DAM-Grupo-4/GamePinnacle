package m13dam.grupo4.gamepinnacle.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import m13dam.grupo4.gamepinnacle.R;
import m13dam.grupo4.gamepinnacle.Classes.SteamWebApi.Games;

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

        public ViewHolder(View itemView) {
            super(itemView);

            imagenJuego = itemView.findViewById(R.id.imagen_multi);
            nJuego = itemView.findViewById(R.id.nombre_juego);
            hJuego = itemView.findViewById(R.id.horas_juego);
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
        holder.hJuego.setText(juego.getPlaytime_2weeks_on_hours());
    }

    @Override
    public int getItemCount() {
        return listaJuegos.size();
    }

}
