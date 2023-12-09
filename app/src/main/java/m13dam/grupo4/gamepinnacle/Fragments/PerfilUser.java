package m13dam.grupo4.gamepinnacle.Fragments;



import android.content.Intent;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;

import m13dam.grupo4.gamepinnacle.Adapters.AdaptadorPrincipal;
import m13dam.grupo4.gamepinnacle.R;
import m13dam.grupo4.gamepinnacle.SteamWebApi;
import m13dam.grupo4.gamepinnacle.Types.Games;
import m13dam.grupo4.gamepinnacle.Types.GetOwnedGamesResponse;
import m13dam.grupo4.gamepinnacle.Types.SteamUserId;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
    private ImageView avatarUsuario;

    private ArrayList <Games> listaJuegos = new ArrayList<>();
    private ActivityResultLauncher<Intent> pickImageLauncher;


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
        return inflater.inflate(R.layout.activity_main2, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        avatarUsuario = getActivity().findViewById(R.id.imagenAvatar);

        SteamWebApi.getSteamWebApiService().getOwnedGamesByUser(
                SteamUserId.idUser,
                true,
                true,
                "json").enqueue(new Callback<GetOwnedGamesResponse>() {
            @Override
            public void onResponse(Call<GetOwnedGamesResponse> call, Response<GetOwnedGamesResponse> response) {
                System.out.println(call.request());
                if (response.code() == 200) {

                    for(Games g : response.body().getGetOwnedGames().getGames()) {

                        listaJuegos.add(g);

                    }
                    listaJuegosRecientes();
                }
            }

            @Override
            public void onFailure(Call<GetOwnedGamesResponse> call, Throwable t) {
                System.out.println(t.getMessage());
            }
        });

    }

    private void listaJuegosRecientes () {
        AdaptadorPrincipal recycleview_jvm = new AdaptadorPrincipal(getActivity(), listaJuegos);

        RecyclerView recyclerView = getActivity().findViewById(R.id.reciclo);

        recyclerView.setAdapter(recycleview_jvm);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }
}