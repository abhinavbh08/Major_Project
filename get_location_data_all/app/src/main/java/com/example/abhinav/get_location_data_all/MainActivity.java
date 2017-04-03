package com.example.abhinav.get_location_data_all;


import android.content.Intent;
import android.os.Handler;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
/*
    public static  int count = 0;
 //   double lat2,lat1;
  //  double lng2,lng1;
    private static final int INTERVAL = 5*1000;
    final Handler handler = new Handler();
    Runnable runnable;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;
    private ChildEventListener mChildEventListener;
*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("JUMP","OnCreate");

        /*
        Log.d("YO", String.valueOf(count));
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = mFirebaseDatabase.getReference().child("messages");

        get_data getData = new get_data(30.5622221,77.3023859);
        mDatabaseReference.child("abhinav").setValue(getData);

        get_data GetData = new get_data(31.5622221,78.3023859);
        mDatabaseReference.child("lalaland").setValue(GetData);

        get_data getdata = new get_data(32.5622221,79.3023859);
        mDatabaseReference.child("hola_hola").setValue(getdata);



        mDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Log.d("ALLO", "Inside onDataChanged");

                Iterable<DataSnapshot> ds = dataSnapshot.getChildren();
                for (DataSnapshot mydata : ds){
                    get_data get_data = mydata.getValue(get_data.class);
                    Log.d("ALLO",get_data.getLat()+" "+get_data.getLon());
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("ALLO", "Reading the database failed");
            }
        });
*/

/*
        lat2 = 30.5621007;
        lng2 = 77.3022859;

        lat1 = 22.5621023;
        lng1 = 77.3022812;

        if (distance(lat1, lng1, lat2, lng2) < 0.1) { // if distance < 0.1 miles we take locations as equa
            Log.d("ALLO", String.valueOf(distance(lat1, lng1, lat2, lng2)));
        }
        else
            Log.d("ALLO", String.valueOf(distance(lat1, lng1, lat2, lng2)));

*/
        Intent intent = new Intent(this, service_test.class);
        startService(intent);
    }

    @Override
    protected void onStart() {
        Log.d("JUMP","OnsTART");
/*
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                count++;
                Log.d("Yo", String.valueOf(count));
                mDatabaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        Log.d("ALLO", "Inside onDataChanged");

                        Iterable<DataSnapshot> ds = dataSnapshot.getChildren();
                        for (DataSnapshot mydata : ds){
                            get_data get_data = mydata.getValue(get_data.class);
                            Log.d("ALLO",get_data.getLat()+" "+get_data.getLon());
                        }

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.d("ALLO", "Reading the database failed");
                    }
                });

                runnable = this;
                handler.postDelayed(runnable, INTERVAL);
            }
        },INTERVAL);
   */
        super.onStart();
    }

    @Override
    protected void onPause() {
  //      handler.removeCallbacks(runnable);
        Log.d("JUMP","OnCreate");
        super.onPause();
    }
/*
    private double distance(double lat1, double lng1, double lat2, double lng2) {

        double earthRadius = 3958.75; // in miles, change to 6371 for kilometer output

        double dLat = Math.toRadians(lat2-lat1);
        double dLng = Math.toRadians(lng2-lng1);

        double sindLat = Math.sin(dLat / 2);
        double sindLng = Math.sin(dLng / 2);

        double a = Math.pow(sindLat, 2) + Math.pow(sindLng, 2)
                * Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2));

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));

        double dist = earthRadius * c;

        return dist;
    }
*/

    @Override
    protected void onDestroy() {
        Log.d("JUMP","OnDestroy");
        super.onDestroy();
        stopService(new Intent(this, service_test.class));
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("JUMP","OnResume");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("JUMP","OnRestart");
    }

    @Override
    protected void onStop() {
        super.onStop();      Intent intent = new Intent(this, service_test.class);
        startService(intent);

        Log.d("JUMP","OnStop");
    }
}
