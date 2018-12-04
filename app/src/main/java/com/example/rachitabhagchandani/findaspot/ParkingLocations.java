package com.example.rachitabhagchandani.findaspot;

import android.content.Intent;
import android.os.Parcelable;
import android.util.Log;

import java.io.Serializable;

public class ParkingLocations implements Serializable
{

        String address;
        String capacity_car;
        String capacity_two_wheeler;
        String charges_car, charges_two_wheeler;
        String city;
        String lat_value;
        String long_value;
        String available_car;
        String available_two_wheeler;

    public String getLocationid() {
        return locationid;
    }

    public void setLocationid(String locationid) {
        this.locationid = locationid;
    }

    String locationid;

    public String getAvailable_car() {
        return available_car;
    }

    public void setAvailable_car(String available_car) {
        this.available_car = available_car;
    }

    public String getAvailable_two_wheeler() {
        return available_two_wheeler;
    }

    public void setAvailable_two_wheeler(String available_two_wheeler) {
        this.available_two_wheeler = available_two_wheeler;
    }

    public ParkingLocations()
        { }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCapacity_car() {
        return capacity_car;
    }

    public void setCapacity_car(String capacity_car) {
        this.capacity_car = capacity_car;
    }

    public String getCapacity_two_wheeler() {
        return capacity_two_wheeler;
    }

    public void setCapacity_two_wheeler(String capacity_two_wheeler) {
        this.capacity_two_wheeler = capacity_two_wheeler;
    }

    public String getCharges_car() {
        return charges_car;
    }

    public void setCharges_car(String charges_car) {
        this.charges_car = charges_car;
    }

    public String getCharges_two_wheeler() {
        return charges_two_wheeler;
    }

    public void setCharges_two_wheeler(String charges_two_wheeler) {
        this.charges_two_wheeler = charges_two_wheeler;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getLat_value() {
        return lat_value;
    }

    public void setLat_value(String lat_value) {
        this.lat_value = lat_value;
    }

    public String getLong_value() {
        return long_value;
    }

    public void setLong_value(String long_value) {
        this.long_value = long_value;
    }

    public ParkingLocations(String address, String capacity_car, String capacity_two_wheeler, String charges_car, String charges_two_wheeler, String city, String lat_value, String long_value){

        this.address = address;
        this.capacity_car= capacity_car;
        this.capacity_two_wheeler=capacity_two_wheeler;
        this.charges_car=charges_car;
        this.charges_two_wheeler=charges_two_wheeler;
        this.city=city;
        this.lat_value=lat_value;
        this.long_value=long_value;
        Log.d("saumya","value of address set to "+this.address);
    }

}

