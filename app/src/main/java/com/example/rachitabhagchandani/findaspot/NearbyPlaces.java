package com.example.rachitabhagchandani.findaspot;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;

public class NearbyPlaces extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nearby_places);
        Intent i= getIntent();
        ParkingLocations pk1=(ParkingLocations)i.getSerializableExtra("pk1");
        Log.d("saumya",pk1.getAddress());
        ArrayList<ParkingLocations> mylist=(ArrayList<ParkingLocations>)i.getSerializableExtra("whole_list");
        Log.d("saumya",""+mylist.size());
    }
}
