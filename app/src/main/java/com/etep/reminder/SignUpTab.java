package com.etep.reminder;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class SignUpTab extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstance){
        View view = inflater.inflate(R.layout.sign_up_tab, container, false);
        final EditText etUsername = (EditText) view.findViewById(R.id.etUsername);
        final EditText etEmail = (EditText) view.findViewById(R.id.etEmail);
        final EditText etPassword = (EditText) view.findViewById(R.id.etPassword);
        final Button btnSignUp = (Button) view.findViewById(R.id.btnSignUp);
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = etUsername.getText().toString();
                String email = etEmail.getText().toString();
                String password = etPassword.getText().toString();
                boolean exists = checkIfEmpty(etUsername, etEmail, etPassword);

                if(!exists) {
                    ParseUser user = new ParseUser();
                    // Set the user's username and password, which can be obtained by a forms
                    user.setUsername(username);
                    user.setPassword(password);
                    user.setEmail(email);
                    user.signUpInBackground(new SignUpCallback() {
                        @Override
                        public void done(ParseException e) {
                            if (e == null) {
                                goToActivity(SendEmailConfirmation.class);
                            } else {
                                ParseUser.logOut();
                                Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
            }
        });

        return view;
    }


    public void goToActivity(Class<?> view) {
        Intent i = new Intent(getActivity(), view);
        startActivity(i);
    }

    public boolean checkIfEmpty(EditText etLogin,EditText etEmail, EditText etPassword){
        if(etLogin.getText().toString().isEmpty() || etEmail.getText().toString().isEmpty() || etPassword.getText().toString().isEmpty()){
            Toast.makeText(getActivity(), getString(R.string.errorLoginSignUpEmpty), Toast.LENGTH_SHORT).show();
            return true;
        } else {
            return false;
        }

    }

}
