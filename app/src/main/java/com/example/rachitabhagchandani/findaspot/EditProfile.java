package com.example.rachitabhagchandani.findaspot;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;

public class EditProfile extends AppCompatActivity {
    String uid;
    User u2;
    EditText name, email, phone, vehicle_no;
    TextView topname;
    Button editButton;
    String vstring="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        name = (EditText) findViewById(R.id.name);
        topname = (TextView) findViewById(R.id.nametop);
        phone = (EditText) findViewById(R.id.contact);
        editButton = (Button) findViewById(R.id.editprofilebutton);
        vehicle_no = (EditText) findViewById(R.id.vehiclelist);
        vehicle_no.setEnabled(false);
        Intent i = getIntent();
        uid = i.getStringExtra("uid");
        getUserDetailsFirebase();
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String uname = name.getText().toString();
                String contact = phone.getText().toString();
                String vehicle = vehicle_no.getText().toString();
                saveToDB(uname, contact, vehicle,contact);

            }
        });
    }

    public void getUserDetailsFirebase() {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference db_ref = firebaseDatabase.getReference("users").child(uid);
        Log.d("saumya", db_ref.toString());
        Log.d("saumya", "path is " + db_ref.getPath());
        db_ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                u2 = new User();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                  /*Log.d("saumya","iterating over "+ds.getKey());
                  Log.d("saumya","its value is "+ds.getValue());*/
                    if (ds.getKey().equals("email_id")) {
                        u2.email_id = ds.getValue().toString();
                    } else if (ds.getKey().equals("phone")) {
                        u2.phone = ds.getValue().toString();
                        phone.setText(u2.phone);
                    } else {
                        u2.name = ds.getValue().toString();
                        name.setText(u2.name);
                        topname.setText(u2.name);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        Log.d("saumya","-------------");
        FirebaseDatabase.getInstance().getReference("booking_details").child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                vstring="";
             for(DataSnapshot ds:dataSnapshot.getChildren())
             {
                 for (DataSnapshot ds1:ds.getChildren())
                 {

                     Log.d("saumya","looping over:"+ds.getKey());
                     if(ds1.getKey().equals("vehicle_number"))
                     {
                         Log.d("saumya","hip hip"+ds1.getValue());
                         vstring+=ds1.getValue()+",";
                     }
                 }

             }
             Log.d("saumya","list is :"+vstring);
             vehicle_no.setText(vstring);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void saveToDB(String name, String email, String vehicle_no,String contact) {
     FirebaseDatabase.getInstance().getReference("users").child(uid).child("name").setValue(name);
     FirebaseDatabase.getInstance().getReference("users").child(uid).child("phone").setValue(contact);
     topname.setText(name);
     Toast.makeText(getApplicationContext(),"Data updated",Toast.LENGTH_LONG).show();
    }
}

