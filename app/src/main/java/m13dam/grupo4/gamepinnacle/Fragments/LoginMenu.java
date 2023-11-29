package m13dam.grupo4.gamepinnacle.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.text.InputType;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import m13dam.grupo4.gamepinnacle.R;

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

    // UI

    ImageView email_img;
    EditText email_text;

    ImageView password_lock;
    EditText password_text;
    ImageView password_eye;
    boolean password_hidden = true;

    ConstraintLayout login_button;

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

        password_eye = view.findViewById(R.id.password_eye);
        password_text = view.findViewById(R.id.password_text);

        password_eye.setOnClickListener(v -> {

            if (password_hidden) {
                password_hidden = false;
                password_eye.setImageResource(R.drawable.eye_light);
                int sel_start = password_text.getSelectionStart();
                int sel_end = password_text.getSelectionEnd();
                password_text.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                password_text.setSelection(sel_start, sel_end);
            } else {
                password_hidden = true;
                password_eye.setImageResource(R.drawable.key_alt_light);
                int sel_start = password_text.getSelectionStart();
                int sel_end = password_text.getSelectionEnd();
                password_text.setTransformationMethod(PasswordTransformationMethod.getInstance());
                password_text.setSelection(sel_start, sel_end);
            }

        });

    }
}