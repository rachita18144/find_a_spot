package com.example.rachitabhagchandani.findaspot;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Date;

public class PastBookingActivity extends AppCompatActivity {

     RecyclerView recyclerView;
     private ArrayList<PastBookings> pastBookings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_past_bookings);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerViewPastBookings);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        initialize();
        PastBookingsAdapter adapter = new PastBookingsAdapter(pastBookings, this);
        //RecyclerViewAdapter adapter = new RecyclerViewAdapter(pastBookings, this);
        recyclerView.setAdapter(adapter);
    }


    public void initialize(){
        pastBookings = new ArrayList<PastBookings>();
        Date date = new Date();
        java.sql.Time time = java.sql.Time.valueOf( "18:05:00" );
        pastBookings.add(new PastBookings(date, time ,time , "abcd","121e","Nehru Place",100));
        pastBookings.add(new PastBookings(date, time ,time , "abcd","121e", "Nehru Place",100));
        pastBookings.add(new PastBookings(date, time ,time , "abcd","121e", "Nehru Place",100));
        pastBookings.add(new PastBookings(date, time ,time , "abcd","121e", "Nehru Place",100));
        pastBookings.add(new PastBookings(date, time ,time , "abcd","121e", "Nehru Place",100));
        pastBookings.add(new PastBookings(date, time ,time , "abcd","121e", "Nehru Place",100));
    }

    public void openParkingSlotWindow(int pos){
        /*
        ParkingLocations selectedLocation = parkingLocations.get(pos);
        Intent intent = new Intent(getApplicationContext(), ParkingSlotInformation.class);
        intent.putExtra("name", selectedLocation.getAddress());
        intent.putExtra("price", "Rs. " + selectedLocation.getCharges_car());
        intent.putExtra("capacity", "Capacity : "+selectedLocation.getCapacity_car());
        intent.putExtra("address", selectedLocation.getAddress());
        startActivity(intent);
        */
    }
}
