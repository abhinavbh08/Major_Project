package com.example.abhinav.notification_system;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class Main2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main2);
        Log.d("LOL", "Inside main Activity 2");
        onNewIntent(getIntent());

    }


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Bundle extras = getIntent().getExtras();
            Log.d("LOL","Inside Extras");

                setContentView(R.layout.activity_main2);
                String lat = extras.getString("lat");
                String lon = extras.getString("lon");

            Log.d("LOL",lat);
            Log.d("LOL",lon);
        }

    }

