package com.example.googlemaps_events;

import android.content.Context;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;

import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.gson.Gson;
import com.google.maps.android.PolyUtil;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

public class Markers {

    @RequiresApi(api = Build.VERSION_CODES.N)
    public static void add(MarkerOptions markerOptions) {
        MapsActivity.Gmap.addMarker(markerOptions);
    }

    public static String getAddress(Marker marker, Context context) {
        Geocoder geocoder = new Geocoder(context, Locale.getDefault());
        List<Address> addresses = null; //1 num of possible location returned
        try {
            addresses = geocoder.getFromLocation(marker.getPosition().latitude, marker.getPosition().longitude, 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return addresses.get(0).getAddressLine(0); //0 to obtain first possible address
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public static String[] getDirectionData(LatLng StartP, LatLng EndP, GoogleMap googleMap) {
        String result = "";
        Object[] dataTransfer = new Object[3];
        String url = getDirectionsUrl(StartP, EndP);
        GetDirectionData getDirectionData = new GetDirectionData();
        dataTransfer[0] = googleMap;
        dataTransfer[1] = url;
        dataTransfer[2] = new LatLng(EndP.latitude, EndP.longitude);


        try {
            result = getDirectionData.execute(dataTransfer).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        DataParser parser = new DataParser();
        HashMap<String, String> directionList = parser.parseDurationsDistance(result);
        String[] pathList = parser.parsePath(result);
        return new String[]
                {directionList.get("distance") + "\n" + directionList.get("duration"),
                new Gson().toJson(pathList)};
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public static String getDirectionsUrl(LatLng StartP, LatLng EndP) {
        StringBuilder googleDirectionsUrl = new StringBuilder("https://maps.googleapis.com/maps/api/directions/json?");
        googleDirectionsUrl
                .append("origin=" + StartP.latitude + "," + StartP.longitude)
                .append("&destination=" + EndP.latitude + "," + EndP.longitude)
                .append("&key=" + "AIzaSyC-1JNpeH4u53Kh8YZh95pyPrOza62UIw8");

        return googleDirectionsUrl.toString();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public static void displayPath(String[] pathList) {
        for (String s : pathList) {
            PolylineOptions options = new PolylineOptions();
            options.color(Color.BLUE);
            options.width(10);
            options.addAll(PolyUtil.decode(s));
            MapsActivity.Gmap.addPolyline(options);
        }
    }
}
