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
import m13dam.grupo4.gamepinnacle.Classes.SteamWebApi.Amigos;
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

public class FriendsAdapter extends RecyclerView.Adapter<FriendsAdapter.ViewHolder> {
    private Context mContext_jvm;
    private ArrayList<Amigos> listaJuegos;


    public FriendsAdapter(Context context, ArrayList<Amigos>listaJuegos) {
        this.mContext_jvm = context;
        this.listaJuegos = listaJuegos;

    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView nombre;
        TextView apellido;
        TextView apellido2;

        public ViewHolder(View itemView) {
            super(itemView);

            nombre = itemView.findViewById(R.id.nombre);
            apellido = itemView.findViewById(R.id.apellido1);
            apellido2 = itemView.findViewById(R.id.apellido2);


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
        View view = inflater.inflate(R.layout.recycleviewlayout_jvm, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Amigos juego = listaJuegos.get(position);
        holder.nombre.setText(juego.getNombre());
        holder.apellido.setText(juego.getApellidoUno());
        holder.apellido2.setText(juego.getApellidoDos());


    }

    @Override
    public int getItemCount() {
        return listaJuegos.size();
    }

}
