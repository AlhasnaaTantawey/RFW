package com.example.reducefoodewaste.Ui;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.example.reducefoodewaste.Adapters.RecyclerViewAdapterMainPageActivity;
import com.example.reducefoodewaste.Models.MealModel;
import com.example.reducefoodewaste.Models.UserModel;
import com.example.reducefoodewastebasic.R;
import com.example.reducefoodewaste.loginActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class MainPageActivity extends AppCompatActivity {
    public static final String languageFile = "language_file";
    SharedPreferences myLang;
    String defaultLanguage = "en";
    String language1;
    TextView userna;
    ImageView userimg;
    FirebaseAuth mauth;
    DatabaseReference mref;
    FirebaseUser muser;
    private AppBarConfiguration mAppBarConfiguration;
    DrawerLayout drawer;
    NavigationView navigationView;
    String lang;
    SharedPreferences sharpreferences;
    SharedPreferences login_sharpreferences;
    int chekedItem;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharpreferences = getSharedPreferences("pref", Context.MODE_PRIVATE);
        login_sharpreferences = getSharedPreferences("login", Context.MODE_PRIVATE);
        lang = sharpreferences.getString("lang","");

        if (!lang.equals("")) {
            Locale locale = new Locale(lang);
            Locale.setDefault(locale);
            Configuration configuration = getResources().getConfiguration();
            configuration.locale = locale;
            configuration.setLayoutDirection(locale); // for RTL changes
            getResources().updateConfiguration(configuration, getResources().getDisplayMetrics());
        }
        setContentView(R.layout.activity_drawer_main_page);
        myLang = getSharedPreferences(languageFile, Context.MODE_PRIVATE);
        language1 = myLang.getString("language", defaultLanguage).toString();
        //setLocale(language1);
        mauth = FirebaseAuth.getInstance();
        muser = mauth.getCurrentUser();
        mref = FirebaseDatabase.getInstance().getReference().child("users");
        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        userimg = headerView.findViewById(R.id.userpicimg);
        userna  = headerView.findViewById(R.id.userna);
        mauth = FirebaseAuth.getInstance();
        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                // item.setChecked(true);
                switch (id) {
                    case R.id.nav_language:
                        setlang();
                        Log.i("logggt", "clicked");
                        return true;
                    case R.id.nav_logout:
                        logout();
                        Log.i("loggprof", "clicked");
                        return true;
                }
                NavigationUI.onNavDestinationSelected(item, navController);
                drawer.closeDrawers();
                return true;
            }
        });
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home2, R.id.navigation_about2, R.id.navigation_bottom_profile)
                .setOpenableLayout(drawer)
                .build();
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void setlang() {
        final String[] language = {"عربي", "English"};
        if (lang.equals("ar")) {
            chekedItem = 0;
        } else {
            chekedItem = 1;
        }
        final AlertDialog.Builder mBuilder = new AlertDialog.Builder(this);
        mBuilder.setTitle("Choose Language");
        mBuilder.setSingleChoiceItems(language, chekedItem, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (language[i].equals("عربي")) {
                    lang("ar");
                    SharedPreferences.Editor shared = sharpreferences.edit();
                    shared.putString("lang","ar");
                    shared.apply();
                    chekedItem = 0;
                }
                if (language[i].equals("English")) {
                    lang("En");
                    SharedPreferences.Editor shared = sharpreferences.edit();
                    shared.putString("lang","en");
                    shared.apply();
                    chekedItem = 1;
                }
            }
        });
        // Set the neutral/ok button click listener
        mBuilder.setNeutralButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        mBuilder.create().show();
    }
    private void logout() {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(this);
        mBuilder.setMessage(R.string.are_you_sure);

        mBuilder.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });

        mBuilder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();

                SharedPreferences.Editor editor = login_sharpreferences.edit();
                editor.clear();
                editor.apply();
                Intent intent = new Intent(MainPageActivity.this, SplashActivity.class);
                intent.putExtra("finish", true);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                FirebaseAuth.getInstance().signOut();
                finish();
            }
        });
        AlertDialog mDialog = mBuilder.create();
        mDialog.show();
    }


    //for navigation view
    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }


    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResumeFragments() {
        super.onResumeFragments();
        if (muser == null) {
            senduserTologinactivity();
        } else {
            FirebaseFirestore.getInstance().collection("users")
                    .document(getcurrentuserid()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            UserModel user=  documentSnapshot.toObject(UserModel.class);
                            if (!Objects.equals(user.getImageUrl(), "")) {
                                Picasso.get().load(user.getImageUrl()).into(userimg);
                                Glide.with(MainPageActivity.this)
                                        .load(user.getImageUrl())
                                        .placeholder(R.drawable.progress_animation)
                                        .into(userimg);
                            }
                            userna.setText(user.getName());
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(MainPageActivity.this, "Failed "+e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }

    public String getcurrentuserid() {
        FirebaseUser currentuser = FirebaseAuth.getInstance().getCurrentUser();
        String currentuserid = "";
        if (currentuser != null) {
            currentuserid = currentuser.getUid();
        }
        return currentuserid;
    }
    private void senduserTologinactivity() {
        Intent intent = new Intent(MainPageActivity.this, loginActivity.class);
        startActivity(intent);
        finish();
    }
    public void lang (String langCode){
        Locale locale = new Locale(langCode);
        Locale.setDefault(locale);
        Configuration configuration = getResources().getConfiguration();
        configuration.locale = locale;
        configuration.setLayoutDirection(locale); // for RTL changes
        getResources().updateConfiguration(configuration, getResources().getDisplayMetrics());
        finish();
        startActivity(getIntent());
    }
}