package com.example.rachitabhagchandani.findaspot;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.ArrayList;

public class DisplayParkingList extends AppCompatActivity{

    RecyclerView recyclerView;
    private ArrayList<ParkingLocations> parkingLocations;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.parking_list);
        Intent i = getIntent();
        Bundle bundle = i.getExtras();
        if(bundle!=null) {
            parkingLocations = (ArrayList<ParkingLocations>) bundle.getSerializable("list_locations");
        }

        //parkingLocations = (ArrayList<ParkingLocations>) i.getSerializableExtra("list_locations");
       // Log.d("abcd",parkingLocations.get(0).getAddress());
        Log.d("size",parkingLocations.size()+"");
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(parkingLocations, this);
        recyclerView.setAdapter(adapter);
    }
    public void openParkingSlotWindow(int pos){
        ParkingLocations selectedLocation = parkingLocations.get(pos);
        Intent intent = new Intent(getApplicationContext(), ParkingSlotInformation.class);
        intent.putExtra("name", selectedLocation.getAddress());
        intent.putExtra("price", "Rs. " + selectedLocation.getCharges_car());
        intent.putExtra("capacity", "Capacity : "+selectedLocation.getCapacity_car());
        intent.putExtra("address", selectedLocation.getAddress());
        startActivity(intent);
    }
}
