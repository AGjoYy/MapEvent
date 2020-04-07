package com.example.googlemaps_events;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    public static String eventName;
    private Button addButton;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addButton = findViewById(R.id.add);
        addButton();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void addButton() {
        addButton.setOnClickListener(click -> {
            EditText event = findViewById(R.id.event);
            eventName = event.getText().toString();
            Markers.setUpMarker(eventName);
            finish();
        });
    }
}
