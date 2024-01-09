package m13dam.grupo4.gamepinnacle.Activities;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.os.Handler;

import m13dam.grupo4.gamepinnacle.BuildConfig;
import m13dam.grupo4.gamepinnacle.Classes.Other.CurrentSession;
import m13dam.grupo4.gamepinnacle.Classes.Other.MailManager;
import m13dam.grupo4.gamepinnacle.Classes.TwitchApi.GetAccessToken;
import m13dam.grupo4.gamepinnacle.Classes.TwitchApi.TwitchApi;
import m13dam.grupo4.gamepinnacle.DataBases.DataBaseManager;
import m13dam.grupo4.gamepinnacle.Fragments.Menus.AddFriendMenu;
import m13dam.grupo4.gamepinnacle.Fragments.Menus.AddGamesMenu;
import m13dam.grupo4.gamepinnacle.Fragments.Menus.FriendListMenu;
import m13dam.grupo4.gamepinnacle.Fragments.Menus.GameInfo;
import m13dam.grupo4.gamepinnacle.Fragments.Menus.GameListMenu;
import m13dam.grupo4.gamepinnacle.Fragments.Menus.LoginMenu;
import m13dam.grupo4.gamepinnacle.Fragments.Menus.PerfilFriendMenu;
import m13dam.grupo4.gamepinnacle.Fragments.Menus.PerfilUserMenu;
import m13dam.grupo4.gamepinnacle.Fragments.Menus.RegisterMenu;
import m13dam.grupo4.gamepinnacle.Fragments.Menus.SettingsMenu;
import m13dam.grupo4.gamepinnacle.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private boolean backButtonEnabled = true;
    private boolean backButtonTransitionInProgress = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        createTwitchToken();

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.main_fragment_container, LoginMenu.class, null)
                .commit();

        backButton();
        disableBackButton();

    }

    private void createTwitchToken() {
        new Thread(() -> {
            if (CurrentSession.getTwitchToken() != null) {
                return;
            }

            if (DataBaseManager.sqliteTwitchToken(MainActivity.this) != null){
                String token = DataBaseManager.sqliteTwitchToken(MainActivity.this);
                System.out.println("SQLITE TWITCH: " + token);
                CurrentSession.setTwitchToken(token);
                return;
            }

            TwitchApi.getTwitchApiService().getAccessToken(BuildConfig.twitchclientid, BuildConfig.twitchclientsecret, "client_credentials").enqueue(
                    new Callback<GetAccessToken>() {
                        @Override
                        public void onResponse(Call<GetAccessToken> call, Response<GetAccessToken> response) {
                            if (response.code() == 200){
                                CurrentSession.setTwitchToken(response.body().getAccessToken());
                            }
                        }

                        @Override
                        public void onFailure(Call<GetAccessToken> call, Throwable t) {
                            t.printStackTrace();
                        }
                    }
            );
        }).start();
    }

    private void backButton() {
        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                if (backButtonEnabled && !backButtonTransitionInProgress) {
                    disableBackButton();
                    backButtonTransitionInProgress = true;
                    Fragment fragContainer = getSupportFragmentManager().findFragmentById(R.id.main_fragment_container);

                    Fragment newFrag = null;

                    if (fragContainer != null) {
                        if (fragContainer.getClass() == RegisterMenu.class) {
                            newFrag = new LoginMenu();
                        }

                        if (fragContainer.getClass() == SettingsMenu.class) {
                            newFrag = new PerfilUserMenu();
                        }
                        if (fragContainer.getClass() == FriendListMenu.class) {
                            newFrag = new PerfilUserMenu();
                        }
                        if (fragContainer.getClass() == GameListMenu.class) {
                            newFrag = new PerfilUserMenu();
                        }
                        if (fragContainer.getClass() == PerfilFriendMenu.class) {
                            newFrag = new FriendListMenu();
                        }
                        if (fragContainer.getClass() == GameInfo.class) {
                            newFrag = new GameListMenu();
                        }
                        if (fragContainer.getClass() == AddFriendMenu.class) {
                            newFrag = new FriendListMenu();
                        }
                        if (fragContainer.getClass() == AddGamesMenu.class){
                            newFrag = new GameListMenu();
                        }

                    }

                    if (newFrag != null) {
                        FragmentManager fragmentManager = getSupportFragmentManager();
                        disableBackButton();
                        fragmentManager.beginTransaction()
                                .replace(R.id.main_fragment_container, newFrag, null)
                                .commit();

                        new Handler(getMainLooper()).postDelayed(() -> {
                            enableBackButton();
                        }, 2000);
                    }


                }
            }
        });
    }

    public void disableBackButton() {
        backButtonEnabled = false;
    }

    public void enableBackButton() {
        backButtonEnabled = true;
        backButtonTransitionInProgress = false; // Restablecer el indicador de transición
    }

}