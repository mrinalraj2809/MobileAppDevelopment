package com.example.loginact;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.util.Calendar;

public class CalendarActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_calendar);
        Intent i = getIntent();
        String agenda = i.getStringExtra("agenda");
        String timeOfEvent = i.getStringExtra("timeOfEvent");
        String dateOfEvent = i.getStringExtra("dateOfEvent");
        String needsOfEvent = i.getStringExtra("needsOfEvent");

        // Get Current Date
        final Calendar c = Calendar.getInstance();
        Intent intent = new Intent(Intent.ACTION_EDIT);
        intent.setType("vnd.android.cursor.item/event");
        intent.putExtra("beginTime", c.getTimeInMillis());
        intent.putExtra("allDay", false);
        intent.putExtra("rrule", "FREQ=DAILY");
        intent.putExtra("endTime", c.getTimeInMillis()+timeOfEvent);
        intent.putExtra("title", agenda);
        //
        startActivity(intent);


        // Get Current Time
        //inal Calendar c = Calendar.getInstance();
        /*mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);*/

        // Launch Time Picker Dialog
        /*TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay,
                                          int minute) {

                        txtTime.setText(hourOfDay + ":" + minute);
                    }
                }, mHour, mMinute, false);
        timePickerDialog.show();*/

        //Toast.makeText(this,agenda,Toast.LENGTH_SHORT).show();



    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}
