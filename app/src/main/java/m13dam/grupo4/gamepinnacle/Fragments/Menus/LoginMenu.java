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
import android.util.Patterns;
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
import java.security.SecureRandom;

import m13dam.grupo4.gamepinnacle.BuildConfig;
import m13dam.grupo4.gamepinnacle.Classes.Other.MailManager;
import m13dam.grupo4.gamepinnacle.Classes.Other.Usuario;
import m13dam.grupo4.gamepinnacle.DataBases.DataBaseManager;
import m13dam.grupo4.gamepinnacle.R;
import m13dam.grupo4.gamepinnacle.Classes.Other.CurrentSession;

public class LoginMenu extends Fragment {

    // Ui_Layout
    private LinearLayout login_ui;
    private TextView recover;
    // UI
    private EditText email_text;
    private EditText password_text;
    private ImageView password_eye;

    private Button login_button;
    private TextView register_register;

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
        System.out.println("LOGIN 1");
        // Login remember
        new Thread(() -> {
            Looper.prepare();

            int userId = DataBaseManager.LoginRemember(getActivity());

            if (userId < 1){
                return;
            }

            System.out.println(userId);

            Usuario usuario = DataBaseManager.GetUserFromDatabase(userId);
            CurrentSession.setUsuario(usuario);
            CurrentSession.setSteamApiKey(usuario.getSteamapikey());
            System.out.println(CurrentSession.getUsuario().getSteamid());
            System.out.println(CurrentSession.getSteamApiKey());

            loadUserMenu();
        }).start();


        System.out.println("LOGIN 2");
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

            System.out.println("LOGIN 3");
            new Thread(() -> {
                Looper.prepare();

                System.out.println("LOGIN 4");

                int LoginID = DataBaseManager.Login(usuarioIntroducido_JVM, Hashing.sha256().hashString(contraseñaIntroducido_JVM, StandardCharsets.UTF_8).toString());
                System.out.println("LOGIN ID: " + LoginID);

                if (LoginID == -1){
                    System.out.println("User not found");
                    Toast.makeText(getActivity(), "Usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show();
                    return;
                }

                Usuario usuario = DataBaseManager.GetUserFromDatabase(LoginID);

                if (usuario == null) {
                    Toast.makeText(getActivity(), "Usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show();
                return;
                }
                System.out.println(usuario.getSteamid());
                System.out.println(usuario.getSteamapikey());
                SaveLoginRemember(getActivity(), usuario, CurrentSession.getTwitchToken());
                CurrentSession.setUsuario(usuario);
                CurrentSession.setSteamApiKey(usuario.getSteamapikey());

                loadUserMenu();
            }).start();
        });

    }

    private boolean isValidEmail(String email) {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private void recover(@NonNull View view) {
        // Recover
        recover = view.findViewById(R.id.login_recover_button);
        recover.setOnClickListener(v -> {
            Animation anim = AnimationUtils.loadAnimation(getActivity(), R.anim.blink);
            recover.startAnimation(anim);

            if (!isValidEmail(email_text.getText().toString())){
                Toast.makeText(getActivity(), "Correo no valido.", Toast.LENGTH_SHORT).show();
                return;
            }

            String ALLOWED_CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

            StringBuilder password = new StringBuilder();
            SecureRandom random = new SecureRandom();

            for (int i = 0; i < 8; i++) {
                int randomIndex = random.nextInt(ALLOWED_CHARACTERS.length());
                char randomChar = ALLOWED_CHARACTERS.charAt(randomIndex);
                password.append(randomChar);
            }

            String newPassword = password.toString();
            String encPassword = Hashing.sha256().hashString(newPassword, StandardCharsets.UTF_8).toString();

            new Thread(() -> {

                int id = DataBaseManager.getIdFromMail(email_text.getText().toString());

                if (id > 0){
                    DataBaseManager.ActualizarContraseña(id, encPassword);
                    try {
                        new MailManager(BuildConfig.mailusername, BuildConfig.mailpassword, email_text.getText().toString(), "GamePinnacle - Contraseña", "Esta es tu nueva contraseña: " + newPassword).execute();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    getActivity().runOnUiThread(() -> {
                        Toast.makeText(getActivity(), "Se ha enviado tu nueva contraseña al correo.", Toast.LENGTH_SHORT).show();
                    });
                }

            }).start();

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