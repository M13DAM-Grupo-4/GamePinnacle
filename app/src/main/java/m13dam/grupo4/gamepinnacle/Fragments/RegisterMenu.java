package m13dam.grupo4.gamepinnacle.Fragments;

import static m13dam.grupo4.gamepinnacle.DataBase.DataBaseManager.Login;
import static m13dam.grupo4.gamepinnacle.DataBase.DataBaseManager.RegistarUsuario;
import static m13dam.grupo4.gamepinnacle.DataBase.DataBaseManager.SaveLoginRemember;
import static m13dam.grupo4.gamepinnacle.DataBase.DataBaseManager.comprobarCorreo;

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
import com.squareup.picasso.Picasso;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.CompletableFuture;

import m13dam.grupo4.gamepinnacle.DataBase.DataBaseManager;
import m13dam.grupo4.gamepinnacle.R;
import m13dam.grupo4.gamepinnacle.SteamWebApi;
import m13dam.grupo4.gamepinnacle.Types.CurrentSession;
import m13dam.grupo4.gamepinnacle.Types.GetPlayerSummariesResponse;
import m13dam.grupo4.gamepinnacle.Types.Usuario;
import m13dam.grupo4.gamepinnacle.Types.sesion;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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

    // Text
    EditText register_password_text;
    EditText register_password_repeated_text;
    EditText register_steam_id;

    // Password visibility
    ImageView register_password_visibility;
    ImageView register_password_repeated_visibility;

    boolean password_hidden = true;
    boolean password_repeated_hidden = true;

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

        register_password_text = getActivity().findViewById(R.id.register_password_text);
        register_password_repeated_text = getActivity().findViewById(R.id.register_password_repeated_text);

        register_password_visibility = getActivity().findViewById(R.id.register_password_visibility);
        register_password_visibility.setOnClickListener(v -> {
            Animation anim1 = AnimationUtils.loadAnimation(getActivity(), R.anim.blink);
            register_password_visibility.startAnimation(anim1);

            if (password_hidden) {
                password_hidden = false;
                register_password_visibility.setImageResource(R.drawable.password_eye);
                int sel_start = register_password_text.getSelectionStart();
                int sel_end = register_password_text.getSelectionEnd();
                register_password_text.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                register_password_text.setSelection(sel_start, sel_end);
            } else {
                password_hidden = true;
                register_password_visibility.setImageResource(R.drawable.password_key);
                int sel_start = register_password_text.getSelectionStart();
                int sel_end = register_password_text.getSelectionEnd();
                register_password_text.setTransformationMethod(PasswordTransformationMethod.getInstance());
                register_password_text.setSelection(sel_start, sel_end);
            }
        });

        register_password_repeated_visibility = getActivity().findViewById(R.id.register_password_repeated_visibility);
        register_password_repeated_visibility.setOnClickListener(v -> {
            Animation anim1 = AnimationUtils.loadAnimation(getActivity(), R.anim.blink);
            register_password_repeated_visibility.startAnimation(anim1);

            if (password_repeated_hidden) {
                password_repeated_hidden = false;
                register_password_repeated_visibility.setImageResource(R.drawable.password_eye);
                int sel_start = register_password_repeated_text.getSelectionStart();
                int sel_end = register_password_repeated_text.getSelectionEnd();
                register_password_repeated_text.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                register_password_repeated_text.setSelection(sel_start, sel_end);
            } else {
                password_repeated_hidden = true;
                register_password_repeated_visibility.setImageResource(R.drawable.password_key);
                int sel_start = register_password_repeated_text.getSelectionStart();
                int sel_end = register_password_repeated_text.getSelectionEnd();
                register_password_repeated_text.setTransformationMethod(PasswordTransformationMethod.getInstance());
                register_password_repeated_text.setSelection(sel_start, sel_end);
            }
        });

        mail_int = view.findViewById(R.id.register_email_text);
        user_int = view.findViewById(R.id.register_username_text);
        passOne_int = view.findViewById(R.id.register_password_text);
        passTwo_int = view.findViewById(R.id.register_password_repeated_text);
        register_steam_id = view.findViewById(R.id.register_steamid_text);

        registrar = view.findViewById(R.id.login_login_button);

        registrar.setOnClickListener(v -> {

            String mail = String.valueOf(mail_int.getText());
            String user = String.valueOf(user_int.getText());
            String passOne = String.valueOf(passOne_int.getText());
            String passTwo = String.valueOf(passTwo_int.getText());
            String steamId = String.valueOf(register_steam_id.getText());



            if (isValidEmail(mail) && !user.isEmpty() && !passOne.isEmpty() && !passTwo.isEmpty() && !steamId.isEmpty()) {

                if (passOne.equals(passTwo)) {

                    isValidSteamId().thenAccept(isValid -> {
                        if (isValid) {

                        String pass = Hashing.sha256().hashString(passOne, StandardCharsets.UTF_8).toString();

                        sesion.usuario = new Usuario(mail, user, pass);

                        Thread thread = new Thread(() -> {
                            if (comprobarCorreo(mail) < 0) {

                                RegistarUsuario(sesion.usuario);
                                Login(mail, pass);

                                SaveLoginRemember(1, getActivity(), mail, user);

                                int LoginID = DataBaseManager.Login(mail, Hashing.sha256().hashString(passOne, StandardCharsets.UTF_8).toString());
                                CurrentSession.setUserID(LoginID);
                                CurrentSession.setMail(mail);
                                CurrentSession.setUserName(user);
                                CurrentSession.setSteamId(steamId);


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
                            } else {
                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(getActivity(), "El correo ya existe", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }


                        });
                        thread.start();
                    }else {
                    getActivity().runOnUiThread(() ->
                            Toast.makeText(getActivity(), "La ID de Steam no es válida", Toast.LENGTH_SHORT).show());
                     }
                });

                getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getActivity(), "Las contraseñas deben coincidir", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            } else {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getActivity(), "Complete todos los campos", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });



    }
    private boolean isValidEmail(String email) {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private CompletableFuture<Boolean> isValidSteamId() {

        CompletableFuture<Boolean> future = new CompletableFuture<>();

        SteamWebApi.getSteamWebApiService().getPlayerSummaries(
                CurrentSession.getSteamId(),
                "json"
        ).enqueue(new Callback<GetPlayerSummariesResponse>() {
            @Override
            public void onResponse(Call<GetPlayerSummariesResponse> call, Response<GetPlayerSummariesResponse> response) {
                System.out.println(call.request());

                if (response.code() == 200) {
                    future.complete(true);
                } else {
                    future.complete(false);
                }
            }

            @Override
            public void onFailure(Call<GetPlayerSummariesResponse> call, Throwable t) {
                System.out.println(t.getMessage());
                future.complete(false);
            }
        });

        return future;
    }

}