package com.example.rachitabhagchandani.findaspot;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ParkingSlotInformation extends AppCompatActivity {
        TextView l_name, l_price, l_capacity , l_address;
        String name;
        Button proceed;
    TextView nav_name ;
    TextView nav_phone;
    private DrawerLayout mDrawerLayout;
    String uid="KhlwBCB3gabrcsh2p8Xt175Rp9I3";
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.parking_slot_information);
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
            Intent intent = getIntent();
            Bundle bundle = intent.getExtras();
            if(bundle != null) {
                l_name = (TextView) findViewById(R.id.location_name);
                l_price = (TextView) findViewById(R.id.location_price);
                l_capacity = (TextView) findViewById(R.id.capacity);
                l_address = (TextView) findViewById(R.id.location_address);
                name = (String) bundle.get("name");
                l_name.setText(name);
                String price = (String) bundle.get("price");
                l_price.setText(price);
                String capacity = (String) bundle.get("capacity");
                l_capacity.setText(capacity);
                String address = (String) bundle.get("address");
                l_address.setText(address);
                proceed = (Button)findViewById(R.id.proceed);
            }

            proceed.setOnClickListener(new View.OnClickListener(){
                public void onClick(View v){
                    Intent intent = new Intent(getApplicationContext(), UserDetails.class);
                    intent.putExtra("location_name",name);
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
}
