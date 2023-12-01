package m13dam.grupo4.gamepinnacle.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;
import java.util.ArrayList;

import m13dam.grupo4.gamepinnacle.R;
import m13dam.grupo4.gamepinnacle.Types.Juegos;

public class AdaptadorPrincipal extends RecyclerView.Adapter<AdaptadorPrincipal.ViewHolder> { //Creamos el adaptador para nuestro RecyclerView
    //Declaramos las variables que utilizara para almacenar el contexto, la actividad qe lo llama y la informacion con los archivos
    private Context mContext_jvm;
    private static ArrayList<Juegos> listaJuegos;


    public AdaptadorPrincipal(Context context,ArrayList<Juegos>listaJuegos) { //Creamos el constructo de nuestro adaptador
        this.mContext_jvm = context;
        this.listaJuegos = listaJuegos;

    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        //Declaramos las variables que utilizaremos para la informacion del layout que se mostrara por pantalla
        TextView nJuego;
        TextView hJuego;
        TextView uSesion;
        ImageView imagenJuego;

        Juegos juegos;

        public ViewHolder(View itemView) {
            super(itemView);
            //Asignamos cada variable a su respectiva id del layout
            imagenJuego = itemView.findViewById(R.id.imagen_multi);
            nJuego = itemView.findViewById(R.id.nombre_juego);
            hJuego = itemView.findViewById(R.id.horas_juego);
            uSesion = itemView.findViewById(R.id.ultima_sesion);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Obtiene la posición del elemento clickeado
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        // Abre la nueva actividad y pasa el array y la posición

                    }
                }
            });
        }
    }

    //Metodo para asignar el layout que utilizara nuestro RecyclerView
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext_jvm);
        View vista_elemento = inflater.inflate(R.layout.recycleviewlayout_jvm, parent, false); //Asignamos nuestro layout personalizado
        return new ViewHolder(vista_elemento);
    }

    //Metodo para asignar a cada una de las columnas de nuestro recyclerview la informacion pertinente, extrayendo metadados de los archivos listados
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Juegos juego = listaJuegos.get(position);

        holder.nJuego.setText(juego.getNombre());
        holder.hJuego.setText(juego.getHoras());
        holder.uSesion.setText(juego.getSesion());

        holder.juegos = juego;
    }

    @Override
    public int getItemCount() {return listaJuegos.size();
    }

}