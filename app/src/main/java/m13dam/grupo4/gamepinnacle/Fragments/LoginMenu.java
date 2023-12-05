package m13dam.grupo4.gamepinnacle.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Handler;
import android.os.Looper;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import m13dam.grupo4.gamepinnacle.DataBase.DataBaseManager;
import m13dam.grupo4.gamepinnacle.R;
import m13dam.grupo4.gamepinnacle.Types.CurrentSession;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LoginMenu#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LoginMenu extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    // Ui_Layout

    LinearLayout login_ui;

    // UI
    EditText email_text;

    EditText password_text;
    ImageView password_eye;
    boolean password_hidden = true;

    TextView recover;

    Button login_button;

    TextView register_register;

    public LoginMenu() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LoginMenu.
     */
    // TODO: Rename and change types and number of parameters
    public static LoginMenu newInstance(String param1, String param2) {
        LoginMenu fragment = new LoginMenu();
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

        return inflater.inflate(R.layout.fragment_login_menu, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Ui
        login_ui = getActivity().findViewById(R.id.login_ui);

        Animation uianim = AnimationUtils.loadAnimation(getActivity(), R.anim.blink);
        login_ui.startAnimation(uianim);

        // Password
        password_eye = view.findViewById(R.id.login_password_visibility);
        password_text = view.findViewById(R.id.login_password_text);

        email_text = view.findViewById(R.id.login_email_text);

        password_eye.setOnClickListener(v -> {
            Animation anim = AnimationUtils.loadAnimation(getActivity(), R.anim.blink);
            password_eye.startAnimation(anim);

            if (password_hidden) {
                password_hidden = false;
                password_eye.setImageResource(R.drawable.password_eye);
                int sel_start = password_text.getSelectionStart();
                int sel_end = password_text.getSelectionEnd();
                password_text.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                password_text.setSelection(sel_start, sel_end);
            } else {
                password_hidden = true;
                password_eye.setImageResource(R.drawable.password_key);
                int sel_start = password_text.getSelectionStart();
                int sel_end = password_text.getSelectionEnd();
                password_text.setTransformationMethod(PasswordTransformationMethod.getInstance());
                password_text.setSelection(sel_start, sel_end);
            }

        });

        // Recover
        recover = view.findViewById(R.id.login_recover_button);
        recover.setOnClickListener(v -> {
            Animation anim = AnimationUtils.loadAnimation(getActivity(), R.anim.blink);
            recover.startAnimation(anim);

            // TODO
            System.out.println("TODO: Recover");
            /*
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.main_fragment_container, RecoverMenu.class, null)
                    .commit();
            */
        });

        // Login
        login_button = view.findViewById(R.id.login_login_button);
        login_button.setOnClickListener(v -> {
            Animation anim = AnimationUtils.loadAnimation(getActivity(), R.anim.blink);
            login_button.startAnimation(anim);

            // TODO
            System.out.println("TODO: Login");

            String usuarioIntroducido_JVM = email_text.getText().toString();
            String contraseñaIntroducido_JVM = password_text.getText().toString();


            Thread thread = new Thread(() -> {

                int RememberedID = DataBaseManager.LoginRemember(getActivity());
                if(RememberedID > 0){
                    CurrentSession.setUserID(RememberedID);
                } else {
                    int LoginID = DataBaseManager.Login(usuarioIntroducido_JVM,contraseñaIntroducido_JVM.hashCode());
                    CurrentSession.setUserID(LoginID);
                }
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

        });

        // Register
        register_register = view.findViewById(R.id.login_register_button);
        register_register.setOnClickListener(v -> {
            Animation anim = AnimationUtils.loadAnimation(getActivity(), R.anim.blink);
            register_register.startAnimation(anim);

            // TODO
            System.out.println("TODO: Register");
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.main_fragment_container, RegisterMenu.class, null)
                    .commit();
        });

    }

}