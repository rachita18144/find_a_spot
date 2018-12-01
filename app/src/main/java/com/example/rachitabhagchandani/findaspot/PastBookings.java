package com.example.rachitabhagchandani.findaspot;

import java.io.Serializable;
import java.sql.Time;
import java.util.Date;

public class PastBookings implements Serializable {
        Date booking_date;
        Time arrival_time;
        Time leaving_time;
        String vehicle_number;
        String location;
        String user_id;
        float amount_paid;

    PastBookings(){ }

        public String getLocation() {

            return location;
    }

       public float getAmount_paid() {
        return amount_paid;
    }

    public void setAmount_paid(float amount_paid) {
        this.amount_paid = amount_paid;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Date getBooking_date() {
        return booking_date;
    }

    public void setBooking_date(Date booking_date) {
        this.booking_date = booking_date;
    }

    public Time getArrival_time() {
        return arrival_time;
    }

    public void setArrival_time(Time arrival_time) {
        this.arrival_time = arrival_time;
    }

    public Time getLeaving_time() {
        return leaving_time;
    }

    public void setLeaving_time(Time leaving_time) {
        this.leaving_time = leaving_time;
    }

    public String getVehicle_number() {
        return vehicle_number;
    }

    public void setVehicle_number(String vehicle_number) {
        this.vehicle_number = vehicle_number;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public PastBookings(Date booking_date, Time arrival_time, Time leaving_time, String vehicle_number, String user_id, String location, float amount_paid) {

        this.amount_paid = amount_paid;
        this.location = location;
        this.booking_date = booking_date;
        this.arrival_time = arrival_time;
        this.leaving_time = leaving_time;
        this.vehicle_number = vehicle_number;
        this.user_id = user_id;
    }
}
