package com.dungkk.gasorder.passingObjects;

public class coordinate {
    private Double lat;
    private Double lng;

    public coordinate(){}

    public coordinate(Double Lat, Double Lng)
    {
        lat = Lat;
        lng = Lng;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }
}
