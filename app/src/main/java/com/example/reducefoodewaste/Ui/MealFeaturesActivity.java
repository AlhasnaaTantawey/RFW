package com.example.reducefoodewaste.Ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.reducefoodewaste.Maps.InstructionsLocation;
import com.example.reducefoodewaste.Models.MealModel;
import com.example.reducefoodewaste.Adapters.RecyclerViewAdapterMainPageActivity;
import com.example.reducefoodewaste.Models.UserModel;
import com.example.reducefoodewaste.loginActivity;
import com.example.reducefoodewastebasic.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Objects;

public class MealFeaturesActivity extends AppCompatActivity {
    Button request;
    FirebaseFirestore db;
    private String mealid;
    private int views,newcounter;
    MealModel mealModel;
    ImageView userimage,mealphoto;
    TextView mealName, username,  details, location, numPerson, view,chatting;
    SharedPreferences login_sharpreferences;
    String musername,muserimage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal_features);

        login_sharpreferences = getSharedPreferences("login", Context.MODE_PRIVATE);
        muserimage = login_sharpreferences.getString("user_photo","");
        musername = login_sharpreferences.getString("username","");
        Log.i("userimage",muserimage);
        //getuser();
        db = FirebaseFirestore.getInstance();
        userimage = findViewById(R.id.mimg);
        chatting=findViewById(R.id.smokingpaper_textView_chatWithMe);
        mealphoto = findViewById(R.id.smokingpaper_imageView_meal);
        mealName = findViewById(R.id.smokingpaper_textView_mealname);
        username = findViewById(R.id.list_view_mainPage_textView_userName);
        details = findViewById(R.id.smokingpaper_textView_DetailsMeal);
        location = findViewById(R.id.smokingpaper_textView_Location);
        numPerson = findViewById(R.id.smokingpaper_NumberPerson);
        view = findViewById(R.id.view_counter);
        Intent intent = getIntent();
        mealModel = (MealModel) intent.getSerializableExtra(RecyclerViewAdapterMainPageActivity.keyName);
        mealName.setText(mealModel.getMealName());
        details.setText(mealModel.getMealDeatails());
        location.setText(mealModel.getLocation());
        numPerson.setText(String.valueOf(mealModel.getPersonNumber()));
        view.setText(String.valueOf(mealModel.getView()));
        String url_image = mealModel.getPhoto();
        mealid = mealModel.getdocid();
        views = mealModel.getView();
        newcounter = views + 1;
        username.setText(mealModel.getUserName());
        Picasso.get().load(url_image).into(mealphoto);
        if (!mealModel.getUserImage().isEmpty()) {
            Picasso.get().load(mealModel.getUserImage()).into(userimage);
        }
        updateviews(mealid,newcounter);

        //chatting textview onclick
        chatting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MealFeaturesActivity.this, ChattingActivity.class);
                startActivity(intent);
            }
        });
//chatting textview ontouch
        chatting.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                chatting.setTextColor(getResources().getColor(R.color.black));
                return false;
            }
        });

        request = findViewById(R.id.smokingpaper__button_request);
        request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestMethod();
            }
        });
    }

    public void updateviews( String docid,int count) {
        db.collection("MealModel")
                .document(docid)
                .update("view",count)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        // Toast.makeText(SmokingPaper.this,"mealModel updated successfully",Toast.LENGTH_LONG).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                // Toast.makeText(SmokingPaper.this,e.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        chatting.setTextColor(getResources().getColor(R.color.teal_200));
    }
    //dailog for Request button
    private void requestMethod() {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(this);
        mBuilder.setMessage("Are you sure to request this mealModel  ?");
        mBuilder.setNegativeButton("Cancle", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });

        mBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
                request.setClickable(false);
                request.getBackground().setAlpha(40);
                updateData();
            }
        });
        AlertDialog mDialog = mBuilder.create();
        mDialog.show();
    }

    private void updateData() {
        db.collection("MealModel")
                .document(mealid)
                .update("isRequested","true")
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(MealFeaturesActivity.this,"Requested successfully!",Toast.LENGTH_LONG).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(MealFeaturesActivity.this,"Failed! "+e.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
    }
    private void getuser(){

        FirebaseFirestore.getInstance().collection("users")
                .document(getcurrentuserid()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        UserModel user=  documentSnapshot.toObject(UserModel.class);
                        Log.i("musser",user.toString());
                        if (!Objects.equals(user.getImageUrl(), "")) {
                            Picasso.get().load(user.getImageUrl()).into(userimage);
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(MealFeaturesActivity.this, "Failed "+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public String getcurrentuserid() {
        FirebaseUser currentuser = FirebaseAuth.getInstance().getCurrentUser();
        String currentuserid = "";
        if (currentuser != null) {
            currentuserid = currentuser.getUid();
        }
        return currentuserid;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


}