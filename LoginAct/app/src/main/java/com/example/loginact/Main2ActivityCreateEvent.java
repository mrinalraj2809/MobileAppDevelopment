package com.example.loginact;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class Main2ActivityCreateEvent extends AppCompatActivity implements
        View.OnClickListener {

    private static final String MEETING_URL = "https://hangouts.google.com/u/1/call/olzYQ4VFClNuGjsZenvYAEEM?no_rd";
    Button btnDatePicker, btnTimePicker;
    EditText txtDate, txtTime, txtagenda, txtneeds,txtHaves;        //added
    Button create_Link;
    TextView createLink;
    Button createEvent;
    DatabaseReference databaseReference;
    String sAgenda,sNeeds,sHaves,sDate,sTime;


    private int mYear, mMonth, mDay, mHour, mMinute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2_create_event);


        txtagenda = (EditText) findViewById(R.id.et_agenda); //added
        create_Link = (Button)findViewById(R.id.et_link);
        btnDatePicker=(Button)findViewById(R.id.btn_date);
        btnTimePicker=(Button)findViewById(R.id.btn_time);
        txtDate=(EditText)findViewById(R.id.in_date);
        txtTime=(EditText)findViewById(R.id.in_time);
        createLink =(TextView) findViewById(R.id.textView_CreateLink);
        createEvent = (Button)findViewById(R.id.BUTTON);
        txtneeds = (EditText) findViewById(R.id.et_needs);
        txtHaves = (EditText) findViewById(R.id.et_haves);



        btnDatePicker.setOnClickListener(this);
        btnTimePicker.setOnClickListener(this);

        create_Link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createLink.setText(MEETING_URL);

            }
        });
        createEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sAgenda = txtagenda.getText().toString();
                sDate = txtDate.getText().toString();
                sTime = txtTime.getText().toString();
                sNeeds = txtneeds.getText().toString();
                sHaves = txtHaves.getText().toString();
                if (sTime.isEmpty() || sDate.isEmpty() )
                {
                    Toast.makeText(Main2ActivityCreateEvent.this,"Date and Time required",Toast.LENGTH_SHORT).show();
                    return;
                }
                else
                {
                    Event event = new Event(sAgenda,sDate,sTime,sNeeds,sHaves,MEETING_URL);
                    databaseReference = FirebaseDatabase.getInstance().getReference();
                    databaseReference.child("Clubs").child("deAsra123").child("Events").child(txtDate.getText().toString()+txtTime.getText().toString()).setValue(event);
                    Toast.makeText(getApplicationContext(),"Event created successfully",Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(),EventList.class));
                }

            }
        });

    }

    @Override
    public void onClick(View v) {

        switch (v.getId())
        {
            case R.id.btn_date:
                                if (v == btnDatePicker) {

                                // Get Current Date
                                final Calendar c = Calendar.getInstance();
                                mYear = c.get(Calendar.YEAR);
                                mMonth = c.get(Calendar.MONTH);
                                mDay = c.get(Calendar.DAY_OF_MONTH);


                                DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                                        new DatePickerDialog.OnDateSetListener() {

                                            @Override
                                            public void onDateSet(DatePicker view, int year,
                                                                  int monthOfYear, int dayOfMonth) {

                                                txtDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                                            }
                                        }, mYear, mMonth, mDay);
                                datePickerDialog.show();
                            }
            case R.id.btn_time:
                                if (v == btnTimePicker) {

                                    // Get Current Time
                                    final Calendar c = Calendar.getInstance();
                                    mHour = c.get(Calendar.HOUR_OF_DAY);
                                    mMinute = c.get(Calendar.MINUTE);

                                    // Launch Time Picker Dialog
                                    TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                                            new TimePickerDialog.OnTimeSetListener() {

                                                @Override
                                                public void onTimeSet(TimePicker view, int hourOfDay,
                                                                      int minute) {

                                                    txtTime.setText(hourOfDay + ":" + minute);
                                                }
                                            }, mHour, mMinute, false);
                                    timePickerDialog.show();
                                }
                                    break;
            case R.id.et_link:  // code by punitha
                                AlertDialog.Builder builder = new AlertDialog.Builder(Main2ActivityCreateEvent.this);

                                if (v == create_Link) {
                                    builder.setTitle("Message:");
                                    builder.setMessage("Event is created.");
                                } else {    //if link of meeting is not added
                                    builder.setTitle("Mesaage:");
                                    builder.setMessage("Link of Meeting is not created. Kindly create it later.");
                                }
                                builder.show();

                                break;
                                }
            }
        }
