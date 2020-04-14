package com.example.googlemaps_events;

import android.os.AsyncTask;

public class GetDirectionData extends AsyncTask<Object, String, String> {

    @Override
    protected String doInBackground(Object[] objects) {
        String url = (String) objects[1];
        return new DownloadUrl().readUrl(url);
    }
}
