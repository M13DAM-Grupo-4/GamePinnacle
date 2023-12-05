package m13dam.grupo4.gamepinnacle.Fragments;

import static m13dam.grupo4.gamepinnacle.DataBase.DataBaseManager.Login;
import static m13dam.grupo4.gamepinnacle.DataBase.DataBaseManager.RegistarUsuario;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.SQLOutput;

import m13dam.grupo4.gamepinnacle.R;
import m13dam.grupo4.gamepinnacle.Types.Usuario;
import m13dam.grupo4.gamepinnacle.Types.sesion;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RegisterMenu#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RegisterMenu extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private EditText mail_int;
    private EditText user_int;
    private EditText passOne_int;
    private EditText passTwo_int;
    private Button registrar;

    // Ui_Layout

    LinearLayout register_menu_ui;
    TextView register_menu_title;

    public RegisterMenu() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RegisterMenu.
     */
    // TODO: Rename and change types and number of parameters
    public static RegisterMenu newInstance(String param1, String param2) {
        RegisterMenu fragment = new RegisterMenu();
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
        return inflater.inflate(R.layout.fragment_register_menu, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        register_menu_ui = getActivity().findViewById(R.id.register_menu_ui);
        register_menu_title = getActivity().findViewById(R.id.register_menu_title);

        Animation anim = AnimationUtils.loadAnimation(getActivity(), R.anim.blink);
        register_menu_ui.startAnimation(anim);
        register_menu_title.startAnimation(anim);

        mail_int = view.findViewById(R.id.register_email_text);
        user_int = view.findViewById(R.id.register_username_text);
        passOne_int = view.findViewById(R.id.register_password_text);
        passTwo_int = view.findViewById(R.id.register_password_repeated_text);

        registrar = view.findViewById(R.id.login_login_button);

        registrar.setOnClickListener(v -> {

            String mail = String.valueOf(mail_int.getText());
            String user = String.valueOf(user_int.getText());
            String passOne = String.valueOf(passOne_int.getText());
            String passTwo = String.valueOf(passTwo_int.getText());



            if (!mail.isEmpty() && !user.isEmpty() && !passOne.isEmpty() && !passTwo.isEmpty()) {

                if(passOne.equals(passTwo)) {

                    int pass = passOne.hashCode();

                    sesion.usuario = new Usuario(mail, user, pass);


                    Thread thread = new Thread(() -> {

                            RegistarUsuario(sesion.usuario);
                            Login(mail,pass);


                        Handler handler = new Handler(Looper.getMainLooper());

                        handler.post(new Runnable() {
                            @Override
                            public void run() {


                                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                                fragmentManager.beginTransaction()
                                        .replace(R.id.main_fragment_container, PerfilUser.class, null)
                                        .commit();
                            }
                        });
                    });
                    thread.start();
                }else {
                    Toast.makeText(getActivity(), "Las contraseñas deben coincidir", Toast.LENGTH_SHORT).show();
                }

            } else {
                Toast.makeText(getActivity(), "Complete todos los campos", Toast.LENGTH_SHORT).show();
            }
        });



    }



}