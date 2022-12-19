package com.example.reducefoodewaste.Maps;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.reducefoodewastebasic.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.reducefoodewastebasic.databinding.ActivityCurrentLocationMapBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.util.List;
import java.util.Locale;

public class CurrentLocationMap extends FragmentActivity implements OnMapReadyCallback {
    Button set;
    private GoogleMap mMap;
    private ActivityCurrentLocationMapBinding binding;
    TextView getLoc;
    public static final String LOCATION_OBJECT = "1";
    Location currentLocation;
    public static final int REQUEST_CODE = 101;
    FusedLocationProviderClient fusedLocationProviderClient;
    LatLng latLng1;
    double latitude ;
    double longitude ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCurrentLocationMapBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        fetchLocation();
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        getLoc = findViewById(R.id.txt_loc);
        getLoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = getIntent();
                i.putExtra(LOCATION_OBJECT, getLoc.getText().toString());
                setResult(Activity.RESULT_OK, i);
                finish();
            }
        });

        set = findViewById(R.id.activity_map_set_location_button_sethomelocation);
        set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), NearbyPlacesLocationMap.class);
                startActivity(intent);
                finish();
            }
        });

    }

    private void fetchLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE);
            return;
        }
        Task<Location> task = fusedLocationProviderClient.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    currentLocation = location;
                    latitude = location.getLatitude();
                    longitude = location.getLongitude();
                }
                //   Toast.makeText(getApplicationContext(), currentLocation.getLatitude()+""+currentLocation.getLongitude(), Toast.LENGTH_SHORT).show();
                SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                        .findFragmentById(R.id.map);
                mapFragment.getMapAsync(CurrentLocationMap.this);
            }

        });
    }



    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;

        latLng1=new LatLng(latitude,longitude);
        MarkerOptions markerOptions=new MarkerOptions().position(latLng1).title("My location");
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng1));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng1,10));
        mMap.addMarker(markerOptions);
        getLoc.setText(getCompleteAddressString(latitude,longitude));

    }

    private String getCompleteAddressString(double LATITUDE, double LONGITUDE) {
        String strAdd = "";
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(LATITUDE, LONGITUDE, 1);
            if (addresses != null) {
                String city = addresses.get(0).getSubAdminArea();
                String country = addresses.get(0).getCountryName();
                strAdd = city+", "+ country;
            } else {
                Log.w("My Current loction ", "No Address returned!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.w("My Current loction", "Canont get Address!");
        }
        return strAdd;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (REQUEST_CODE){
            case REQUEST_CODE:
                if(grantResults.length>0 && grantResults[0]== PackageManager.PERMISSION_GRANTED){
                    fetchLocation();
                }
                break;
        }

    }
    public void onBackPressed() {
        Intent intent = new Intent(CurrentLocationMap.this,InstructionsLocation.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
   }