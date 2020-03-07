package com.example.loginact;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class EventList extends AppCompatActivity {
    public ListView listView;
    List<Event> lst = new ArrayList<Event>();
    FirebaseDatabase firebaseDatabase;
    DatabaseReference ref;
    CustomAdapterEvent customAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_list);
        //Toast.makeText(getApplicationContext(),"inide event list",Toast.LENGTH_SHORT).show();
        listView = findViewById(R.id.eventList);
        firebaseDatabase = FirebaseDatabase.getInstance();
        ref = firebaseDatabase.getReference().child("Clubs").child("deAsra123").child("Events");

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                lst.clear();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {

                    String agenda = ds.child("agenda").getValue(String.class);
                    String dateOfEvent = ds.child("dateOfEvent").getValue(String.class);
                    String timeOfEvent = ds.child("timeOfEvent").getValue(String.class);
                    String needsofEvent = ds.child("needsOfEvent").getValue(String.class);
                    String havesOfEvent = ds.child("havesOfEvent").getValue(String.class);
                    String urlMeeting = ds.child("eventUrl").getValue(String.class);
                    Event event = new Event(agenda,dateOfEvent,timeOfEvent,needsofEvent,havesOfEvent,urlMeeting);


                    lst.add(event);
                    //Toast.makeText(getApplicationContext(),"inide foreach"+ agenda,Toast.LENGTH_SHORT).show();

                }
                populatedata(lst);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

    private void populatedata(List<Event> lst) {
        if (lst.size() >= 1) {
            customAdapter = new CustomAdapterEvent(getApplicationContext(), R.layout.enentlist2, lst);
            listView.setAdapter(customAdapter);

        } else {
            listView.setVisibility(View.GONE);

        }

    }


}
