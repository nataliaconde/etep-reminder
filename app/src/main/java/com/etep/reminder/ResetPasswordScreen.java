package com.etep.reminder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.RequestPasswordResetCallback;

public class ResetPasswordScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password_screen);

        final Button btnResetPassword = (Button) findViewById(R.id.btnSendForgotPasswordEmail);
        final EditText etLogin = (EditText) findViewById(R.id.edtEmail);

        btnResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnResetPassword.setBackgroundColor(Color.GRAY);
                btnResetPassword.setEnabled(false);
                boolean exists = checkIfEmpty(etLogin);
                if (!exists) {
                    ParseUser.requestPasswordResetInBackground(etLogin.getText().toString(), new RequestPasswordResetCallback() {
                        public void done(ParseException e) {
                         if (e == null) {
                             goToActivity(SendEmailConfirmation.class, getString(R.string.ForgotPasswordTitle), getString(R.string.ForgotPasswordEmailVerificationSent));
                             btnResetPassword.setBackgroundColor(Color.BLACK);
                             btnResetPassword.setEnabled(true);
                         } else {
                             Toast.makeText(ResetPasswordScreen.this, getString(R.string.ForgotPasswordEmailVerificationNotSent), Toast.LENGTH_LONG).show();
                             btnResetPassword.setBackgroundColor(Color.BLACK);
                             btnResetPassword.setEnabled(true);
                         }
                        }
                    });

                }
            }
        });
    }

    public void goToActivity(Class<?> view, String title, String description) {
        Intent i = new Intent(ResetPasswordScreen.this, view);
        i.putExtra("title", title);
        i.putExtra("description", description);
        startActivity(i);
    }

    public boolean checkIfEmpty(EditText etLogin){
        if(etLogin.getText().toString().isEmpty()){
            Toast.makeText(this, getString(R.string.errorLoginSignUpEmpty), Toast.LENGTH_SHORT).show();
            return true;
        } else {
            return false;
        }

    }
}
