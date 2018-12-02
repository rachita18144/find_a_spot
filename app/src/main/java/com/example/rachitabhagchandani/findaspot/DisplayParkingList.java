package com.example.rachitabhagchandani.findaspot;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import android.widget.Toast;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class DisplayParkingList extends AppCompatActivity{

    RecyclerView recyclerView;
    private ArrayList<ParkingLocations> parkingLocations;
    TextView nav_name ;
    TextView nav_phone;
    private DrawerLayout mDrawerLayout;
    String uid="KhlwBCB3gabrcsh2p8Xt175Rp9I3";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.parking_list);
        mDrawerLayout = findViewById(R.id.drawer);
        NavigationView navigationView = findViewById(R.id.nav_view);
        View header=navigationView.getHeaderView(0);
        nav_name = (TextView)header.findViewById(R.id.nav_name);
        nav_phone = (TextView)header.findViewById(R.id.nav_phone);
        getUserDataFirebase("KhlwBCB3gabrcsh2p8Xt175Rp9I3");
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener()
        {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem)
            {
                menuItem.setChecked(true);
                mDrawerLayout.closeDrawers();
                if(menuItem.getItemId()==R.id.past)
                {
                    Intent intent = new Intent(getApplicationContext(),PastBookingActivity.class);
                    startActivity(intent);
                }

                if(menuItem.getItemId()==R.id.edit)
                {
                    Intent intent = new Intent(getApplicationContext(),EditProfile.class);
                    intent.putExtra("uid",uid);
                    startActivity(intent);
                }
                if(menuItem.getItemId()==R.id.logout)
                {
                    Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                    // intent.putExtra("uid",uid);
                    startActivity(intent);
                }
                return true;
            }
        });
        Intent i = getIntent();
        Bundle bundle = i.getExtras();
        if(bundle!=null) {
            parkingLocations = (ArrayList<ParkingLocations>) bundle.getSerializable("list_locations");
        }

        Log.d("size",parkingLocations.size()+"");
        String uid = i.getStringExtra("uid");
        Log.d("size",uid);
        saveUidInExternalStorage(uid);
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

    public void getUserDataFirebase(String uid)
    {
        FirebaseDatabase.getInstance().getReference("users").child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                nav_name.setText(dataSnapshot.child("name").getValue().toString());
                nav_phone.setText(dataSnapshot.child("phone").getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


   public void saveUidInExternalStorage(String uid){
       try {
           File file = new File(Environment.getExternalStorageDirectory(), "uid.txt");
           if(file.exists()){
               file.delete();
           }
           file.createNewFile();
           FileWriter writer = new FileWriter(file);
           writer.append(uid);
           writer.flush();
           writer.close();
           Toast.makeText(DisplayParkingList.this, "Saved", Toast.LENGTH_SHORT).show();
       } catch (IOException e) {
           e.printStackTrace();
       }
   }

}
