package m13dam.grupo4.gamepinnacle;

import androidx.appcompat.app.AppCompatActivity;

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
import m13dam.grupo4.gamepinnacle.Types.CurrentSession;

public class MainActivity extends AppCompatActivity {

    Button login;
    EditText usuario;
    EditText contraseña;
    private CheckBox rememberme;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        login = findViewById(R.id.loginBoton);
        usuario = findViewById(R.id.loginUsuario);
        contraseña = findViewById(R.id.loginPassword);
        rememberme = findViewById(R.id.checkBox);

        Intent intent = new Intent(MainActivity.this, MainActivity2.class);
        startActivity(intent);

        Thread thread = new Thread(() -> {
            int RememberedID = DataBaseManager.LoginRemember(MainActivity.this);
            if(RememberedID > 0){
                CurrentSession.setUserID(RememberedID);
                Handler handler = new Handler(Looper.getMainLooper());

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        abrirNuevaActividad(false);
                    }
                });
            }
        });
        thread.start();

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               String usuarioIntroducido = usuario.getText().toString();
               String contraseñaIntroducido = contraseña.getText().toString();

                Thread thread = new Thread(() -> {
                    int RememberedID = DataBaseManager.LoginRemember(MainActivity.this);
                    if(RememberedID > 0){
                        CurrentSession.setUserID(RememberedID);
                    } else {
                        int LoginID = DataBaseManager.Login(usuarioIntroducido,contraseñaIntroducido);
                        CurrentSession.setUserID(LoginID);
                    }
                    Handler handler = new Handler(Looper.getMainLooper());

                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            abrirNuevaActividad(rememberme.isChecked());
                        }
                    });

                });
                thread.start();
            }
        });


    }
    private void abrirNuevaActividad(Boolean remember) {

        if (CurrentSession.getUserID() > 0) {
            Intent intent = new Intent(MainActivity.this, MainActivity2.class);
            startActivity(intent);
            if (remember){
                DataBaseManager.SaveLoginRemember(CurrentSession.getUserID(), MainActivity.this);
            }
            finish();
        } else {
            Toast.makeText(MainActivity.this, "Usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show();
        }
    }
}