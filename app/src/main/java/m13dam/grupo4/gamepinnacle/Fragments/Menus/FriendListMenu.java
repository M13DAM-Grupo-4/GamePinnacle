package m13dam.grupo4.gamepinnacle.Fragments.Menus;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

import m13dam.grupo4.gamepinnacle.Activities.MainActivity;
import m13dam.grupo4.gamepinnacle.Adapters.FilterGamesAdapter;
import m13dam.grupo4.gamepinnacle.Adapters.FriendsAdapter;
import m13dam.grupo4.gamepinnacle.Classes.Other.Amigos;
import m13dam.grupo4.gamepinnacle.Classes.Other.CurrentSession;
import m13dam.grupo4.gamepinnacle.Classes.Other.FilterOption;
import m13dam.grupo4.gamepinnacle.DataBases.DataBaseManager;
import m13dam.grupo4.gamepinnacle.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FriendListMenu#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FriendListMenu extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    //ArrayList<Amigos> friendList = new ArrayList<>();
    private Button añadirFriend;
    private ProgressBar barra;
    int contador = 2;

    public FriendListMenu() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FriendListMenu.
     */
    // TODO: Rename and change types and number of parameters
    public static FriendListMenu newInstance(String param1, String param2) {
        FriendListMenu fragment = new FriendListMenu();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_friend_list_menu, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        /*
        friendList.add (new Amigos("asdd","asd","asd",1));
        listJuegosAmigos(); */
        System.out.println("Algo paso mu mal");
        barra = view.findViewById(R.id.barraAmigosLista);


        añadirFriend = view.findViewById(R.id.añadirAmigo);
        disableButtons();

        añadirFriend.setOnClickListener(v -> {
            FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.main_fragment_container, AddFriendMenu.class, null)
                    .commit();

        });


        new Thread(() -> {
            ArrayList<Amigos> listaDeAmigos = DataBaseManager.getFriendList(CurrentSession.getUsuario().getId());

            requireActivity().runOnUiThread(() -> {
                mostrarListaDeAmigos(listaDeAmigos);
                contador-=1;
            });
        }).start();

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (getActivity() != null) { // Verifica que la actividad no sea nula
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (contador == 1) {
                                enableButtons();
                                timer.cancel(); // Cancelar el temporizador después de activar los botones
                            }
                        }
                    });
                }
            }
        }, 0, 1000);

    }

    private void mostrarListaDeAmigos(ArrayList<Amigos> listaDeAmigos) {
        FriendsAdapter adaptadorRecyclerView = new FriendsAdapter(getActivity(), listaDeAmigos);

        RecyclerView recyclerView = getActivity().findViewById(R.id.recicleFriendsList);

        recyclerView.setAdapter(adaptadorRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    private void disableButtons() {
        barra.setVisibility(View.VISIBLE);
        añadirFriend.setEnabled(false);
        ((MainActivity) requireActivity()).disableBackButton();


        // Deshabilitar otros botones según sea necesario
    }

    private void enableButtons() {
        if (isAdded()) {
            barra.setVisibility(View.INVISIBLE);
            añadirFriend.setEnabled(true);
            ((MainActivity) requireActivity()).enableBackButton();
        }
        // Habilitar otros botones según sea necesario
    }

}