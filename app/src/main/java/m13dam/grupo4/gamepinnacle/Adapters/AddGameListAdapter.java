package m13dam.grupo4.gamepinnacle.Adapters;

import android.content.Context;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import m13dam.grupo4.gamepinnacle.Classes.Other.Amigos;
import m13dam.grupo4.gamepinnacle.Classes.Other.Juego;
import m13dam.grupo4.gamepinnacle.DataBases.DataBaseManager;
import m13dam.grupo4.gamepinnacle.Fragments.Menus.FriendListMenu;
import m13dam.grupo4.gamepinnacle.Fragments.Menus.GameListMenu;
import m13dam.grupo4.gamepinnacle.R;

public class AddGameListAdapter extends RecyclerView.Adapter<AddGameListAdapter.ViewHolder> {

    private Context context;
    private List<Juego> juegos;
    private boolean isLoading = false;


    public AddGameListAdapter(Context context, List<Juego> juegos) {
        this.context = context;
        this.juegos = juegos;
    }

    @NonNull
    @Override
    public AddGameListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.add_games_recycler_item, parent, false);
        return new AddGameListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AddGameListAdapter.ViewHolder holder, int position) {

        Juego j = juegos.get(position);

        holder.nombre.setText(j.getNombre());
        holder.nombre.setSelected(true);
        holder.descripcion.setText(j.getDescripcion());
        holder.descripcion.setSelected(true);
        Picasso.get().load(j.getImagen()).into(holder.imagen);

        holder.container.setEnabled(!isLoading);
    }

    @Override
    public int getItemCount() {
        return juegos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView nombre, descripcion;
        ImageView imagen;
        LinearLayout container;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            nombre = itemView.findViewById(R.id.add_game_name);
            descripcion = itemView.findViewById(R.id.add_game_description);
            imagen = itemView.findViewById(R.id.add_game_image);
            container = itemView.findViewById(R.id.add_game_container);

            container.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION && !isLoading) {



                    Juego selectedGame = juegos.get(position);
                    new Thread(() ->{
                        Looper.prepare();

                        if (DataBaseManager.listaJuegosRegistrados(selectedGame.getNombre())<0) {
                            DataBaseManager.RegistrarJuego(selectedGame);

                            FragmentManager fragmentManager = ((AppCompatActivity) v.getContext()).getSupportFragmentManager();
                            fragmentManager.beginTransaction()
                                    .replace(R.id.main_fragment_container, GameListMenu.class, null)
                                    .commit();
                        }else {
                            Toast.makeText(context, "El juego ya esta registrado", Toast.LENGTH_SHORT).show();
                        }
                        isLoading = false;
                    }).start();


                }
            });

        }
    }

}
