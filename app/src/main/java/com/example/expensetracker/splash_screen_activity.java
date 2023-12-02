package com.example.expensetracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

public class splash_screen_activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        // Using a Handler to hold for a certain duration before moving to the main activity
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                try{
                    startActivity(new Intent(splash_screen_activity.this, MainActivity.class));
                    finish();
                }
                catch(Exception e){
                    Toast.makeText(splash_screen_activity.this, "Error Raised In Splash Screen", Toast.LENGTH_SHORT).show();
                }
            }
        },3000);
    }
}