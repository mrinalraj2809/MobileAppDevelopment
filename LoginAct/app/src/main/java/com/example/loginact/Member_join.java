package com.example.loginact;

import android.app.Dialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class Member_join extends AppCompatActivity {


    private Button member;
    private Button guest;
    private String clubname;

    FirebaseStorage storage;
    StorageReference storageReference;
    DatabaseReference myref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_join);

        Intent i = getIntent();
        clubname = i.getStringExtra("clubname");
        Toast.makeText(getApplicationContext(),clubname,Toast.LENGTH_SHORT).show();
        member = (Button) findViewById(R.id.member_choice_btn);
        guest = (Button) findViewById(R.id.guest_choice_btn);

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference ref = firebaseDatabase.getReference();

        ref.child("Clubs").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        myref = FirebaseDatabase.getInstance().getReference();
        member.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<String> members = new ArrayList<>();
                members.add(FirebaseAuth.getInstance().getCurrentUser().getEmail());

                myref.child("Clubs").child(clubname).child("members").setValue(members);
                myref.child("users_profile").child("my_clubs").child(FirebaseAuth.getInstance().getCurrentUser().getEmail().toString().replace(".",",")).child(clubname).setValue(clubname);
                Intent intent = new Intent(getApplicationContext(),Club_Activity.class);
                Bundle b = new Bundle();
                b.putString("keyvalue","1");
                intent.putExtras(b);
                startActivity(intent);

            }
        });

        guest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final Dialog dialogdetail = new Dialog(Member_join.this);
                    dialogdetail.setContentView(R.layout.dialog);

                    dialogdetail.show();


                final EditText whoami = (EditText) dialogdetail.findViewById(R.id.who_am_i);
                final EditText whatiwant = (EditText) dialogdetail.findViewById(R.id.what_i_want);
                final EditText refer = (EditText) dialogdetail.findViewById(R.id.referred_by);
                Button submit = (Button) dialogdetail.findViewById(R.id.submit_guest);


                submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String swhoami = whoami.getText().toString();
                        String swhatiwant = whatiwant.getText().toString();
                        String srefer = refer.getText().toString();

                        Toast.makeText(getApplicationContext(),"Inside",Toast.LENGTH_SHORT).show();

                        List<String> guests = new ArrayList<>();
                        guests.add(FirebaseAuth.getInstance().getCurrentUser().getEmail());

                        myref.child("Clubs").child(clubname).child("guests").setValue(guests);

                        Intent intent = new Intent(getApplicationContext(),Club_Activity.class);
                        Bundle b = new Bundle();
                        b.putString("keyvalue","0");
                        intent.putExtras(b);
                        startActivity(intent);

                    }
                });
            }
        });
    }
}
