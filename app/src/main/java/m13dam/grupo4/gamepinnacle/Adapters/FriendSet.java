package m13dam.grupo4.gamepinnacle.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Currency;

import m13dam.grupo4.gamepinnacle.Classes.Other.Amigos;
import m13dam.grupo4.gamepinnacle.Classes.Other.CurrentSession;
import m13dam.grupo4.gamepinnacle.Classes.Other.PlayedGamesFriends;
import m13dam.grupo4.gamepinnacle.Fragments.Menus.PerfilFriendMenu;
import m13dam.grupo4.gamepinnacle.Fragments.Menus.PerfilUserMenu;
import m13dam.grupo4.gamepinnacle.R;

public class FriendSet extends RecyclerView.Adapter<FriendSet.ViewHolder> {
    private Context mContext_jvm;
    private ArrayList<PlayedGamesFriends> listapartidas;
    private Fragment frag;



    public FriendSet(Context context, ArrayList<PlayedGamesFriends>Listapartidas, Fragment frag) {
        this.mContext_jvm = context;
        this.listapartidas = Listapartidas;
        this.frag = frag;
        System.out.println("asdasdasdasdasdad");
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
        }
    }

    @NonNull
    @Override
    public FriendSet.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext_jvm);
        View view = inflater.inflate(R.layout.recycleviewlayout_jvm, parent, false);
        return new FriendSet.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        PlayedGamesFriends parti = listapartidas.get(position);
        if (frag.getClass() == PerfilFriendMenu.class){
            holder.nombre.setText(parti.getGame_name());
        } else {
            if (parti.getFriend_name().isEmpty()){
                holder.nombre.setText(CurrentSession.getUsuario().getUsuario());
            } else {
                holder.nombre.setText(parti.getFriend_name());
            }
        }

        if(parti.getLoseWin()){
            holder.apellido.setText("Ganada");}
        else {
            holder.apellido.setText("Perdida");
        }
        String texto = parti.getHours() + " Horas";
        holder.apellido2.setText(texto);

    }

    @Override
    public int getItemCount() {
        return listapartidas.size();
    }

}
