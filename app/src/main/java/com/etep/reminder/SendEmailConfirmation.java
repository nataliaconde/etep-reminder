package com.etep.reminder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.parse.ParseUser;

public class SendEmailConfirmation extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_email_confirmation);

        String titleString = getIntent().getStringExtra("title");
        String titleDescription = getIntent().getStringExtra("description");
        TextView title = (TextView) findViewById(R.id.txtTitle);
        TextView description = (TextView) findViewById(R.id.txtDescription);

        title.setText(titleString);
        description.setText(titleDescription);

        final Button btnSendForgotPasswordEmail = (Button) findViewById(R.id.btnSendForgotPasswordEmail);
        btnSendForgotPasswordEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnSendForgotPasswordEmail.setBackgroundColor(Color.GRAY);
                btnSendForgotPasswordEmail.setEnabled(false);
                ParseUser.logOut();
                goToActivity(LogInScreen.class);
                btnSendForgotPasswordEmail.setBackgroundColor(Color.BLACK);
                btnSendForgotPasswordEmail.setEnabled(true);
            }
        });
    }

    public void goToActivity(Class<?> view) {
        Intent i = new Intent(SendEmailConfirmation.this, view);
        startActivity(i);
    }
}
