package com.etep.reminder;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
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
import com.parse.ParseInstallation;
import com.parse.ParseObject;
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
        final long TIME = 1 * 1000;
        final Button btnLogin = (Button) view.findViewById(R.id.btnLogin);
        final Button btnResetPassword = (Button) view.findViewById(R.id.idResetPassword);

        final EditText etLogin = (EditText) view.findViewById(R.id.etUsername);
        final EditText etPassword = (EditText) view.findViewById(R.id.etPassword);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean exists = checkIfEmpty(etLogin, etPassword);
                btnLogin.setBackgroundColor(Color.GRAY);
                btnLogin.setEnabled(false);

                if(!exists) {
                    ParseUser.logInInBackground(String.valueOf(etLogin.getText()), String.valueOf(etPassword.getText()), new LogInCallback() {
                        @Override
                        public void done(ParseUser parseUser, ParseException e) {
                            if (parseUser != null) {
                                boolean isEmailVefified = parseUser.getBoolean("emailVerified");
                                if (isEmailVefified) {
                                    goToActivity(ListTasksScreen.class);
                                    btnLogin.setBackgroundColor(Color.BLACK);
                                    btnLogin.setEnabled(true);
                                    // Store app language and version
                                    ParseInstallation installation = ParseInstallation.getCurrentInstallation();
                                    installation.put("userId", ParseObject.createWithoutData("_User", ParseUser.getCurrentUser().getObjectId()));
                                    installation.saveInBackground();
                                } else {
                                    Toast.makeText(getActivity(), getString(R.string.errorLoginEmailVerified), Toast.LENGTH_SHORT).show();
                                    btnLogin.setBackgroundColor(Color.BLACK);
                                    btnLogin.setEnabled(true);
                                }
                            }
                            else {
                                ParseUser.logOut();
                                Toast.makeText(getActivity(), getString(R.string.errorLoginDontMatch), Toast.LENGTH_SHORT).show();
                                btnLogin.setBackgroundColor(Color.BLACK);
                                btnLogin.setEnabled(true);
                            }
                        }
                    });
                } else {
                    btnLogin.setBackgroundColor(Color.BLACK);
                    btnLogin.setEnabled(true);
                }
            }
        });

        btnResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnResetPassword.setEnabled(false);
                btnLogin.setBackgroundColor(Color.GRAY);

                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        btnLogin.setBackgroundColor(Color.BLACK);
                        btnResetPassword.setEnabled(true);
                    }
                }, TIME);
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
