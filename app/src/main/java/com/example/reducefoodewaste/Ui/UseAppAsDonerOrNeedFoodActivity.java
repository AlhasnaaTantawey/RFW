package com.example.reducefoodewaste.Ui;

import static android.app.ProgressDialog.show;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.reducefoodewastebasic.R;

public class UseAppAsDonerOrNeedFoodActivity extends AppCompatActivity {
 Button btsend;
RadioButton radioButton1,radioButton2;
TextView textView;
private Boolean checkedbutton;
    SharedPreferences login_sharpreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_use_app_as_doner_or_need_food);
        textView=findViewById( R.id.use_as_textview_invalid);
            radioButton1 = findViewById(R.id.radioButton);
            radioButton2 = findViewById(R.id.radioButton2);
            btsend=findViewById( R.id.button3);
            //when click on any radiobutton
        login_sharpreferences = getSharedPreferences("login", Context.MODE_PRIVATE);
        btsend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                checkedbutton = radioButton1.isChecked();

                SharedPreferences.Editor editor = login_sharpreferences.edit();
                editor.putBoolean("user_type",checkedbutton);
                editor.apply();
                if (!radioButton1.isChecked()&&!radioButton2.isChecked()) {
                    textView.setVisibility(View.VISIBLE);
                }else if(radioButton1.isChecked()) {
                    Intent intent = new Intent(getApplicationContext(),AddMeal.class);
                   // intent.putExtra("checked",checkedbutton);
                    startActivity(intent);
                    textView.setVisibility(View.INVISIBLE);
                }else if(radioButton2.isChecked()){
                    Intent intent = new Intent(getApplicationContext(), MainPageActivity.class);
                  //  intent.putExtra("checked",checkedbutton);
                    startActivity(intent);
                    textView.setVisibility(View.INVISIBLE);
                }
            }

        });

        }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(UseAppAsDonerOrNeedFoodActivity.this, InstructionsActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}



