package com.example.googlemaps_events;

import android.annotation.SuppressLint;
import android.icu.text.DecimalFormat;
import android.location.Location;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;


@RequiresApi(api = Build.VERSION_CODES.N)
@SuppressLint("Registered")
public class Markers extends FragmentActivity implements GoogleMap.OnMarkerClickListener {

    public static float[] Distance = new float[10];
    public static Map<String,Float> map = new HashMap();

    @RequiresApi(api = Build.VERSION_CODES.N)
    public static Marker setUpMarker(String eventName) {
        MarkerOptions markerOptions = new MarkerOptions()
                .title(eventName)
                .snippet((CalculationByDistance(MapsActivity.MyCurrentPoint, MapsActivity.MarkerPoint) + "  KM till your location"))
                .position(MapsActivity.MarkerPoint)
                .icon(BitmapDescriptorFactory.defaultMarker(new Random().nextInt(360)));

        map.put(markerOptions.getTitle(),Distance[0]);
        return MapsActivity.Gmap.addMarker(markerOptions);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public static String CalculationByDistance(LatLng StartP, LatLng EndP) {
        Location.distanceBetween(StartP.latitude, StartP.longitude, EndP.latitude, EndP.longitude, Distance);
        return new DecimalFormat("#.##").format(Distance[0] / 1000);
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        return false;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

}
