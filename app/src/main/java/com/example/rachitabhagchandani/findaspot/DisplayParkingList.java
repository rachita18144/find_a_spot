package com.example.rachitabhagchandani.findaspot;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import java.util.ArrayList;

public class DisplayParkingList extends AppCompatActivity{

    RecyclerView recyclerView;
    private ArrayList<ParkingLocations> parkingLocations;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.parking_list);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        initialise();
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(parkingLocations, this);
        recyclerView.setAdapter(adapter);
    }
    public void initialise(){
        parkingLocations = new ArrayList<ParkingLocations>();
        parkingLocations.add(new ParkingLocations("Okhla Parking Location","Okhla Industrial area, Delhi" ,20, 100));
        parkingLocations.add(new ParkingLocations("Delhi Parking", "New Delhi", 20, 100));
        parkingLocations.add(new ParkingLocations("Canaught Place parking", "CP,Delhi",50, 200));
        parkingLocations.add(new ParkingLocations("Okhla Parking Location", "Noida Area", 20, 100));
        parkingLocations.add(new ParkingLocations("Okhla Parking Location", "Delhi", 20, 100));
        parkingLocations.add(new ParkingLocations("Okhla Parking Location", "Delhi", 20, 100));
    }
    public void openParkingSlotWindow(int pos){
        ParkingLocations selectedLocation = parkingLocations.get(pos);
        Intent intent = new Intent(getApplicationContext(), ParkingSlotInformation.class);
        intent.putExtra("name", selectedLocation.getName());
        intent.putExtra("price", "Rs. " + selectedLocation.getPrice());
        intent.putExtra("capacity", "Capacity : "+selectedLocation.getCapacity());
        intent.putExtra("address", selectedLocation.getAddress());
        startActivity(intent);
    }
}
