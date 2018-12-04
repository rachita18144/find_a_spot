package com.example.rachitabhagchandani.findaspot;

public class BookSlotFirebase {
    String booking_date;
    String arrival_time;
    String leaving_time;
    String vehicle_number;
    String location;
    String locationid;
    String user_id;
    String status;
    String vehicle_type;
    float amount_paid;

    BookSlotFirebase() {
    }

    public String getStatus() {
        return status;
    }

    public String getVehicle_type() {
        return vehicle_type;
    }

    public void setVehicle_type(String vehicle_type) {
        this.vehicle_type = vehicle_type;
    }
    public void setStatus(String status) {
        this.status = status;
    }

    public String getLocationid() {
        return locationid;
    }

    public void setLocationid(String locationid) {
        this.locationid = locationid;
    }

    public String getBooking_date() {
        return booking_date;
    }

    public void setBooking_date(String booking_date) {
        this.booking_date = booking_date;
    }

    public String getArrival_time() {
        return arrival_time;
    }

    public void setArrival_time(String arrival_time) {
        this.arrival_time = arrival_time;
    }

    public String getLeaving_time() {
        return leaving_time;
    }

    public void setLeaving_time(String leaving_time) {
        this.leaving_time = leaving_time;
    }

    public String getVehicle_number() {
        return vehicle_number;
    }

    public void setVehicle_number(String vehicle_number) {
        this.vehicle_number = vehicle_number;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public float getAmount_paid() {
        return amount_paid;
    }

    public void setAmount_paid(float amount_paid) {
        this.amount_paid = amount_paid;
    }

    public BookSlotFirebase(String booking_date, String arrival_time, String leaving_time, String vehicle_number, String location, String user_id, float amount_paid) {
        this.booking_date = booking_date;
        this.arrival_time = arrival_time;
        this.leaving_time = leaving_time;
        this.vehicle_number = vehicle_number;
        this.location = location;
        this.user_id = user_id;
        this.amount_paid = amount_paid;
    }

}
