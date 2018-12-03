package com.example.rachitabhagchandani.findaspot;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseError;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class UserDetails extends AppCompatActivity {
    TextView selectDate, selectedDate, selectTime, selectedTime, selectedLeavngTime, selectleavingTime;
    EditText vehicleNumber;
    TextView nav_name ;
    TextView nav_phone;
    private DrawerLayout mDrawerLayout;
    String uid;
    //create a new object of type past bookings and save the object in db
    BookSlotFirebase booking = new BookSlotFirebase();
    //booking.user_id = getIntent;

    final Calendar myCalendar = Calendar.getInstance();
    Button bookingConfirmation;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_details);
        uid=getUserIdFromExternalStorage();
        NavigationView navigationView = findViewById(R.id.nav_view);
        View header=navigationView.getHeaderView(0);
        mDrawerLayout = findViewById(R.id.drawer);
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
        booking.user_id = getUserIdFromExternalStorage();
       // Log.d("USER_ID",booking.user_id);
        TextView l_name;
        if (bundle != null) {
            l_name = (TextView) findViewById(R.id.booking_location);
            String name = (String) bundle.get("location_name");
            l_name.setText(name);
        }

        //------------------Arrival and leaving time
        selectTime = (TextView) findViewById(R.id.select_time);
         selectTime .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showTimePicker();
            }
        });

         selectleavingTime= (TextView) findViewById(R.id.select_leaving_time);
         selectleavingTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showTimePickerForLeavingTime();
            }
        });

         //-----------------select date for booking slot------------------------------
        selectDate = (TextView) findViewById(R.id.select_date);
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }
        };

        selectDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(UserDetails.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        //----------------------------------------------------------------------------

        //-----------------confirm booking parking slot for user

          bookingConfirmation = (Button) findViewById(R.id.booking_confirmation);
          bookingConfirmation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bookParkingSlot();
            }
        });

    }

    public void showTimePicker(){
        selectedTime = (TextView) findViewById(R.id.display_time);
        final Calendar c = Calendar.getInstance();
        final int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(UserDetails.this,
                new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                       selectedTime.setText(hourOfDay + ":" + minute);
                        //get time from hour and minute
                        Time arrivalTime = getTimeFromHourAndMinute(hourOfDay, minute);
                        booking.arrival_time = arrivalTime.toString();
                    }
                }, hour, minute, false);
        timePickerDialog.show();
    }

      public void showTimePickerForLeavingTime(){
       selectedLeavngTime = (TextView) findViewById(R.id.display_leaving_time);
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(UserDetails.this,
                new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                       selectedLeavngTime.setText(hourOfDay + ":" + minute);
                        Time leavingTime = getTimeFromHourAndMinute(hourOfDay, minute);
                        booking.leaving_time = leavingTime.toString();
                    }
                }, hour, minute, false);
        timePickerDialog.show();
    }

    //***************UTILITY FUNCTIONS****************
   public Time getTimeFromHourAndMinute(int hourOfDay, int minute){
        String time = hourOfDay + ":" + minute + ":" + "00";
        Time javatime = Time.valueOf(time);
        return javatime;
   }

    public void updateLabel(){
        selectedDate = (TextView) findViewById(R.id.display_date);
        String myFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        selectedDate.setText(sdf.format(myCalendar.getTime()));
        booking.booking_date = sdf.format(myCalendar.getTime());
    }

    public void bookParkingSlot(){
        vehicleNumber = (EditText) findViewById(R.id.vehcile_number);
        booking.vehicle_number = vehicleNumber.getText().toString();
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("booking_details");
        DatabaseReference postsRef = ref.child(booking.user_id);
        DatabaseReference newPostRef = postsRef.push();
        newPostRef.setValue(booking).addOnCompleteListener(new OnCompleteListener<Void>(){
                                       public void onComplete(@NonNull Task<Void> task){
                                           if(task.isSuccessful()){
                                               Toast.makeText(UserDetails.this, "Booking Successfull",
                                                       Toast.LENGTH_SHORT).show();
                                           }else{
                                               Toast.makeText(UserDetails.this, "Booking Failed",
                                                       Toast.LENGTH_SHORT).show();
                                           }
                                       }
                                    });
   }

    public void getUserDataFirebase(String uid) {
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

