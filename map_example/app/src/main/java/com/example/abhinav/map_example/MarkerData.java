package com.example.abhinav.map_example;

/**
 * Created by abhinav on 16/3/17.
 */
public class MarkerData {

    private double lat;
    private double lon;

    public MarkerData(double lat, double lon){
        this.lat = lat;
        this.lon = lon;
    }

    public double getLon() {
        return lon;
    }

    public double getLat() {
        return lat;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }
}


