package m13dam.grupo4.gamepinnacle.Fragments.Menus;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import m13dam.grupo4.gamepinnacle.Classes.Other.Amigos;
import m13dam.grupo4.gamepinnacle.Classes.Other.CurrentSession;
import m13dam.grupo4.gamepinnacle.Classes.Other.Juego;
import m13dam.grupo4.gamepinnacle.DataBases.DataBaseManager;
import m13dam.grupo4.gamepinnacle.R;

public class AddSession extends Fragment {

    private EditText playTime;
    private Spinner friend;
    private Spinner winLose;
    private Button addSession;
    private int amigoId = -1;
    private Boolean viLo;

    public AddSession() {
        // Required empty public constructor
    }

    public static AddSession newInstance(String param1, String param2) {
        AddSession fragment = new AddSession();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_partida_menu, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        playTime = view.findViewById(R.id.add_partida_time);
        friend = view.findViewById(R.id.add_partida_friend);
        winLose = view.findViewById(R.id.add_partida_winned);
        addSession = view.findViewById(R.id.añadirAmigo_button);

        new Thread(() -> {
            Looper.prepare();
            ArrayList <Amigos> amigos = DataBaseManager.getFriendList(CurrentSession.getUsuario().getId());

            ArrayAdapter<Amigos> adaptador = new ArrayAdapter<Amigos>(getActivity(), android.R.layout.simple_spinner_item, amigos) {
                @Override
                public View getView(int position, View convertView, ViewGroup parent) {
                    return super.getView(position, convertView, parent);
                }

                @Override
                public View getDropDownView(int position, View convertView, ViewGroup parent) {
                    View view = super.getDropDownView(position, convertView, parent);
                    TextView textView = (TextView) view.findViewById(android.R.id.text1);
                    textView.setText(amigos.get(position).getNombre());

                    return view;
                }
            };

            adaptador.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            getActivity().runOnUiThread(() -> {
                friend.setAdapter(adaptador);
            });

            friend.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                    amigoId = amigos.get(position).getId();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parentView) {
                    // Handle case where nothing is selected
                }
            });

            ArrayList<String> partida = new ArrayList<>();
            partida.add("Ganada");
            partida.add("Perdida");
            ArrayAdapter<String> adaptadorDos = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, partida);
            adaptadorDos.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            getActivity().runOnUiThread(() -> {
                winLose.setAdapter(adaptadorDos);

                if (winLose.getSelectedItem().equals("Ganada")) {
                    viLo = true;
                } else if (winLose.getSelectedItem().equals("Perdida")) {
                    viLo = false;
                }

            });

            addSession.setOnClickListener(v -> {
                if (playTime.getText().toString().isEmpty()) {
                    Toast.makeText(getActivity(), "Introduzca un tiempo de juego", Toast.LENGTH_SHORT).show();
                    return;
                }

                new Thread(() -> {
                    Juego nuevoJuego = new Juego(CurrentSession.getUsuario().getId(), amigoId, Integer.parseInt(playTime.getText().toString()), viLo);
                    nuevoJuego.setPlayTime(Integer.valueOf(playTime.getText().toString()));
                    DataBaseManager.RegistrarPartida(nuevoJuego);

                    getActivity().runOnUiThread(() -> {
                        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                        fragmentManager.beginTransaction()
                                .replace(R.id.main_fragment_container, GameInfo.class, null)
                                .commit();
                    });
                }).start();
            });

        }).start();
    }
}