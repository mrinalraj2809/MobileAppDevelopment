package com.example.loginact;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ClubList extends AppCompatActivity {

    public ListView listView;
    List<Club> lst = new ArrayList<Club>();
    FirebaseDatabase firebaseDatabase;
    DatabaseReference ref;
    CustomAdapter customAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_club_list);

        //Toast.makeText(getApplicationContext(),"inide club list", Toast.LENGTH_SHORT).show();
        listView = findViewById(R.id.list_club);
        firebaseDatabase = FirebaseDatabase.getInstance();
        ref = firebaseDatabase.getReference().child("Clubs");



        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                lst.clear();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {

                    String name = ds.child("clubName").getValue(String.class);
                    String objective = ds.child("objective").getValue(String.class);

                    Club event = new Club(name,objective);


                    lst.add(event);
                    //Toast.makeText(getApplicationContext(),"inside foreach"+ name,Toast.LENGTH_SHORT).show();

                }
                populatedata(lst);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

    private void populatedata(List<Club> lst) {
        if (lst.size() >= 1) {
            customAdapter = new CustomAdapter(getApplicationContext(), R.layout.club, lst);
            listView.setAdapter(customAdapter);

        } else {
            listView.setVisibility(View.GONE);

        }

    }
}
