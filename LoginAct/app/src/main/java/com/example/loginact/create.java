package com.example.loginact;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;


public class create extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    Button button;

    EditText clubnameedit;
    EditText objectivesedit;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference myref;
    String secretary,president;
    Club club;

    String clubname;
    String objecctive;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create);


        clubnameedit = (EditText)findViewById(R.id.clubnameedit);
        objectivesedit = (EditText)findViewById(R.id.objectivesedit);



        button = findViewById(R.id.button);




        //secretary spinner
        Spinner spinner2 = findViewById(R.id.spinner2);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this, R.array.secretary_choices, android.R.layout.simple_spinner_item
        );
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(adapter2);
        spinner2.setOnItemSelectedListener(this);

        //president spinner
        Spinner spinner = findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.president_choices, android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button

                myref = FirebaseDatabase.getInstance().getReference();

                clubname =clubnameedit.getText().toString();
                objecctive = objectivesedit.getText().toString();
                List<String> members = new ArrayList<>();
                members.add(secretary);
                members.add(president);
                members.add(FirebaseAuth.getInstance().getCurrentUser().getEmail());
                List<String> guests = new ArrayList<>();


                club = new Club(clubname,objecctive,secretary,president);


                myref.child("Clubs").child(clubname).child("clubName").setValue(clubname);
                myref.child("Clubs").child(clubname).child("objective").setValue(objecctive);
                myref.child("Clubs").child(clubname).child("Secretary").setValue(secretary);
                myref.child("Clubs").child(clubname).child("president").setValue(president);
                myref.child("Clubs").child(clubname).child("members").setValue(members);
                myref.child("Clubs").child(clubname).child("guests").setValue(guests);

                Toast.makeText(create.this, "club being created", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {
            case R.id.spinner:
                president = parent.getSelectedItem().toString();
                break;
            case R.id.spinner2:
                secretary = parent.getSelectedItem().toString();
                break;
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
