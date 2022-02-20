package com.snoozieapp.app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

// Splash Screen that shows up for a few seconds with Snoozie Logo
public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        
        //WindowInsetsController.hide(WindowInsets.Type.statusBars());

        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashScreen.this, HomePage.class);
                startActivity(intent);
                finish();
            }
        }, 3000);
    }
}