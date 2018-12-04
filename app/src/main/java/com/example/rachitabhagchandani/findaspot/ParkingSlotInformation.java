package com.example.rachitabhagchandani.findaspot;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class ParkingSlotInformation extends AppCompatActivity {
        TextView l_name, l_price, l_capacity , available_car,l_address , getAvailable_two_wheeler;
        String name;
        String locationid;
        String availableCar;
        String availabletwo;
        Button proceed;
    TextView nav_name ;
    TextView nav_phone;
    private DrawerLayout mDrawerLayout;
    String uid=getUserIdFromExternalStorage();
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.parking_slot_information);
            mDrawerLayout = findViewById(R.id.drawer);
            NavigationView navigationView = findViewById(R.id.nav_view);
            View header=navigationView.getHeaderView(0);
            nav_name = (TextView)header.findViewById(R.id.nav_name);
            nav_phone = (TextView)header.findViewById(R.id.nav_phone);
            getUserDataFirebase(uid);
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
                    if(menuItem.getItemId()==R.id.current)
                    {
                        Intent intent = new Intent(getApplicationContext(),CurrentStatus.class);
                        // intent.putExtra("uid",uid);
                        startActivity(intent);
                    }
                    return true;
                }
            });
            Intent intent = getIntent();
            Bundle bundle = intent.getExtras();
            if(bundle != null) {
                l_name = (TextView) findViewById(R.id.location_name);
                l_price = (TextView) findViewById(R.id.location_price);
                l_capacity = (TextView) findViewById(R.id.capacity);
                l_address = (TextView) findViewById(R.id.location_address);
                available_car = (TextView) findViewById(R.id.available_car);
                getAvailable_two_wheeler = (TextView) findViewById(R.id.available_two_wheeler);
                name = (String) bundle.get("name");
                locationid = (String) bundle.get("location_id");
                l_name.setText(name);
                String price = (String) bundle.get("price");
                l_price.setText(price);
                String capacity = (String) bundle.get("capacity");
                l_capacity.setText(capacity);
                String address = (String) bundle.get("address");
                l_address.setText(address);
                availableCar = (String) bundle.get("available_car");
                available_car.setText("Available Car Parking : " + availableCar);
                availabletwo = (String) bundle.get("available_two_wheeler");
                getAvailable_two_wheeler.setText("Available Two Wheeler Parking : " + availabletwo);
                proceed = (Button)findViewById(R.id.proceed);
            }

            proceed.setOnClickListener(new View.OnClickListener(){
                public void onClick(View v){
                    Intent intent = new Intent(getApplicationContext(), UserDetails.class);
                    intent.putExtra("location_name",name);
                    intent.putExtra("location_id",locationid);
                    intent.putExtra("available_car", availableCar);
                    intent.putExtra("available_two", availabletwo);
                    startActivity(intent);
                }
            });
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
    public String getUserIdFromExternalStorage() {
        String line = "";
        final File file = new File(Environment.getExternalStorageDirectory()
                .getAbsolutePath(), "uid.txt");
        StringBuilder text = new StringBuilder();

        try {
            BufferedReader br = new BufferedReader(new FileReader(file));

            while ((line = br.readLine()) != null) {

                Log.d("USER_ID", line);
                text.append(line);
            }
            br.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return text.toString();

    }
}
