package com.example.reducefoodewaste.Maps;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.reducefoodewastebasic.R;

public class InstructionsLocation extends AppCompatActivity {
    Button next ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructions_location);
        next = findViewById(R.id.first_page_button_ok_lets_go);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CurrentLocationMap.class);
                startActivity(intent);
            }
        });
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
