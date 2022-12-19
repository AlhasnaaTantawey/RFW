package com.example.reducefoodewaste;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.reducefoodewaste.Models.UserModel;
import com.example.reducefoodewaste.Maps.InstructionsLocation;
import com.example.reducefoodewastebasic.R;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
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
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.auth.OAuthProvider;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Pattern;


public class
SignUpActivity extends AppCompatActivity {
    private static final String EMAIL = "email";
    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile(
                    "(?=.*[a-z])"//lower case
                            +  "(?=.*[A-Z])"+//upper case
                            "(?=.*[0-9])"+//numeric
                            "(?=.*[@#$%^&+=])" +    //at least 1 special character
                            "(?=\\S+$)" +           //no white spaces
                            ".{8,}" +               //at least 8 characters
                            "$"
            );

    Button button;
    EditText userName, password , confirmPass ,email;
    ImageView tw, go, face;
    FirebaseAuth mauth = FirebaseAuth.getInstance();
    FirebaseFirestore db= FirebaseFirestore.getInstance();
    private static final String TAG = "signupActivity";
    private static final int RC_SIGN_IN = 9001;
    private GoogleSignInClient mGoogleSignInClient;
    CallbackManager callbackManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        button = findViewById(R.id.signup_button);
        userName = findViewById(R.id.editTextTextPersonName);
        password = findViewById(R.id.editTextTextPassword);
        confirmPass = findViewById(R.id.editTextTextPassword2);
        email = findViewById(R.id.editTextTextEmailAddress);
        tw = findViewById(R.id.imageView3);
        go = findViewById(R.id.gimageView);
        face = findViewById(R.id.imageView4);

        tw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OAuthProvider.Builder provider = OAuthProvider.newBuilder("twitter.com");
                Task<AuthResult> pendingResultTask = mauth.getPendingAuthResult();
                if (pendingResultTask != null) {
                    // There's something already here! Finish the sign-in for your user.
                    pendingResultTask
                            .addOnSuccessListener(
                                    new OnSuccessListener<AuthResult>() {
                                        @Override
                                        public void onSuccess(AuthResult authResult) {
                                            startActivity(new Intent(SignUpActivity.this, InstructionsLocation.class));
                                            Toast.makeText(SignUpActivity.this, "login successfully", Toast.LENGTH_SHORT).show();
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
                            .startActivityForSignInWithProvider(/* activity= */ SignUpActivity.this, provider.build())
                            .addOnSuccessListener(
                                    new OnSuccessListener<AuthResult>() {
                                        @Override
                                        public void onSuccess(AuthResult authResult) {
                                            startActivity(new Intent(SignUpActivity.this, InstructionsLocation.class));

                                        }
                                    })
                            .addOnFailureListener(
                                    new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            // Handle failure.
                                            Log.d(TAG, "onFailure: " + e.getLocalizedMessage());
                                        }
                                    });
                }
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkCredentials()) {
                    Log.i("logedm11","loged");

                    adddata();
                }
            }

        });

        go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GooglesignIn();
            }
        });
        face.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callbackManager= CallbackManager.Factory.create();
                LoginManager.getInstance().logInWithReadPermissions(SignUpActivity.this, Arrays.asList("public_profile"));
                LoginManager.getInstance().registerCallback(callbackManager,
                        new FacebookCallback<LoginResult>() {
                            @Override
                            public void onSuccess(LoginResult loginResult) {
                                handleFacebookAccessToken(loginResult.getAccessToken());
                            }

                            @Override
                            public void onCancel() {
                                // App code
                            }

                            @Override
                            public void onError(FacebookException exception) {
                                // App code
                            }
                        });
            }
        });
    }

    /* @Override
     public void onActivityResult(int requestCode, int resultCode, Intent data) {
         super.onActivityResult(requestCode, resultCode, data);

         // Pass the activity result back to the Facebook SDK
         callbackManager.onActivityResult(requestCode, resultCode, data);
     }**/
    private void handleFacebookAccessToken(AccessToken token) {
        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mauth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            String uid= task.getResult().getUser().getUid();
                            String name= task.getResult().getUser().getDisplayName();
                            String img_url=task.getResult().getUser().getPhotoUrl().toString();
                            UserModel obj=new UserModel(uid,name,"","","","",0,"https://graph.facebook.com/" + uid + "/picture?type=large"
                            );
                            db.collection("users").document(uid).set(obj).addOnCompleteListener(task1 -> {
                                Log.d(TAG, "onComplete: "+task.isSuccessful());
                                updateUI(UserModel.fromfirebaseuser(mauth.getCurrentUser()));
                            });


                            //     updateUI(UserModel.fromfirebaseuser(mauth.getCurrentUser()));

                        } else {
                            Toast.makeText(SignUpActivity.this, ""+task.getException(),
                                    Toast.LENGTH_SHORT).show();

                        }
                    }
                });
    }

    public void adddata() {
        String pass = password.getText().toString();
        String repass = confirmPass.getText().toString();
        String username = userName.getText().toString();
        String mail = email.getText().toString();
        mauth.createUserWithEmailAndPassword(mail, pass)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {

                        UserModel obj = new UserModel(authResult.getUser().getUid(), username, pass, repass, String.valueOf(System.currentTimeMillis()), mail, 0,"");

                        Log.i("logedm", "loged");
                        String uid = authResult.getUser().getUid();
                        String name = authResult.getUser().getDisplayName();
                        String memail = authResult.getUser().getEmail();
                        //  UserModel obj=new UserModel(uid,name,"","","",memail,new ArrayList<>());
                        db.collection("users").document(uid).set(obj).addOnCompleteListener(task1 -> {
                            Log.d(TAG, "onComplete: " + task1.isSuccessful());
                            updateUI(obj);
                        });

                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.i( "errorrr: " , e.getMessage());
            }
        });

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        if(mauth.getCurrentUser()!=null) {
            UserModel object = UserModel.fromfirebaseuser(mauth.getCurrentUser());
            updateUI(object);
        }
    }
    private void updateUI(UserModel obj) {
        Intent intent=new Intent(SignUpActivity.this, InstructionsLocation.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Pass the activity result back to the Facebook SDK
        // callbackManager.onActivityResult(requestCode, resultCode, data);
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
                            Log.d(TAG, "onComplete: "+task.getResult().getUser().toString());
                            String uid= task.getResult().getUser().getUid();
                            String name= task.getResult().getUser().getDisplayName();
                            String memail= task.getResult().getUser().getEmail();
                            String img_url=task.getResult().getUser().getPhotoUrl().toString();
                            UserModel obj=new UserModel(uid,name,"","","",memail,0,img_url);
                            db.collection("users").document(uid).set(obj).addOnCompleteListener(task1 -> {
                                Log.d(TAG, "onComplete: "+task.isSuccessful());
                                updateUI(obj);
                            });
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

    private boolean checkCredentials(){
        String pass = password.getText().toString();
        String repass = confirmPass.getText().toString();
        String username = userName.getText().toString();
        String mail = email.getText().toString();
        if(username.isEmpty() ){
            showError(userName,"name not valid");
            return false;
        }else if(mail.isEmpty() || !mail.contains("@"))
        {
            showError(email,"email not valid");
            return false;
        }else if(pass.isEmpty() || !pass.matches(PASSWORD_PATTERN.pattern())){
            showError(password,"password should contain upper ,lower ," +
                    "spcial charachter,number and 8 charachter length");
            return false;
        }else if(repass.isEmpty() || !repass.equals(pass)){
            showError(confirmPass,"password not match");
            return false;
        }
        return true;
    }

    private  void showError(EditText input,String s)
    { input.setError(s);
        input.requestFocus();
    }

}