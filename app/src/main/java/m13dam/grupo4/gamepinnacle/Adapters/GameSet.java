package m13dam.grupo4.gamepinnacle.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import m13dam.grupo4.gamepinnacle.Classes.Other.Amigos;
import m13dam.grupo4.gamepinnacle.R;

public class GameSet extends RecyclerView.Adapter<GameSet.ViewHolder> {
    private Context mContext_jvm;
    private static ArrayList<Amigos> listAmigos;



    public GameSet(Context context, ArrayList<Amigos>listAmigos) {
        this.mContext_jvm = context;
        this.listAmigos = listAmigos;

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
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext_jvm);
        View view = inflater.inflate(R.layout.recycleviewlayout_jvm, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Amigos juego = listAmigos.get(position);
        holder.nombre.setText(juego.getNombre());
        holder.apellido.setText(juego.getApellidoUno());
        holder.apellido2.setText(juego.getApellidoDos());


    }

    @Override
    public int getItemCount() {
        return listAmigos.size();
    }

}
