package com.example.rachitabhagchandani.findaspot;

import android.content.Intent;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

public class PastBookingActivity extends AppCompatActivity {

     RecyclerView recyclerView;
     private FirebaseDatabase firebaseDatabase;
     private ArrayList<BookSlotFirebase> pastBookings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_past_bookings);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerViewPastBookings);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        final String uid = getUserIdFromExternalStorage();
        getBookingDetailsFromUid(uid);
        for(int i=0; i<10000000; i++){

        }
        PastBookingsAdapter adapter = new PastBookingsAdapter(pastBookings, this);
        recyclerView.setAdapter(adapter);
    }


     public String getUserIdFromExternalStorage() {
        String line = "";
        final File file = new File(Environment.getExternalStorageDirectory()
                .getAbsolutePath(), "uid.txt");
        StringBuilder text = new StringBuilder();

        try {
            BufferedReader br = new BufferedReader(new FileReader(file));

            while ((line = br.readLine()) != null) {
                text.append(line);
            }
            br.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return text.toString();
    }

    //get booking details of user corresponding where uid = uid of user

    public void getBookingDetailsFromUid(String uid){

        Log.d("ye user id hai", uid);
        final String userId = uid;
        pastBookings = new ArrayList<BookSlotFirebase>();
        firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference db_ref = firebaseDatabase.getReference("booking_details");
          db_ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                Log.d("booking","result: "+dataSnapshot.hasChildren());
                Log.d("booking","count : "+dataSnapshot.getChildrenCount());
                for(DataSnapshot ds:dataSnapshot.getChildren())
                {
                    Log.d("db wali", ds.child("user_id").getValue().toString());
                    if(ds.child("user_id").getValue().toString().equals(userId)){
                        Log.d("imuser", ds.child("user_id").getValue().toString());
                        Log.d("imuser", ds.child("booking_date").getValue().toString());
                        BookSlotFirebase pb = dataSnapshot.getValue(BookSlotFirebase.class);
                        pb.setBooking_date(ds.child("booking_date").getValue().toString());
                        pb.setArrival_time(ds.child("arrival_time").getValue().toString());
                        pb.setLeaving_time(ds.child("leaving_time").getValue().toString());
                        pb.setVehicle_number(ds.child("vehicle_number").getValue().toString());
                        pb.setUser_id(ds.child("user_id").getValue().toString());
                        //pb.setLocation(ds.child("location").getValue().toString());
                        pb.setAmount_paid(Float.parseFloat(ds.child("amount_paid").getValue().toString()));
                        pastBookings.add(pb);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
             Log.d("error",databaseError.getDetails());
            }
        });

    }
}
