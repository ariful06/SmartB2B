package com.example.virus.smartb2b.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.firestore.GeoPoint;

import java.io.Serializable;

public class Customer implements Serializable,Parcelable{

    private String shopName;
    private String shopOwnerName;
    private String phoneNumber;
    private double latitude,longitude;
    private GeoPoint geoPoint;

    public Customer(){

    }



    protected Customer(Parcel in) {
    shopName = in.readString();
    latitude = in.readDouble();
    longitude = in.readDouble();
    geoPoint = new GeoPoint(latitude,longitude);
    }



    public static final Creator<Customer> CREATOR = new Creator<Customer>() {
        @Override
        public Customer createFromParcel(Parcel in) {
            return new Customer(in);
        }

        @Override
        public Customer[] newArray(int size) {
            return new Customer[size];
        }
    };

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getShopOwnerName() {
        return shopOwnerName;
    }

    public void setShopOwnerName(String shopOwnerName) {
        this.shopOwnerName = shopOwnerName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public GeoPoint getGeoPoint() {
        return geoPoint;
    }

    public void setGeoPoint(GeoPoint geoPoint) {
        this.geoPoint = geoPoint;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        latitude = getGeoPoint().getLatitude();
        longitude = getGeoPoint().getLongitude();
        dest.writeString(shopName);
        dest.writeString(shopOwnerName);
        dest.writeString(phoneNumber);
        dest.writeDouble(latitude);
        dest.writeDouble(longitude);
    }
}
