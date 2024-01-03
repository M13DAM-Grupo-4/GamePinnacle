package m13dam.grupo4.gamepinnacle.Activities;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;

import m13dam.grupo4.gamepinnacle.Fragments.Menus.AddFriendMenu;
import m13dam.grupo4.gamepinnacle.Fragments.Menus.FriendInfo;
import m13dam.grupo4.gamepinnacle.Fragments.Menus.FriendListMenu;
import m13dam.grupo4.gamepinnacle.Fragments.Menus.GameInfo;
import m13dam.grupo4.gamepinnacle.Fragments.Menus.GameListMenu;
import m13dam.grupo4.gamepinnacle.Fragments.Menus.LoginMenu;
import m13dam.grupo4.gamepinnacle.Fragments.Menus.PerfilUserMenu;
import m13dam.grupo4.gamepinnacle.Fragments.Menus.RegisterMenu;
import m13dam.grupo4.gamepinnacle.Fragments.Menus.SettingsMenu;
import m13dam.grupo4.gamepinnacle.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.main_fragment_container, LoginMenu.class, null)
                .commit();

        backButton();



    }

    private void backButton() {
        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                Fragment fragContainer = getSupportFragmentManager().findFragmentById(R.id.main_fragment_container);

                Fragment newFrag = null;

                if (fragContainer == null) {
                    return;
                }

                if (fragContainer.getClass() == RegisterMenu.class){
                    newFrag = new LoginMenu();
                }

                if (fragContainer.getClass() == SettingsMenu.class){
                    newFrag = new PerfilUserMenu();
                }
                if (fragContainer.getClass() == FriendListMenu.class){
                    newFrag = new PerfilUserMenu();
                }
                if (fragContainer.getClass() == GameListMenu.class){
                    newFrag = new PerfilUserMenu();
                }
                if (fragContainer.getClass() == FriendInfo.class){
                    newFrag = new FriendListMenu();
                }
                if (fragContainer.getClass() == GameInfo.class){
                    newFrag = new GameListMenu();
                }
                if (fragContainer.getClass() == AddFriendMenu.class){
                    newFrag = new FriendListMenu();
                }

                if (newFrag != null) {
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    fragmentManager.beginTransaction()
                            .replace(R.id.main_fragment_container, newFrag, null)
                            .commit();
                }

            }
        });
    }

}