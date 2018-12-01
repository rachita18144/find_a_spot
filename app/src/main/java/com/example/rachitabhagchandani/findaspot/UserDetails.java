package com.example.rachitabhagchandani.findaspot;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;

import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class UserDetails extends AppCompatActivity {
    TextView selectDate, selectedDate, selectTime, selectedTime, selectedLeavngTime, selectleavingTime;
    EditText vehicleNumber;

    //create a new object of type past bookings and save the object in db
    BookSlotFirebase booking = new BookSlotFirebase();
    //booking.user_id = getIntent;


    final Calendar myCalendar = Calendar.getInstance();
    Button bookingConfirmation;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_details);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
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
            FirebaseDatabase.getInstance().getReference("booking_details").child("123").setValue(booking)
                                    .addOnCompleteListener(new OnCompleteListener<Void>(){
                                       public void onComplete(@NonNull Task<Void> task){
                                           if(task.isSuccessful()){
                                               Toast.makeText(UserDetails.this, "Authentication success.",
                                                       Toast.LENGTH_SHORT).show();
                                           }else{
                                               Toast.makeText(UserDetails.this, "Authentication failed.",
                                                       Toast.LENGTH_SHORT).show();
                                           }
                                       }
                                    });
    }
}
