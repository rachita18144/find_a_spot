package com.example.rachitabhagchandani.findaspot;
//Include lat long values
public class ParkingLocations {
    String name;
    //Auto generated
    int location_id;
    String address;
    int price;
    int capacity;

    ParkingLocations(String name, String address, int price, int capacity){
        this.name = name;
        this.address = address;
        this.price = price;
        this.capacity = capacity;
    }
    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public int getPrice() {
        return price;
    }

    public int getCapacity() {
        return capacity;
    }
}
