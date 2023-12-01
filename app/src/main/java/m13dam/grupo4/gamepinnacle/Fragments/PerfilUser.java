package m13dam.grupo4.gamepinnacle.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import m13dam.grupo4.gamepinnacle.Adapters.AdaptadorPrincipal;
import m13dam.grupo4.gamepinnacle.R;
import m13dam.grupo4.gamepinnacle.Types.Juegos;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PerfilUser#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PerfilUser extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private ArrayList <Juegos> listaJuegos;

    public PerfilUser() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PerfilUser.
     */
    // TODO: Rename and change types and number of parameters
    public static PerfilUser newInstance(String param1, String param2) {
        PerfilUser fragment = new PerfilUser();
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
        return inflater.inflate(R.layout.fragment_perfil_user, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);



        listaJuegosRecientes();







    }


    private void listaJuegosRecientes () {
        AdaptadorPrincipal recycleview_jvm = new AdaptadorPrincipal(getActivity(), listaJuegos);
        //Asignamos la id de nuestro RecyclerView del layout
        RecyclerView recyclerView = getActivity().findViewById(R.id.pruebaRecycle);
        //Asignamos el adaptador que vamos a utilizar en nuestro recyclerview
        recyclerView.setAdapter(recycleview_jvm);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }
}