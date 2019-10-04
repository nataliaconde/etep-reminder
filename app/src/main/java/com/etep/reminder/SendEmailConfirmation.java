package com.etep.reminder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class SendEmailConfirmation extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_email_confirmation);

        Glide.with(this)
                .load(R.drawable.animated_email)
                .asGif()
                .into((ImageView) findViewById(R.id.imageViewEmail));

        Intent intent = new Intent(SendEmailConfirmation.this, ResetPasswordScreen.class);
        String titleString = intent.getStringExtra("title");
        String titleDescription = intent.getStringExtra("description");
        TextView title = (TextView) findViewById(R.id.txtTitle);
        TextView description = (TextView) findViewById(R.id.txtDescription);

        title.setText(titleString);
        description.setText(titleDescription);
    }
}
