package m13dam.grupo4.gamepinnacle;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;

import m13dam.grupo4.gamepinnacle.Fragments.LoginMenu;
import m13dam.grupo4.gamepinnacle.Fragments.RegisterMenu;
import m13dam.grupo4.gamepinnacle.Types.GetNewsForApp;
import m13dam.grupo4.gamepinnacle.Types.GetNewsForAppResponse;
import m13dam.grupo4.gamepinnacle.Types.NewsItems;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.main_fragment_container, LoginMenu.class, null)
                .commit();

        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                Fragment frag = getSupportFragmentManager().findFragmentById(R.id.main_fragment_container);

                if (frag.getClass() == RegisterMenu.class){
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    fragmentManager.beginTransaction()
                            .replace(R.id.main_fragment_container, LoginMenu.class, null)
                            .commit();
                    return;
                }

            }
        });

    }

}