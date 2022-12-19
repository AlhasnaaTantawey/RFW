package com.example.reducefoodewaste.Authentication;

import androidx.annotation.NonNull;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.reducefoodewaste.Maps.InstructionsLocation;
import com.example.reducefoodewaste.loginActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.OAuthProvider;
import com.google.firebase.firestore.FirebaseFirestore;

public class TwitterLoginActivity extends loginActivity {
    FirebaseAuth mauth = FirebaseAuth.getInstance();
    FirebaseFirestore db= FirebaseFirestore.getInstance();
    private static final String TAG = "TwitterLoginActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        OAuthProvider.Builder  provider = OAuthProvider.newBuilder("twitter.com");
        Task<AuthResult> pendingResultTask = mauth.getPendingAuthResult();
        if (pendingResultTask != null) {
            // There's something already here! Finish the sign-in for your user.
            pendingResultTask
                    .addOnSuccessListener(
                            new OnSuccessListener<AuthResult>() {
                                @Override
                                public void onSuccess(AuthResult authResult) {
                                    startActivity(new Intent(TwitterLoginActivity.this, InstructionsLocation.class));
                                    Toast.makeText(TwitterLoginActivity.this, "login successfully", Toast.LENGTH_SHORT).show();
                                }
                            })
                    .addOnFailureListener(
                            new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                }
                            });
        } else {
            // There's no pending result so you need to start the sign-in flow.
            // See below.
            mauth
                    .startActivityForSignInWithProvider(/* activity= */ this, provider.build())
                    .addOnSuccessListener(
                            new OnSuccessListener<AuthResult>() {
                                @Override
                                public void onSuccess(AuthResult authResult) {
                                    startActivity(new Intent(TwitterLoginActivity.this, InstructionsLocation.class));
                                    Toast.makeText(TwitterLoginActivity.this, "login successfully", Toast.LENGTH_SHORT).show();
                                   // FirebaseUser fireUser = authResult.getUser();
                                    //String uid= authResult.getUser().getUid();
                                   // db.collection("myuser").document().set(uid);
                                }
                            })
                    .addOnFailureListener(
                            new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    // Handle failure.
                                    Log.d(TAG, "onFailure: "+e.getLocalizedMessage());
                                }
                            });
        }
    }
}