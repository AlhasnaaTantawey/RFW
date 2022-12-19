package com.example.reducefoodewaste;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.util.Patterns;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.reducefoodewaste.Adapters.RecyclerViewAdapterMainPageActivity;
import com.example.reducefoodewaste.Authentication.TwitterLoginActivity;
import com.example.reducefoodewaste.Authentication.ForgetpasswordActivity;


import com.example.reducefoodewaste.Maps.InstructionsLocation;
import com.example.reducefoodewaste.Models.MealModel;
import com.example.reducefoodewaste.Models.UserModel;
import com.example.reducefoodewastebasic.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.regex.Pattern;

public class loginActivity extends AppCompatActivity {
    Button button;
    ImageView tw, go,face;
    EditText email, password;
    TextView textView, textView1, textView2, forgetText,textView3 , textView4;
    boolean passwordvisible;
    SharedPreferences sharpreferences;
    UserModel user;
    SharedPreferences login_sharpreferences;


    private static final String EMAIL = "email";
    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^" +
                    "(?=.*[a-zA-Z])" +      //any letter
                    "(?=.*[@#$%^&+=])" +    //at least 1 special character
                    "(?=\\S+$)" +           //no white spaces
                    ".{4,}" +               //at least 4 characters
                    "$");
    FirebaseAuth mauth = FirebaseAuth.getInstance();
    private static final String TAG = "GoogleActivity";
    private static final int RC_SIGN_IN = 9001;
    private GoogleSignInClient mGoogleSignInClient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        sharpreferences = getSharedPreferences("pref", Context.MODE_PRIVATE);
        login_sharpreferences = getSharedPreferences("login", Context.MODE_PRIVATE);



        button = findViewById(R.id.login_button_btn1);
        tw = findViewById(R.id.login_twiter_imageView);
        go = findViewById(R.id.login_google_imageView);
        face=findViewById(R.id.login_facebook);
        email = findViewById(R.id.loin_edittext_email);
        password = findViewById(R.id.login_edittext_password);
        textView = findViewById(R.id.login_textview_signup);
        textView1 = findViewById(R.id.login_t1);
        textView2 = findViewById(R.id.login_t2);
        textView3 = findViewById(R.id.login_t3);
        textView4 = findViewById(R.id.login_t4);
        forgetText = findViewById(R.id.login_textView3_tv);
        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        password.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                final int Right = 2;
                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    if (motionEvent.getRawX() >= password.getRight() - password.getCompoundDrawables()[Right].getBounds().width()) {
                        int selection = password.getSelectionEnd();
                        if (passwordvisible) {
                            password.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.ic_baseline_visibility_off_24, 0);
                            password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                            passwordvisible = false;
                        } else {
                            password.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.ic_baseline_visibility_24, 0);
                            password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                            passwordvisible = true;
                        }
                        password.setSelection(selection);
                        return true;
                    }
                }
                return false;
            }
        });

        password.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                final int Right = 2;
                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    if (motionEvent.getRawX() >= password.getRight() - password.getCompoundDrawables()[Right].getBounds().width()) {
                        int selection = password.getSelectionEnd();
                        if (passwordvisible) {
                            password.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.ic_baseline_visibility_off_24, 0);
                            password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                            passwordvisible = false;
                        } else {
                            password.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.ic_baseline_visibility_24, 0);
                            password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                            passwordvisible = true;
                        }
                        password.setSelection(selection);
                        return true;
                    }
                }
                return false;
            }
        });

        face.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(loginActivity.this,FacebookActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
            }
        });
        tw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(loginActivity.this, TwitterLoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
            }
        });

        go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GooglesignIn();
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String emailInput = email.getText().toString().trim();
                String passwordInput = password.getText().toString().trim();
                if ((!email.getText().toString().isEmpty()) && !password.getText().toString().isEmpty()) {
                    if ((!Patterns.EMAIL_ADDRESS.matcher(emailInput).matches())&&(!PASSWORD_PATTERN.matcher(passwordInput).matches())) {
                        textView3.setVisibility(View.VISIBLE);
                        textView4.setVisibility(View.VISIBLE);
                    }else  if ((Patterns.EMAIL_ADDRESS.matcher(emailInput).matches())&&(!PASSWORD_PATTERN.matcher(passwordInput).matches())){
                        textView4.setVisibility(View.VISIBLE);
                    }else  if ((!Patterns.EMAIL_ADDRESS.matcher(emailInput).matches())&&(!PASSWORD_PATTERN.matcher(passwordInput).matches())){
                        textView3.setVisibility(View.VISIBLE);
                    }else{
                        login();
                    }


                } else if ((email.getText().toString().isEmpty()) && !password.getText().toString().isEmpty()) {
                    textView2.setVisibility(View.VISIBLE);
                } else if ((email.getText().toString().isEmpty()) && password.getText().toString().isEmpty()){
                    textView1.setVisibility(View.VISIBLE);
                    textView2.setVisibility(View.VISIBLE);
                } else if ((!email.getText().toString().isEmpty()) && password.getText().toString().isEmpty()) {
                    textView1.setVisibility(View.VISIBLE);
                }
            }
        });
        email.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                textView2.setVisibility(View.INVISIBLE);
                return false;
            }
        });
        //forget passwored with firebase
        forgetText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(loginActivity.this, ForgetpasswordActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public void signup(View view) {
        Intent intent = new Intent(loginActivity.this, SignUpActivity.class);
        startActivity(intent);
        finish();
    }

    //store database
    public void login() {
        // Create a new user with a first and last name
        String pass = password.getText().toString();
        String mail = email.getText().toString();
        if (pass.isEmpty() || mail.isEmpty()){
            Toast.makeText(this,"please fill required field",Toast.LENGTH_LONG).show();
            return;
        }
        mauth.signInWithEmailAndPassword(mail, pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(loginActivity.this, "Authentication sucess.",
                                    Toast.LENGTH_SHORT).show();

                            getuser();

                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(loginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mauth.getCurrentUser();
        updateUI(currentUser);
    }

    private void updateUI(FirebaseUser user) {
    }

    private void getuser(){

        Log.i("onresumm","enter");

        FirebaseFirestore database = FirebaseFirestore.getInstance();
        final ArrayList<UserModel> muser = new ArrayList<>();

        database.collection("users")
                .document(getcurrentuserid())
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {

                        user = documentSnapshot.toObject(UserModel.class);

                        SharedPreferences.Editor editor =  login_sharpreferences.edit();
                        editor.putString("username",user.getName());
                        editor.putString("user_photo",user.getImageUrl());
                        editor.putBoolean("islogin",true);
                        editor.apply();

                        if (user != null)
                            Log.i("muser",user.getImageUrl());
                      //  assert user != null;
                     //   Log.i("muser",user.getImageUrl());


                        Intent intent = new Intent(loginActivity.this, InstructionsLocation.class);
                        startActivity(intent);
                        finish();
                    }

                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.i("muser",e.getMessage());
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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                Log.d(TAG, "firebaseAuthWithGoogle:" + account.getId());
                firebaseAuthWithGoogle(account.getIdToken());
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e);
            }
        }
    }
    // [END onactivityresult]
    // [START auth_with_google]
    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mauth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mauth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            updateUI(null);
                        }
                    }
                });
    }
    // [START signin]
    private void GooglesignIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }
    // [END signin]
    public void TwitterSign() {
        Task<AuthResult> pendingResultTask = mauth.getPendingAuthResult();
        if (pendingResultTask != null) {
            // There's something already here! Finish the sign-in for your user.
            pendingResultTask
                    .addOnSuccessListener(
                            new OnSuccessListener<AuthResult>() {
                                @Override
                                public void onSuccess(AuthResult authResult) {
                                    Toast.makeText(loginActivity.this, "sucess", Toast.LENGTH_SHORT).show();
                                }
                            })
                    .addOnFailureListener(
                            new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    // Handle failure.
                                    Toast.makeText(loginActivity.this, "fail", Toast.LENGTH_SHORT).show();
                                }
                            });
        } else {
            // There's no pending result so you need to start the sign-in flow.
            // See below.
        }
    }
}