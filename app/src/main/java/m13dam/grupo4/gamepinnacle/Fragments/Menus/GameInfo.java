package m13dam.grupo4.gamepinnacle.Fragments.Menus;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import m13dam.grupo4.gamepinnacle.Classes.Other.Juego;
import m13dam.grupo4.gamepinnacle.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link GameInfo#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GameInfo extends Fragment {

    private static Juego juego;
    Button addPartidaBtn;

    public GameInfo() {

    }

    public void setGame(Juego juego){
        this.juego = juego;
    }

    public static GameInfo newInstance() {
        GameInfo fragment = new GameInfo();
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
        return inflater.inflate(R.layout.fragment_game_info, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        addPartidaBtn = view.findViewById(R.id.game_info_add_partida);

        addPartidaBtn.setOnClickListener(v -> {
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.main_fragment_container, AddSession.class, null)
                    .commit();

        });

        ImageView photo = view.findViewById(R.id.game_info_photo);
        TextView nombre = view.findViewById(R.id.game_info_nombre);
        TextView description = view.findViewById(R.id.game_info_desciption);

        Picasso.get().load(juego.getImagen()).into(photo);
        nombre.setText(juego.getNombre());
        description.setText(juego.getDescripcion());

    }
}