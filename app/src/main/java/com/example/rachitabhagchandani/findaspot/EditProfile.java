package com.example.rachitabhagchandani.findaspot;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

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
    EditText name,email,phone;
    TextView topname;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        name=(EditText)findViewById(R.id.name);
        topname=(TextView)findViewById(R.id.nametop);
        phone=(EditText)findViewById(R.id.contact);
        Intent i=getIntent();
        uid=i.getStringExtra("uid");
        getUserDetailsFirebase();
       // Log.d("saumya",u2.name+" "+u2.email_id+" "+u2.phone);
    }
    public void getUserDetailsFirebase()
    {
       FirebaseDatabase firebaseDatabase= FirebaseDatabase.getInstance();
        DatabaseReference db_ref=firebaseDatabase.getReference("users").child(uid);
        Log.d("saumya",db_ref.toString());
        Log.d("saumya","path is "+db_ref.getPath());
        db_ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                 u2= new User();
              for(DataSnapshot ds: dataSnapshot.getChildren())
              {
                  /*Log.d("saumya","iterating over "+ds.getKey());
                  Log.d("saumya","its value is "+ds.getValue());*/
                  if(ds.getKey().equals("email_id"))
                  {
                      u2.email_id=ds.getValue().toString();
                  }
                  else if(ds.getKey().equals("phone"))
                  {
                      u2.phone=ds.getValue().toString();
                      phone.setText(u2.phone);
                  }
                  else
                  {
                      u2.name=ds.getValue().toString();
                      name.setText(u2.name);
                      topname.setText(u2.name);
                  }
              }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}

