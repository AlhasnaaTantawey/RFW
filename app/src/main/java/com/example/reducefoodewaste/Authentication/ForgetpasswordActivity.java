package com.example.reducefoodewaste.Authentication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.reducefoodewastebasic.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgetpasswordActivity extends AppCompatActivity {
Button button;
EditText email;
TextView required, valid;
FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        email=findViewById(R.id.forgetpassword_edittext_email);
        auth =FirebaseAuth.getInstance();
        required =findViewById(R.id.forgetpassword_textview4_tv);
      button=findViewById(R.id.forgetpassword_button_t1);
      button.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
             /* Intent intent=new Intent(getApplicationContext(),ChickMail.class);
              startActivity(intent);*/
              resetPasword();
          }
      });
    }
    private void resetPasword (){
        String mail = email.getText().toString().trim();
        if(mail.isEmpty()){
            required.setVisibility(View.VISIBLE);
            required.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(mail).matches()) {
            valid.setVisibility(View.VISIBLE);
            valid.requestFocus();
            return;
        }
        auth.sendPasswordResetEmail(mail).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Intent intent=new Intent(getApplicationContext(), CheckMailActivity.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(ForgetpasswordActivity.this,"tray again",Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
