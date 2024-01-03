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
import android.widget.Toast;

import m13dam.grupo4.gamepinnacle.Classes.Other.Amigos;
import m13dam.grupo4.gamepinnacle.Classes.Other.CurrentSession;
import m13dam.grupo4.gamepinnacle.DataBases.DataBaseManager;
import m13dam.grupo4.gamepinnacle.R;

public class AddFriendMenu extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private EditText friendName;
    private EditText friendSurname1;
    private EditText friendSurname2;
    private EditText imageFriend;
    private Button addFriend;

    public AddFriendMenu() {
        // Required empty public constructor
    }

    public static AddFriendMenu newInstance(String param1, String param2) {
        AddFriendMenu fragment = new AddFriendMenu();
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
        return inflater.inflate(R.layout.fragment_add_friend_menu, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        friendName = view.findViewById(R.id.nombreAmigo);
        friendSurname1 = view.findViewById(R.id.apellidoAmigo1);
        friendSurname2 = view.findViewById(R.id.apellidoAmigo2);
        imageFriend = view.findViewById(R.id.imagenUrlAmigo);
        addFriend = view.findViewById(R.id.añadirAmigo_button);


        addFriend.setOnClickListener(v->{

            if(friendName.getText().toString().isEmpty() || friendSurname1.getText().toString().isEmpty() || friendSurname2.getText().toString().isEmpty() || imageFriend.getText().toString().isEmpty()){
                Toast.makeText(getActivity(), "Complete todos los campos", Toast.LENGTH_SHORT).show();
                return;
            }
            Amigos amigo = new Amigos (friendName.getText().toString(),friendSurname1.getText().toString(),friendSurname2.getText().toString(),imageFriend.getText().toString());

            new Thread(()-> {
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