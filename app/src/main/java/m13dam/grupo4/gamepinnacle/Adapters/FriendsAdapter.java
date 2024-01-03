package m13dam.grupo4.gamepinnacle.Adapters;

import android.content.Context;
import android.os.Bundle;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import m13dam.grupo4.gamepinnacle.Classes.Other.Amigos;
import m13dam.grupo4.gamepinnacle.DataBases.DataBaseManager;
import m13dam.grupo4.gamepinnacle.Fragments.Menus.FriendInfo;
import m13dam.grupo4.gamepinnacle.Fragments.Menus.FriendListMenu;
import m13dam.grupo4.gamepinnacle.R;

public class FriendsAdapter extends RecyclerView.Adapter<FriendsAdapter.ViewHolder> {
    private Context mContext_jvm;
    private static ArrayList<Amigos> listAmigos;



    public FriendsAdapter(Context context, ArrayList<Amigos>listAmigos) {
        this.mContext_jvm = context;
        this.listAmigos = listAmigos;

    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView nombre;
        TextView apellidos;
        ImageView picture;
        Button eliminarAmigo;

        public ViewHolder(View itemView) {
            super(itemView);

            nombre = itemView.findViewById(R.id.Friends_nombre);
            apellidos = itemView.findViewById(R.id.Friends_apellido1);
            picture = itemView.findViewById(R.id.Friends_picture);
            eliminarAmigo =itemView.findViewById(R.id.eliminarAmigoButton);

            eliminarAmigo.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {

                    Amigos selectedFriend = listAmigos.get(position);
                    new Thread(() ->{
                        Looper.prepare();
                        DataBaseManager.removeFriend(selectedFriend.getId());

                        FragmentManager fragmentManager = ((AppCompatActivity) v.getContext()).getSupportFragmentManager();
                        fragmentManager.beginTransaction()
                                .replace(R.id.main_fragment_container, FriendListMenu.class, null)
                                .commit();

                    }).start();


                }
                    });

            picture.setOnClickListener(v ->  {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {

                    Amigos selectedFriend = listAmigos.get(position);

                    Bundle bundle = new Bundle();
                    bundle.putInt("friendId", selectedFriend.getId());

                    FriendInfo friendInfoFragment = new FriendInfo();
                    friendInfoFragment.setArguments(bundle);

                    FragmentManager fragmentManager = ((AppCompatActivity) v.getContext()).getSupportFragmentManager();
                    fragmentManager.beginTransaction()
                            .replace(R.id.main_fragment_container, friendInfoFragment)
                            .addToBackStack(null)
                            .commit();
                }
            });

        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext_jvm);
        View view = inflater.inflate(R.layout.recycler_friend_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Amigos juego = listAmigos.get(position);
        holder.nombre.setText(juego.getNombre());
        holder.apellidos.setText(juego.getApellidoUno() + " " + juego.getApellidoDos());
        if (juego.getPicture().length() > 0){
            Picasso.get().load(juego.getPicture()).into(holder.picture);
        }
    }

    @Override
    public int getItemCount() {
        return listAmigos.size();
    }

}
