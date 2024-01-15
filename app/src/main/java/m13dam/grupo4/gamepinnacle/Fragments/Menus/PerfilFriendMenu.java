package m13dam.grupo4.gamepinnacle.Fragments.Menus;

import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.exifinterface.media.ExifInterface;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import m13dam.grupo4.gamepinnacle.Adapters.FriendSet;
import m13dam.grupo4.gamepinnacle.Adapters.FriendsAdapter;
import m13dam.grupo4.gamepinnacle.Adapters.RecentlyPlayedGamesAdapter;
import m13dam.grupo4.gamepinnacle.Classes.Other.Amigos;
import m13dam.grupo4.gamepinnacle.Classes.Other.PlayedGamesFriends;
import m13dam.grupo4.gamepinnacle.Classes.Other.Usuario;
import m13dam.grupo4.gamepinnacle.DataBases.DataBaseManager;
import m13dam.grupo4.gamepinnacle.R;
public class PerfilFriendMenu extends Fragment {
    TextView nombre;
    TextView apellido;
    TextView juegosTotales;
    TextView partidasTotales;
    TextView horasTotales;
    ImageView avatar;
    RecyclerView listaPartidas;

    ArrayList<PlayedGamesFriends> partidasAmigos;


    private static Amigos amigo;

    public static Amigos getAmigos() {
        return amigo;
    }

    public static void setAmigos(Amigos amigo) {
        PerfilFriendMenu.amigo = amigo;
    }

    public PerfilFriendMenu() {
        // Required empty public constructor
    }

    public static PerfilFriendMenu newInstance(String param1, String param2) {
        PerfilFriendMenu fragment = new PerfilFriendMenu();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_perfil_friend, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        nombre = view.findViewById(R.id.perfil_friend_nombre);
        apellido = view.findViewById(R.id.perfil_friend_correo);
        juegosTotales = view.findViewById(R.id.perfil_friend_juegos_totales);
        partidasTotales = view.findViewById(R.id.perfil_friend_partidas_totales);
        horasTotales = view.findViewById(R.id.perfil_friend_horas_totales);
        avatar = view.findViewById(R.id.perfil_friend_avatar);
        listaPartidas = view.findViewById(R.id.perfil_friend_partidas_jugadas_recycler);

        nombre.setText(amigo.getNombre());
        String apellidos = amigo.getApellidoUno() + " " + amigo.getApellidoDos();
        apellido.setText(apellidos);

        Picasso.get().load(amigo.getPicture()).into(avatar);

        new Thread(() -> {
            Looper.prepare();

            // Simula la creación de una partida y obtiene la lista de partidas
            //DataBaseManager.RegistrarPartida(new PlayedGamesFriends(8, amigo.getId(), "53", true, "323"));
            partidasAmigos = DataBaseManager.partidasAmigos(amigo.getId());
            System.out.println(partidasAmigos.size());

            // Utiliza runOnUiThread para realizar operaciones en el hilo principal
            getActivity().runOnUiThread(() -> {
                FriendSet adaptador = new FriendSet(getActivity(), partidasAmigos, this);
                listaPartidas.setAdapter(adaptador);
                listaPartidas.setLayoutManager(new LinearLayoutManager(getActivity()));

                partidasTotales.setText(String.valueOf(partidasAmigos.size()));

                int horasT = 0;
                Set<Integer> juegosUnicos = new HashSet<>();

                for (PlayedGamesFriends partida : partidasAmigos) {
                    horasT += Integer.parseInt(partida.getHours());
                    int gameId = partida.getGame_id();
                    System.out.println("ID del juego: " + gameId);  // Agrega esta línea para imprimir los IDs de los juegos
                    juegosUnicos.add(gameId);
                }

                int totalJuegosUnicos = juegosUnicos.size();

                horasTotales.setText(String.valueOf(horasT));
                juegosTotales.setText(String.valueOf(totalJuegosUnicos));




            });
        }).start();







    }


}