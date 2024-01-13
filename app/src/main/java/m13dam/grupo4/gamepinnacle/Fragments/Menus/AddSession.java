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
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import m13dam.grupo4.gamepinnacle.Classes.Other.Amigos;
import m13dam.grupo4.gamepinnacle.Classes.Other.CurrentSession;
import m13dam.grupo4.gamepinnacle.DataBases.DataBaseManager;
import m13dam.grupo4.gamepinnacle.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddSession#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddSession extends Fragment {

    private EditText playTime;
    private Spinner friend;
    private Spinner winLose;
    private Button addSession;

    public AddSession() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddSession.
     */
    // TODO: Rename and change types and number of parameters
    public static AddSession newInstance(String param1, String param2) {
        AddSession fragment = new AddSession();
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
        return inflater.inflate(R.layout.fragment_add_partida_menu, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        addSession.setOnClickListener(v->{

            if(friendName.getText().toString().isEmpty() || friendSurname1.getText().toString().isEmpty() || friendSurname2.getText().toString().isEmpty() || imageFriend.getText().toString().isEmpty()){
                Toast.makeText(getActivity(), "Complete todos los campos", Toast.LENGTH_SHORT).show();
                return;
            }
            String imageUrl = imageFriend.getText().toString();
            if (!isValidImageUrl(imageUrl)) {
                Toast.makeText(getActivity(), "La URL de la imagen no es válida", Toast.LENGTH_SHORT).show();
                return;
            }

            Amigos amigo = new Amigos(friendName.getText().toString(), friendSurname1.getText().toString(), friendSurname2.getText().toString(), imageUrl);

            new Thread(() -> {
                Looper.prepare();

                DataBaseManager.RegistrarAmigo(amigo, CurrentSession.getUsuario().getId());

                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.main_fragment_container, FriendListMenu.class, null)
                        .commit();
            }).start();
        });



    }
}