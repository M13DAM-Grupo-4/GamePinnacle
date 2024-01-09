package m13dam.grupo4.gamepinnacle.Fragments.Menus;

import static m13dam.grupo4.gamepinnacle.DataBases.DataBaseManager.SaveLoginRemember;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

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

import com.google.common.hash.HashCode;
import com.google.common.hash.Hashing;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.CompletableFuture;

import m13dam.grupo4.gamepinnacle.Activities.MainActivity;
import m13dam.grupo4.gamepinnacle.BuildConfig;
import m13dam.grupo4.gamepinnacle.Classes.TwitchApi.TwitchApi;
import m13dam.grupo4.gamepinnacle.DataBases.DataBaseManager;
import m13dam.grupo4.gamepinnacle.R;
import m13dam.grupo4.gamepinnacle.Classes.SteamWebApi.SteamWebApi;
import m13dam.grupo4.gamepinnacle.Classes.Other.CurrentSession;
import m13dam.grupo4.gamepinnacle.Classes.SteamWebApi.GetPlayerSummariesResponse;
import m13dam.grupo4.gamepinnacle.Classes.Other.Usuario;
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
    EditText register_steam_id;
    EditText register_steamapikey;

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
        return inflater.inflate(R.layout.fragment_add_user_menu, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        enableButtons();

        register_menu_ui = getActivity().findViewById(R.id.register_menu_ui);
        register_menu_title = getActivity().findViewById(R.id.register_menu_title);

        Animation anim = AnimationUtils.loadAnimation(getActivity(), R.anim.blink);
        register_menu_ui.startAnimation(anim);
        register_menu_title.startAnimation(anim);

        register_password_text = getActivity().findViewById(R.id.register_password_text);

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

        mail_int = view.findViewById(R.id.register_email_text);
        user_int = view.findViewById(R.id.register_username_text);
        passOne_int = view.findViewById(R.id.register_password_text);
        register_steam_id = view.findViewById(R.id.register_steamid_text);
        register_steamapikey = view.findViewById(R.id.register_steamapikey_text);

        registrar = view.findViewById(R.id.login_login_button);

        registrar.setOnClickListener(v -> {

            String email = mail_int.getText().toString();
            String user = user_int.getText().toString();
            HashCode passOneHash = Hashing.sha256().hashString(passOne_int.getText().toString(), StandardCharsets.UTF_8);
            String steamId = register_steam_id.getText().toString();
            String steamApiKey = register_steamapikey.getText().toString();

            new Thread(() -> {

                Looper.prepare();

                if (user.length() == 0){
                    Toast.makeText(getActivity(), "El usuario esta vacio", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!isValidEmail(email)) {
                    Toast.makeText(getActivity(), "El correo no es valido", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (emailExistsInDatabase(email)) {
                    Toast.makeText(getActivity(), "Ya existe una cuenta con este correo", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!isSteamApiKeyValid(steamApiKey)) {
                    Toast.makeText(getActivity(), "El SteamApiKey no es valido", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!isSteamIdValid(steamId, steamApiKey)) {
                    Toast.makeText(getActivity(), "El SteamID no es valido", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!isPasswordValid(passOneHash)) {
                    Toast.makeText(getActivity(), "Las contraseña no es valida", Toast.LENGTH_SHORT).show();
                    return;
                }

                Usuario nuevoUsuario = new Usuario(-1, email, user, passOneHash.toString(), steamId);

                int UserId = DataBaseManager.RegistarUsuario(nuevoUsuario, steamApiKey);

                CurrentSession.setUsuario(nuevoUsuario);
                CurrentSession.setSteamApiKey(steamApiKey);

                nuevoUsuario.setId(UserId);

                SaveLoginRemember(getActivity(), nuevoUsuario, CurrentSession.getTwitchToken());



                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.main_fragment_container, PerfilUserMenu.class, null)
                        .commit();

            }).start();

        });

    }
    private boolean isValidEmail(String email) {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private boolean emailExistsInDatabase(String email) {
        return DataBaseManager.comprobarCorreo(email);
    }

    private boolean isPasswordValid(HashCode password) {

        if (password.equals(Hashing.sha256().hashString("", StandardCharsets.UTF_8))) {
            return false;
        }

        return true;
    }

    private boolean passwordsMatch(HashCode password1, HashCode password2) {
        return password1.equals(password2);
    }

    private boolean isSteamIdValid(String SteamID, String SteamAPIKey){

        if (SteamID.length() != 17) {
            return false;
        }

        try {
            Response<GetPlayerSummariesResponse> res = SteamWebApi.getSteamWebApiService().getPlayerSummaries(SteamAPIKey, SteamID, "json").execute();

            if (res.code() != 200){
                return false;
            }

            if (res.body().getPlayerSummaries().getPlayers().size() == 0) {
                return false;
            }

            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    private boolean isSteamApiKeyValid(String SteamApiKey) {

        if (SteamApiKey.length() == 32) {
            return true;
        }

        try {
            Response<GetPlayerSummariesResponse> res = SteamWebApi.getSteamWebApiService().getPlayerSummaries(CurrentSession.getSteamApiKey(), "76561197960435530", "json").execute();

            if (res.code() != 200){
                return false;
            }

            if (res.body().getPlayerSummaries().getPlayers().size() == 0) {
                return false;
            }

            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;

    }

    private CompletableFuture<Boolean> isValidSteamId() {

        CompletableFuture<Boolean> future = new CompletableFuture<>();

        SteamWebApi.getSteamWebApiService().getPlayerSummaries(
                CurrentSession.getSteamApiKey(),
                CurrentSession.getUsuario().getSteamid(),
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
    private void enableButtons() {
        if (isAdded()) {

            ((MainActivity) requireActivity()).enableBackButton();
        }
        // Habilitar otros botones según sea necesario
    }
}