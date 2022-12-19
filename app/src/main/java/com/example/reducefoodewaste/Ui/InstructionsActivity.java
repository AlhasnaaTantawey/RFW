package com.example.reducefoodewaste.Ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.reducefoodewaste.Maps.NearbyPlacesLocationMap;
import com.example.reducefoodewastebasic.R;

public class InstructionsActivity extends AppCompatActivity {
Button next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructions);
        next = findViewById(R.id.instructuin_button_next);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), UseAppAsDonerOrNeedFoodActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(InstructionsActivity.this,
                NearbyPlacesLocationMap.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);

    }
}

