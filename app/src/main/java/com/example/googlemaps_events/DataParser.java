package com.example.googlemaps_events;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;;

public class DataParser {

    public HashMap<String, String> parseDurationsDistance(String jsonData) {
        JSONArray jsonArray = null;

        try {
            jsonArray = new JSONObject(jsonData)
                    .getJSONArray("routes")
                    .getJSONObject(0)
                    .getJSONArray("legs");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return getDurationDistance(jsonArray);
    }

    private HashMap<String, String> getDurationDistance(JSONArray googleDirectionsJson) {
        HashMap<String, String> googleDirectionsMap = new HashMap<>();

        try {
            String duration = googleDirectionsJson.getJSONObject(0).getJSONObject("duration").getString("text");
            String distance = googleDirectionsJson.getJSONObject(0).getJSONObject("distance").getString("text");

            googleDirectionsMap.put("duration", duration);
            googleDirectionsMap.put("distance", distance);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return googleDirectionsMap;
    }

    public String[] parsePath(String jsonData) {
        JSONArray jsonArray = null;

        try {
            jsonArray = new JSONObject(jsonData)
                    .getJSONArray("routes")
                    .getJSONObject(0)
                    .getJSONArray("legs")
                    .getJSONObject(0)
                    .getJSONArray("steps");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return getPaths(jsonArray);
    }

    public String[] getPaths(JSONArray googleStepsJson) {
        int count = googleStepsJson.length();
        String[] polylines = new String[count];

        for (int i = 0; i < count; i++) {
            try {
                polylines[i] = getPath(googleStepsJson.getJSONObject(i));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return polylines;
    }

    public String getPath(JSONObject googlePathJson) {
        String polyline = null;
        try {
            polyline = googlePathJson.getJSONObject("polyline").getString("points");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return polyline;
    }
}
