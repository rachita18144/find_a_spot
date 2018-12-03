package com.example.rachitabhagchandani.findaspot;

import android.content.Intent;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;

public class CurrentStatus extends AppCompatActivity {
TextView vno,a_time,d_time,payment;
Button make_payment;
String uid;
String charge;
    String id;
    String current_booking;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_status);
        vno=(TextView)findViewById(R.id.vno);
        a_time=(TextView)findViewById(R.id.a_time);
        d_time=(TextView)findViewById(R.id.d_time);
        payment=(TextView)findViewById(R.id.payment);
        make_payment=(Button)findViewById(R.id.make_payment);
        make_payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("saumya","making payment");
                Intent launchIntent = getPackageManager().getLaunchIntentForPackage("net.one97.paytm");
                 startActivity( launchIntent );
            }
        });
        uid=getUserIdFromExternalStorage();
        getDataFromDB();
    }

    public void getDataFromDB()
    {
        FirebaseDatabase.getInstance().getReference("booking_details").child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds:dataSnapshot.getChildren())
                {
                    for(DataSnapshot ds1:ds.getChildren())
                    {
                        if(ds1.getKey().equals("locationid"))
                        {
                            id=ds1.getValue().toString();
                            Log.d("saumya","got it------"+id);
                        }
                        if(ds1.getKey().equals("status"))
                        {
                            if(ds1.getValue().equals("approved"))
                            {
                                Log.d("saumya","this entry is approved"+ds.getKey());
                                vno.setText(ds.child("vehicle_number").getValue().toString());
                                a_time.setText(ds.child("arrival_time").getValue().toString());
                                d_time.setText(ds.child("leaving_time").getValue().toString());
                                payment.setText("Rs. 0");
                            }
                            else if(ds1.getValue().equals("finished"))
                            {
                                Log.d("saumya","this entry is approved"+ds.getKey());
                                int start=Integer.parseInt(a_time.getText().toString().split(":")[0]);
                                int end=Integer.parseInt(d_time.getText().toString().split(":")[0]);
                                float amount=Math.abs(40*(end-start));
                                Log.d("saumya",start+" "+end);
                                payment.setText("Rs "+Float.toString(amount));
                            }
                            else
                            {
                               /* Log.d("saumya","payed status");*/
                            }
                        }
                    }
                }
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
