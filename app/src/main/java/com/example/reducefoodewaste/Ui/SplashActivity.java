package com.example.reducefoodewaste.Ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import com.example.reducefoodewaste.Maps.InstructionsLocation;
import com.example.reducefoodewaste.loginActivity;
import com.example.reducefoodewastebasic.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SplashActivity extends AppCompatActivity {
    SharedPreferences login_sharpreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        login_sharpreferences = getSharedPreferences("login", Context.MODE_PRIVATE);

        boolean islogin = login_sharpreferences.getBoolean("islogin",false);
        //  FirebaseAuth.getInstance().signOut();

        Log.i("islogin", Boolean.toString(islogin) +"111");
        Log.i("islogin",  getcurrentuserid()+"em");

        Thread thread = new Thread(){
            @Override
            public void run() {
                try {
                    sleep(3000);
                    if (!getcurrentuserid().isEmpty()){
                        Intent intent = new Intent(SplashActivity.this, InstructionsLocation.class);
                        startActivity(intent);
                        finish();
                    }else {
                        Intent intent = new Intent(SplashActivity.this, loginActivity.class);
                        startActivity(intent);
                        finish();
                    }

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        thread.start();
    }

    public String getcurrentuserid() {
        FirebaseUser currentuser = FirebaseAuth.getInstance().getCurrentUser();
        String currentuserid = "";
        if (currentuser != null) {
            currentuserid = currentuser.getUid();
        }
        return currentuserid;
    }
}