package com.example.abhinav.map_example;

import android.*;
import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, LocationListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {


    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;

    private String android_id;
    private GoogleMap mMap;
    LocationRequest mLocationRequest;
    GoogleApiClient mGoogleApiClient;
    Location mLastLocation;
    final Handler handler = new Handler();
    private static final int INTERVAL = 5 * 1000;
    MarkerData md;
    LatLng latLng;
    Runnable runnable;
//    Marker mCurrLocationMarker;

    ArrayList<MarkerData> Cars = new ArrayList<MarkerData>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        android_id = Settings.Secure.getString(getApplicationContext().getContentResolver(),
                Settings.Secure.ANDROID_ID);
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = mFirebaseDatabase.getReference().child("messages");
        Log.d("YOYO", "OnCreate Called");
/*
        get_data getData = new get_data(30.5622221,77.3023859);
        mDatabaseReference.child("abhinav").setValue(getData);

        get_data GetData = new get_data(31.5622221,78.3023859);
        mDatabaseReference.child("lalaland").setValue(GetData);

        get_data getdata = new get_data(32.5622221,79.3023859);
        mDatabaseReference.child("hola_hola").setValue(getdata);

        /*
        MarkerData md1 = new MarkerData(30.123456, 77.123456);
        MarkerData md2 = new MarkerData(32.124567, 74.123456);
        MarkerData md3 = new MarkerData(26.123456, 71.567842);

        MarkerData md4 = new MarkerData(30.213456, 77.353456);
        MarkerData md5 = new MarkerData(32.564567, 74.893456);
        MarkerData md6 = new MarkerData(26.893456, 71.117842);

        Cars.add(md1);
        Cars.add(md2);
        Cars.add(md3);

        Cars.add(md4);
        Cars.add(md5);
        Cars.add(md6);
*/
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }

    @Override
    protected void onStart() {
        Log.d("YOYO", "Onstart Called");
        int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        if (ConnectionResult.SUCCESS == status) {
            Log.d("TAG", "GPS available");
        } else {
            Log.d("TAG", "GPS not available");
        }
        Log.d("TAG", "Inside onStart method");
        if (mGoogleApiClient != null) {
            mGoogleApiClient.connect();
            Log.d("TAG", "After Connected");
        } else
            Log.d("TAG", "Not Connected");

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

        }
//        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
//                mGoogleApiClient);
 //       MarkerData md = new MarkerData(mLastLocation.getLatitude(), mLastLocation.getLongitude());
//        Cars.add(md);

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mDatabaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Cars.clear();

                        Log.d("ALLO", "Inside onDataChanged");

                        Iterable<DataSnapshot> ds = dataSnapshot.getChildren();
                        for (DataSnapshot mydata : ds) {

                            get_data get_data = mydata.getValue(get_data.class);
                            Log.d("ALLO", get_data.getLat() + " " + get_data.getLon());
                            md = new MarkerData(get_data.getLat(), get_data.getLon());
                            //      md.setLat(get_data.getLat());
                            //     md.setLon(get_data.getLon());
                            Cars.add(md);
                        }

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.d("ALLO", "Reading the database failed");
                    }
                });

                change_pos();
                runnable = this;
                handler.postDelayed(runnable, INTERVAL);
            }
        },INTERVAL);

        super.onStart();


    }

    @Override
    protected void onStop() {
        super.onStop();
        Intent intent = new Intent(this, service_test.class);
        getApplication().startService(intent);
        if(mGoogleApiClient!=null)
            mGoogleApiClient.disconnect();
        Log.d("YOYO", "OnStop Called");
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mGoogleApiClient != null) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
        }
        Log.d("YOYO", "OnPause Called");
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        getApplication().stopService(new Intent(this, service_test.class));
        mDatabaseReference.child(android_id).removeValue();
    }

    /*
                @Override
                protected void onResume() {
                    super.onResume();

                    if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
                    }
                }
            */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        Log.d("TAG","Callback called");
        mMap = googleMap;

      //  mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
        }
/*
        // Add a marker in Sydney and move the camera
        //    LatLng sydney = new LatLng(-34, 151);
        //   mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        //    mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    */

   //     addMarkerstoMap();
    }

    private void addMarkerstoMap(){
        mMap.clear();
        LatLng sydney = null;
        for(int i=0;i<Cars.size();i++){
            sydney = new LatLng(Cars.get(i).getLat(), Cars.get(i).getLon());
            mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
            //  LatLng ll = new LatLng(Cars.get(i).getLat(), Cars.get(i).getLon());
           // mMap.addMarker(new MarkerOptions().position(ll));
        }
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }

    private void change_pos(){
        mMap.clear();
        LatLng sydney = null;
        for(int i=0;i<Cars.size();i++){
            sydney = new LatLng(Cars.get(i).getLat(), Cars.get(i).getLon());
            mMap.addMarker(new MarkerOptions().position(sydney).title("Marker"));
            //  LatLng ll = new LatLng(Cars.get(i).getLat(), Cars.get(i).getLon());
            // mMap.addMarker(new MarkerOptions().position(ll));
        }
      //  mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

    }



    @Override
    public void onConnected(@Nullable Bundle bundle) {

        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                mGoogleApiClient);
        if (mLastLocation != null) {
            //place marker at current position
            mMap.clear();
            latLng = new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude());
            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position(latLng);
            markerOptions.title("Current Position");
            mMap.addMarker(markerOptions);
            mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        }
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(1000);
        mLocationRequest.setFastestInterval(1000);
        mLocationRequest.setSmallestDisplacement(0.1f);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED)
        {LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);}
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onLocationChanged(Location location) {

        get_data getData = new get_data(location.getLatitude(),location.getLongitude());
        mDatabaseReference.child(android_id).setValue(getData);

       /* Toast.makeText(this, "Location Changed " + location.getLatitude()
                + location.getLongitude(), Toast.LENGTH_LONG).show();

        mLastLocation = location;
        if (mCurrLocationMarker != null) {
            mCurrLocationMarker.remove();
        }

        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title("Current Position");
        mCurrLocationMarker = mMap.addMarker(markerOptions);

        //move map camera
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
      //  mMap.animateCamera(CameraUpdateFactory.zoomTo(15));

//        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 12.0f));
        */

    }
    
    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}



