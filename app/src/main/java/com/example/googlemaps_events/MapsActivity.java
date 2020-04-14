package com.example.googlemaps_events;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.tasks.Task;
import com.google.gson.Gson;

@RequiresApi(api = Build.VERSION_CODES.N)
public class MapsActivity extends FragmentActivity implements
        OnMapReadyCallback,
        GoogleMap.OnMarkerClickListener {

    Gson gson = new Gson();
    public static Location CurrentLocation;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private static final int SUCCESSFUL_REQUEST_CODE = 101;
    public static GoogleMap Gmap;
    public static LatLng MyCurrentPoint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        fetchLastLocation();
    }

    public void fetchLastLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]
                    {Manifest.permission.ACCESS_FINE_LOCATION}, SUCCESSFUL_REQUEST_CODE);
            return;
        }
        Task<Location> task = fusedLocationProviderClient.getLastLocation();
        task.addOnSuccessListener(location -> {
            if (location != null) {
                CurrentLocation = location;
                SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
                mapFragment.getMapAsync(this);
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onMapReady(GoogleMap googleMap) {
        Gmap = googleMap;
        Gmap.setOnMapClickListener(markerPoint -> {
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("Marker_Position", gson.toJson(markerPoint));
            startActivityForResult(intent, 1);
            Gmap.clear();
        });
        Gmap.setOnMarkerClickListener(this);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

            Gmap.setMyLocationEnabled(true);
            Gmap.getUiSettings().setMyLocationButtonEnabled(true);
            Gmap.getUiSettings().setCompassEnabled(true);
            Gmap.setIndoorEnabled(true);
            Gmap.setBuildingsEnabled(true);
            Gmap.getUiSettings().setZoomControlsEnabled(true);

            MyCurrentPoint = new LatLng(CurrentLocation.getLatitude(), CurrentLocation.getLongitude());
            Gmap.animateCamera(CameraUpdateFactory.newLatLngZoom(MyCurrentPoint, 5));
        }
    }

    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == SUCCESSFUL_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                fetchLastLocation();
            } else {
                Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        String[] directionData = Markers.getDirectionData(MyCurrentPoint, marker.getPosition(), Gmap);

        Intent intent = new Intent(this, PopUp_Window.class);
        intent.putExtra("title", marker.getTitle());
        intent.putExtra("city information", Markers.getAddress(marker, this));
        intent.putExtra("direction distance and duration", directionData[0]);
        intent.putExtra("direction path", directionData[1]);
        startActivity(intent);
        return false;
    }
}


