package com.example.abhinav.test_life_cycle;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d("YOYO","OnCreate Called");
    }

    @Override
    protected void onStart() {
        Log.d("YOYO","OnStart Called");
        super.onStart();
    }

    @Override
    protected void onStop() {
        Log.d("YOYO","OnStop Called");
        super.onStop();
    }

    @Override
    protected void onPause() {
        Log.d("YOYO","OnPause Called");
        super.onPause();
    }

    @Override
    protected void onResume() {
        Log.d("YOYO","OnResume Called");
        super.onResume();
    }

    @Override
    protected void onRestart() {
        Log.d("YOYO","OnRestart Called");
        super.onRestart();
    }

    @Override
    protected void onDestroy() {
        Log.d("YOYO","OnCreate Called");
        super.onDestroy();
    }
}
