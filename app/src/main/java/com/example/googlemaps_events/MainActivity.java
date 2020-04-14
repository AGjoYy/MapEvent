package com.example.googlemaps_events;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements Serializable {

    public Gson gson = new Gson();
    public MarkerOptions markerOptions;
    public LatLng position;
    public EditText titleInput;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.gravity = Gravity.TOP | Gravity.CENTER;

        titleInput = findViewById(R.id.titleInput);
        titleInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                setTitle(titleInput.getText());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        position = gson.fromJson(getIntent().getStringExtra("Marker_Position"), (Type) LatLng.class);
        Button addButton = findViewById(R.id.add);
        addButton.setOnClickListener(click -> addMarker());
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void addMarker() {
        titleInput = findViewById(R.id.titleInput);
        markerOptions = new MarkerOptions()
                .title(titleInput.getText().toString())
                .position(position)
                .icon(BitmapDescriptorFactory.defaultMarker(new Random().nextInt(360)));
        Markers.add(markerOptions);
        finish();
    }
}
