package com.example.rachitabhagchandani.findaspot;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class UserDetails extends AppCompatActivity {
    TextView selectDate, selectedDate, selectTime, selectedTime, selectedLeavngTime, selectleavingTime;
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
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(UserDetails.this,
                new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                       selectedTime.setText(hourOfDay + ":" + minute);
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
                    }
                }, hour, minute, false);
        timePickerDialog.show();
    }

    public void updateLabel(){
        selectedDate = (TextView) findViewById(R.id.display_date);
        String myFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        selectedDate.setText(sdf.format(myCalendar.getTime()));
    }
    public void bookParkingSlot(){

    }
}
