package com.etep.reminder;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Checkable;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
                btnSignUp.setBackgroundColor(Color.GRAY);
                btnSignUp.setEnabled(true);
                String username = etUsername.getText().toString();
                String email = etEmail.getText().toString();
                String password = etPassword.getText().toString();
                boolean exists = checkIfEmpty(etUsername, etEmail, etPassword);
                if(!exists) {
                    boolean length = checkLength(etUsername, etEmail, etPassword);
                    if(!length) {
                        boolean checkEmail = checkEmailCorrect(etEmail.getText().toString());
                        if (!checkEmail) {
                            ParseUser user = new ParseUser();
                            // Set the user's username and password, which can be obtained by a forms
                            user.setUsername(username);
                            user.setPassword(password);
                            user.setEmail(email);
                            user.signUpInBackground(new SignUpCallback() {
                                @Override
                                public void done(ParseException e) {
                                    if (e == null) {
                                        ParseUser.logOut();
                                        goToActivity(SendEmailConfirmation.class, getString(R.string.SignUpTitle), getString(R.string.SignUpEmailVerificationSent));
                                        btnSignUp.setBackgroundColor(Color.BLACK);
                                        btnSignUp.setEnabled(false);
                                    }
                                    else{
                                        ParseUser.logOut();
                                        if(e.getCode() == 202){
                                            Toast.makeText(getActivity(), getString(R.string.UsernameTaken), Toast.LENGTH_LONG).show();
                                            btnSignUp.setBackgroundColor(Color.BLACK);
                                            btnSignUp.setEnabled(false);
                                        } else if(e.getCode() == 203){
                                            ParseUser.logOut();
                                            Toast.makeText(getActivity(), getString(R.string.emailAlreadyExists), Toast.LENGTH_LONG).show();
                                            btnSignUp.setBackgroundColor(Color.BLACK);
                                            btnSignUp.setEnabled(false);
                                        } else {
                                            Toast.makeText(getActivity(), getString(R.string.errorNotKnown), Toast.LENGTH_LONG).show();
                                            btnSignUp.setBackgroundColor(Color.BLACK);
                                            btnSignUp.setEnabled(false);
                                        }
                                    }
                                }
                            });
                        }
                    } else {
                        btnSignUp.setBackgroundColor(Color.BLACK);
                        btnSignUp.setEnabled(false);
                    }
                } else {
                    btnSignUp.setBackgroundColor(Color.BLACK);
                    btnSignUp.setEnabled(false);
                }
            }
        });

        return view;
    }


    public void goToActivity(Class<?> view, String title, String description) {
        Intent i = new Intent(getActivity(), view);
        i.putExtra("title", title);
        i.putExtra("description", description);
        startActivity(i);
    }

    public boolean checkLength(EditText etLogin,EditText etEmail, EditText etPassword){
        if((etLogin.getText().length() < 3) || (etEmail.getText().length() < 3) || (etPassword.getText().length() < 3)) {
            Toast.makeText(getActivity(), getString(R.string.errorSignUpLength), Toast.LENGTH_SHORT).show();
            return true;
        }
        return false;
    }

    public boolean checkIfEmpty(EditText etLogin,EditText etEmail, EditText etPassword){
        if(etLogin.getText().toString().isEmpty() || etEmail.getText().toString().isEmpty() || etPassword.getText().toString().isEmpty()){
            Toast.makeText(getActivity(), getString(R.string.errorLoginSignUpEmpty), Toast.LENGTH_SHORT).show();
            return true;
        } else{
            return false;
        }

    }

    boolean checkEmailCorrect(String Email) {
        String pttn = "^\\D.+@.+\\.[a-z]+";
        Pattern p = Pattern.compile(pttn);
        Matcher m = p.matcher(Email);

        if (m.matches()) {
            return false;
        } else {
            Toast.makeText(getActivity(), getString(R.string.errorSignUpEmailFormat), Toast.LENGTH_SHORT).show();
            return true;
        }
    }

}
