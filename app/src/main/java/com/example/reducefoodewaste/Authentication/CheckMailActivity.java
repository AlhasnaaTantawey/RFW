package com.example.reducefoodewaste.Authentication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.reducefoodewastebasic.R;
import com.example.reducefoodewaste.loginActivity;
import com.google.firebase.auth.FirebaseAuth;

import java.util.concurrent.TimeUnit;

public class CheckMailActivity extends AppCompatActivity {
Button ok ;
TextView timer ,resend ;
FirebaseAuth auth1 ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chick_mail);
        ok = findViewById(R.id.chickmail_button_bt);
        auth1 =FirebaseAuth.getInstance();
        resend=findViewById(R.id.chickmail_resend_tv);
        timer=findViewById(R.id.chickmail_textView7);
        long duration = TimeUnit.MINUTES.toMillis(1);
        new CountDownTimer(10000, 1000) {
            public void onTick(long millisUntilFinished) {
                timer.setText(""+ millisUntilFinished / 1000);
            }
            public void onFinish() {
               resend.setVisibility(View.VISIBLE);
            }
        }.start();
resend.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent intent = new Intent(getApplicationContext(), ForgetpasswordActivity.class);
        startActivity(intent);
        finish();
    }
});
    ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CheckMailActivity.this, loginActivity
                        .class);
                startActivity(intent);
                finish();
            }
        });
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}