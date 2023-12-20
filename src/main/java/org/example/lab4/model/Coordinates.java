package org.example.lab4.model;

public class Coordinates {
    private double lat;
    private double lon;

    public Coordinates(double lat, double lon) {
        this.lat = lat;
        this.lon = lon;
    }

    public Coordinates() {
    }

    public double getLat() {
        return lat;
    }

    public double getLon() {
        return lon;
    }
}
