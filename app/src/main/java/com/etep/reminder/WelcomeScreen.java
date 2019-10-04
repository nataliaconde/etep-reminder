package com.etep.reminder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import java.util.Calendar;

public class WelcomeScreen extends AppCompatActivity {

    public static int TIME_OUT = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_screen);

        final TextView year = (TextView) findViewById(R.id.txtYear);
        year.setText(String.valueOf(Calendar.getInstance().get(Calendar.YEAR)));

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(WelcomeScreen.this, LogInScreen.class);
                startActivity(intent);
                finish();
            }
        }, TIME_OUT);

    }
}
