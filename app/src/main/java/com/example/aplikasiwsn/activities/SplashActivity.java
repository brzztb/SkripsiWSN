package com.example.aplikasiwsn.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.example.aplikasiwsn.R;
import com.example.aplikasiwsn.applications.CredentialSharedPreferences;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        CredentialSharedPreferences cred = new CredentialSharedPreferences(this);
        new Handler().postDelayed(new Runnable() {
            Date dateNow = new Date();

            //batas kadaluarsa token dianggap 14 hari
            //ini satuan ms
            long timeLimit = 1209600000;

            @Override
            public void run() {
                if(cred.loadToken().equalsIgnoreCase("NONE")||Math.abs(dateNow.getTime()-cred.loadLoginDate().getTime())>=timeLimit) {
                    cred.clearCredential();
                    startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                }
                else {
                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                }
            }
            },2000);
    }
}