package com.example.reducefoodewaste.Maps;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.reducefoodewaste.Models.MealModel;
import com.example.reducefoodewaste.Ui.InstructionsActivity;
import com.example.reducefoodewastebasic.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.reducefoodewastebasic.databinding.ActivityNearbyPlacesLocationMapBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class NearbyPlacesLocationMap extends FragmentActivity implements OnMapReadyCallback {
    private GoogleMap mMap;
    private ActivityNearbyPlacesLocationMapBinding binding;
    Button button;
    Button Next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        button = findViewById(R.id.mapssetlocnear_button_btn1);
        binding = ActivityNearbyPlacesLocationMapBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
//        setContentView(R.layout.activity_maps_set_location_for_near_by);
        Next = findViewById(R.id.mapssetlocnear_button_btn1);
        FragmentManager fragmentManager=getSupportFragmentManager();
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        fragmentTransaction.addToBackStack(null);
        Next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), InstructionsActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

                        @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(@NonNull LatLng latLng) {
                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.position(latLng);
                markerOptions.title(latLng.latitude + " : " + latLng.longitude);
                mMap.clear();
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10));
                mMap.addMarker(markerOptions);
            }
            public void mapnext(View view) {
                Intent intent = new Intent(NearbyPlacesLocationMap.this, CurrentLocationMap.class);
                startActivity(intent);
                finish();
            }
        });
    }
    public void onBackPressed() {

        Intent intent = new Intent(NearbyPlacesLocationMap.this, CurrentLocationMap.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}
