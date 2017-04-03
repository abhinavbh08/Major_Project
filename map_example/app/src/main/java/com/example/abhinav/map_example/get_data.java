package com.example.abhinav.map_example;

/**
 * Created by abhinav on 16/3/17.
 */
public class get_data {

    private double lat;
    private double lon;

    public get_data(){

    }

    public get_data(double lat, double lon){
        this.lat = lat;
        this.lon = lon;
    }

    public double getLat() {
        return lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

}
