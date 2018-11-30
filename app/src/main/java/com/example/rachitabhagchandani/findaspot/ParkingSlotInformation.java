package com.example.rachitabhagchandani.findaspot;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ParkingSlotInformation extends AppCompatActivity {
        TextView l_name, l_price, l_capacity , l_address;
        String name;
        Button proceed;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.parking_slot_information);
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
}
