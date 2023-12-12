package m13dam.grupo4.gamepinnacle.Fragments.Menus;

import static m13dam.grupo4.gamepinnacle.DataBases.DataBaseManager.SaveLoginRemember;
import static m13dam.grupo4.gamepinnacle.DataBases.DataBaseManager.sqlitemail;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import android.widget.Toast;

import com.google.common.hash.Hashing;

import java.nio.charset.StandardCharsets;

import m13dam.grupo4.gamepinnacle.Classes.Other.Usuario;
import m13dam.grupo4.gamepinnacle.DataBases.DataBaseManager;
import m13dam.grupo4.gamepinnacle.R;
import m13dam.grupo4.gamepinnacle.Classes.Other.CurrentSession;

public class LoginMenu extends Fragment {

    // Ui_Layout
    LinearLayout login_ui;

    // UI
    EditText email_text;
    EditText password_text;
    ImageView password_eye;
    TextView recover;
    Button login_button;
    TextView register_register;

    //Hidden state
    boolean password_hidden = true;

    public LoginMenu() {
        // Required empty public constructor
    }

    public static LoginMenu newInstance() {
        return new LoginMenu();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

        setViews(view);

        passwordEye();
        recover(view);
        login(view);
        register(view);
    }

    private void register(@NonNull View view) {
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

    private void login(@NonNull View view) {
        // Login remember
        new Thread(() -> {
            Looper.prepare();

            int userId = DataBaseManager.LoginRemember(getActivity());

            if (userId < 1){
                return;
            }

            Usuario usuario = DataBaseManager.GetUserFromDatabase(userId);
            CurrentSession.setUsuario(usuario);

            loadUserMenu();
        }).start();

        // Login button
        login_button = view.findViewById(R.id.login_login_button);
        login_button.setOnClickListener(v -> {
            Animation anim = AnimationUtils.loadAnimation(getActivity(), R.anim.blink);
            login_button.startAnimation(anim);

            String usuarioIntroducido_JVM = email_text.getText().toString();
            String contraseñaIntroducido_JVM = password_text.getText().toString();

            if(usuarioIntroducido_JVM.isEmpty() || contraseñaIntroducido_JVM.isEmpty()) {
                Toast.makeText(getActivity(), "Introduzca ambos campos de sesion", Toast.LENGTH_SHORT).show();
                return;
            }

            new Thread(() -> {
                Looper.prepare();

                int LoginID = DataBaseManager.Login(usuarioIntroducido_JVM, Hashing.sha256().hashString(contraseñaIntroducido_JVM, StandardCharsets.UTF_8).toString());
                Usuario usuario = DataBaseManager.GetUserFromDatabase(LoginID);
                DataBaseManager.SaveLoginRemember(getActivity(), usuario);
                CurrentSession.setUsuario(usuario);

                if (CurrentSession.getUsuario() == null) {
                    Toast.makeText(getActivity(), "Usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show();
                    return;
                }

                loadUserMenu();
            }).start();
        });

    }

    private void recover(@NonNull View view) {
        // Recover
        recover = view.findViewById(R.id.login_recover_button);
        recover.setOnClickListener(v -> {
            Animation anim = AnimationUtils.loadAnimation(getActivity(), R.anim.blink);
            recover.startAnimation(anim);

            // TODO
            System.out.println("TODO: Recover");
        });
    }

    private void passwordEye() {
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
    }

    private void loadUserMenu() {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.main_fragment_container, PerfilUserMenu.class, null)
                .commit();
    }

    private void setViews(@NonNull View view) {
        login_ui = getActivity().findViewById(R.id.login_ui);

        Animation anim = AnimationUtils.loadAnimation(getActivity(), R.anim.blink);
        login_ui.startAnimation(anim);

        password_eye = view.findViewById(R.id.login_password_visibility);
        password_text = view.findViewById(R.id.login_password_text);

        email_text = view.findViewById(R.id.login_email_text);
    }

}