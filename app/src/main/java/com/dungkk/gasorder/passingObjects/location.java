package com.dungkk.gasorder.passingObjects;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.maps.model.LatLng;

public class location implements Parcelable {

    private Double lat;
    private Double lng;
    private String address;
    private String ward;

    public location(){}

    public location(Double Lat, Double Lng,String Addr, String Ward){
        lat = Lat;
        lng = Lng;
        address = Addr;
        ward = Ward;
    }

    public location(Parcel in)
    {
        lat = in.readDouble();
        lng = in.readDouble();
        address = in.readString();
        ward = in.readString();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getWard() {
        return ward;
    }

    public void setWard(String ward) {
        this.ward = ward;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(lat);
        dest.writeDouble(lng);
        dest.writeString(address);
        dest.writeString(ward);
    }

    public void readFromParcel(Parcel source){
        lat = source.readDouble();
        lng = source.readDouble();
        address = source.readString();
        ward = source.readString();
    }

    public static final Creator<location> CREATOR = new Creator<location>() {
        @Override
        public location createFromParcel(Parcel in) {
            return new location(in);
        }

        @Override
        public location[] newArray(int size) {
            return new location[size];
        }
    };
}
