package com.example.reducefoodewaste.Ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.reducefoodewaste.Adapters.RecyclerViewAdapterChatting;
import com.example.reducefoodewaste.Models.MessageModel;
import com.example.reducefoodewastebasic.R;

import java.util.ArrayList;

public class ChattingActivity extends AppCompatActivity {
    ArrayList<MessageModel> messageModels;
    RecyclerViewAdapterChatting adapter;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatting);
        messageModels = new ArrayList<>();
        recyclerView = findViewById(R.id.recycler_gchat);
        //
        adapter = new RecyclerViewAdapterChatting(messageModels);
        messageModels.add(new MessageModel("June 10", R.drawable.ic_baseline_tag_faces_24, "John Grady Cole", "Hi Menaa,", R.drawable.ic_baseline_tag_faces_24, "8:00", R.drawable.ic_baseline_tag_faces_24, "John Grady Cole", "Hi ,", R.drawable.ic_baseline_tag_faces_24, "9:00"));
        messageModels.add(new MessageModel("June 10", R.drawable.ic_baseline_tag_faces_24, "John Grady Cole", "Hi Menaa,", R.drawable.ic_baseline_tag_faces_24, "8:00", R.drawable.ic_baseline_tag_faces_24, "John Grady Cole", "Hi ,", R.drawable.ic_baseline_tag_faces_24, "9:00"));
        messageModels.add(new MessageModel("June 10", R.drawable.ic_baseline_tag_faces_24, "John Grady Cole", "Hi Menaa,", R.drawable.ic_baseline_tag_faces_24, "8:00", R.drawable.ic_baseline_tag_faces_24, "John Grady Cole", "Hi ,", R.drawable.ic_baseline_tag_faces_24, "9:00"));
        messageModels.add(new MessageModel("June 10", R.drawable.ic_baseline_tag_faces_24, "John Grady Cole", "Hi Menaa,", R.drawable.ic_baseline_tag_faces_24, "8:00", R.drawable.ic_baseline_tag_faces_24, "John Grady Cole", "Hi ,", R.drawable.ic_baseline_tag_faces_24, "9:00"));
        messageModels.add(new MessageModel("June 10", R.drawable.ic_baseline_tag_faces_24, "John Grady Cole", "Hi Menaa,", R.drawable.ic_baseline_tag_faces_24, "8:00", R.drawable.ic_baseline_tag_faces_24, "John Grady Cole", "Hi ,", R.drawable.ic_baseline_tag_faces_24, "9:00"));
        messageModels.add(new MessageModel("June 10", R.drawable.ic_baseline_tag_faces_24, "John Grady Cole", "Hi Menaa,", R.drawable.ic_baseline_tag_faces_24, "8:00", R.drawable.ic_baseline_tag_faces_24, "John Grady Cole", "Hi ,", R.drawable.ic_baseline_tag_faces_24, "9:00"));
        messageModels.add(new MessageModel("June 10", R.drawable.ic_baseline_tag_faces_24, "John Grady Cole", "Hi Menaa,", R.drawable.ic_baseline_tag_faces_24, "8:00", R.drawable.ic_baseline_tag_faces_24, "John Grady Cole", "Hi ,", R.drawable.ic_baseline_tag_faces_24, "9:00"));
        messageModels.add(new MessageModel("June 10", R.drawable.ic_baseline_tag_faces_24, "John Grady Cole", "Hi Menaa,", R.drawable.ic_baseline_tag_faces_24, "8:00", R.drawable.ic_baseline_tag_faces_24, "John Grady Cole", "Hi ,", R.drawable.ic_baseline_tag_faces_24, "9:00"));
        messageModels.add(new MessageModel("June 10", R.drawable.ic_baseline_tag_faces_24, "John Grady Cole", "Hi Menaa,", R.drawable.ic_baseline_tag_faces_24, "8:00", R.drawable.ic_baseline_tag_faces_24, "John Grady Cole", "Hi ,", R.drawable.ic_baseline_tag_faces_24, "9:00"));
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    public void setAdapter() {
        recyclerView = findViewById(R.id.recycler_gchat);
        //
        adapter = new RecyclerViewAdapterChatting(messageModels);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    public void setDataImage() {
        messageModels.add(new MessageModel("June 10", R.drawable.ic_baseline_tag_faces_24, "John Grady Cole", "Hi Menaa,", R.drawable.ic_baseline_tag_faces_24, "8:00", R.drawable.ic_baseline_tag_faces_24, "John Grady Cole", "Hi ,", R.drawable.ic_baseline_tag_faces_24, "9:00"));
        messageModels.add(new MessageModel("June 10", R.drawable.ic_baseline_tag_faces_24, "John Grady Cole", "Hi Menaa,", R.drawable.ic_baseline_tag_faces_24, "8:00", R.drawable.ic_baseline_tag_faces_24, "John Grady Cole", "Hi ,", R.drawable.ic_baseline_tag_faces_24, "9:00"));
        messageModels.add(new MessageModel("June 10", R.drawable.ic_baseline_tag_faces_24, "John Grady Cole", "Hi Menaa,", R.drawable.ic_baseline_tag_faces_24, "8:00", R.drawable.ic_baseline_tag_faces_24, "John Grady Cole", "Hi ,", R.drawable.ic_baseline_tag_faces_24, "9:00"));
        messageModels.add(new MessageModel("June 10", R.drawable.ic_baseline_tag_faces_24, "John Grady Cole", "Hi Menaa,", R.drawable.ic_baseline_tag_faces_24, "8:00", R.drawable.ic_baseline_tag_faces_24, "John Grady Cole", "Hi ,", R.drawable.ic_baseline_tag_faces_24, "9:00"));
        messageModels.add(new MessageModel("June 10", R.drawable.ic_baseline_tag_faces_24, "John Grady Cole", "Hi Menaa,", R.drawable.ic_baseline_tag_faces_24, "8:00", R.drawable.ic_baseline_tag_faces_24, "John Grady Cole", "Hi ,", R.drawable.ic_baseline_tag_faces_24, "9:00"));
        messageModels.add(new MessageModel("June 10", R.drawable.ic_baseline_tag_faces_24, "John Grady Cole", "Hi Menaa,", R.drawable.ic_baseline_tag_faces_24, "8:00", R.drawable.ic_baseline_tag_faces_24, "John Grady Cole", "Hi ,", R.drawable.ic_baseline_tag_faces_24, "9:00"));
        messageModels.add(new MessageModel("June 10", R.drawable.ic_baseline_tag_faces_24, "John Grady Cole", "Hi Menaa,", R.drawable.ic_baseline_tag_faces_24, "8:00", R.drawable.ic_baseline_tag_faces_24, "John Grady Cole", "Hi ,", R.drawable.ic_baseline_tag_faces_24, "9:00"));
        messageModels.add(new MessageModel("June 10", R.drawable.ic_baseline_tag_faces_24, "John Grady Cole", "Hi Menaa,", R.drawable.ic_baseline_tag_faces_24, "8:00", R.drawable.ic_baseline_tag_faces_24, "John Grady Cole", "Hi ,", R.drawable.ic_baseline_tag_faces_24, "9:00"));
        messageModels.add(new MessageModel("June 10", R.drawable.ic_baseline_tag_faces_24, "John Grady Cole", "Hi Menaa,", R.drawable.ic_baseline_tag_faces_24, "8:00", R.drawable.ic_baseline_tag_faces_24, "John Grady Cole", "Hi ,", R.drawable.ic_baseline_tag_faces_24, "9:00"));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}