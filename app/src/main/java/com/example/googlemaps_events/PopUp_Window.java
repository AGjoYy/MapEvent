package com.example.googlemaps_events;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import com.google.gson.Gson;
import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

public class PopUp_Window extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.N)
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_pop_up_window);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.gravity = Gravity.BOTTOM | Gravity.CENTER;

        String setTitle = getIntent().getStringExtra("title");
        if ((!setTitle.trim().equals("")))
            setTitle(setTitle);

        TextView showCityInfo = findViewById(R.id.cityInformation);
        showCityInfo.setText(getIntent().getStringExtra("city information"));

        TextView distance_and_duration = findViewById(R.id.distance_and_duration);
        distance_and_duration.setText(getIntent().getStringExtra("direction distance and duration"));

        Button showDirections = findViewById(R.id.show_directions);
        showDirections.setOnClickListener(click->{
            Gson gson = new Gson();
            Markers.displayPath(gson.fromJson(getIntent().getStringExtra("direction path"),String[].class));
        });
    }
}
