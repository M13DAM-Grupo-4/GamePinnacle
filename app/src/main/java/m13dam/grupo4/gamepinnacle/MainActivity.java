package m13dam.grupo4.gamepinnacle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentContainer;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import m13dam.grupo4.gamepinnacle.DataBase.DataBaseManager;
import m13dam.grupo4.gamepinnacle.Fragments.LoginMenu;
import m13dam.grupo4.gamepinnacle.Types.CurrentSession;

public class MainActivity extends AppCompatActivity {

    FragmentContainer mainFragmentContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.main_fragment_container, LoginMenu.class, null)
                .commit();

    }

}