package com.etep.reminder;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

public class LoginTab extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstance){
        View view = inflater.inflate(R.layout.log_in_tab, container, false);

        ParseUser currentUser = ParseUser.getCurrentUser();
        if (currentUser != null) {
            goToActivity(ListTasksScreen.class);
        }

        Button btnLogin = (Button) view.findViewById(R.id.btnLogin);
        final Button signUp = (Button) view.findViewById(R.id.btnSignUp);
        Button btnResetPassword = (Button) view.findViewById(R.id.idResetPassword);

        final EditText etLogin = (EditText) view.findViewById(R.id.etUsername);
        final EditText etPassword = (EditText) view.findViewById(R.id.etPassword);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean exists = checkIfEmpty(etLogin, etPassword);

                if(!exists) {
                    ParseUser.logInInBackground(String.valueOf(etLogin.getText()), String.valueOf(etPassword.getText()), new LogInCallback() {
                        @Override
                        public void done(ParseUser parseUser, ParseException e) {
                            if (parseUser != null) {
                                boolean isEmailVefified = parseUser.getBoolean("emailVerified");
                                if (isEmailVefified) {
                                    goToActivity(ListTasksScreen.class);
                                } else {
                                    Toast.makeText(getActivity(), getString(R.string.errorLoginEmailVerified), Toast.LENGTH_SHORT).show();
                                }
                            }
                            else {
                                ParseUser.logOut();
                                Toast.makeText(getActivity(), getString(R.string.errorLoginDontMatch), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });

        btnResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToActivity(ResetPasswordScreen.class);
            }
        });

        return view;
    }

    public void goToActivity(Class<?> view) {
        Intent i = new Intent(getActivity(), view);
        startActivity(i);
    }

    public boolean checkIfEmpty(EditText etLogin,EditText etPassword){
        if(etLogin.getText().toString().isEmpty() || etPassword.getText().toString().isEmpty()){
            Toast.makeText(getActivity(), getString(R.string.errorLoginSignUpEmpty), Toast.LENGTH_SHORT).show();
            return true;
        } else {
            return false;
        }

    }

}
