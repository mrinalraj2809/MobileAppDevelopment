package com.example.loginact;

import android.support.v7.app.AppCompatActivity;

import java.util.List;

public class Club extends AppCompatActivity {

    String clubName;
    String objective;
    String Secretary;
    String president;
    List<String> members;
    List<String> guests;

    public Club(String clubName, String objective) {
        this.clubName = clubName;
        this.objective = objective;
    }

    public Club(String clubName, String objective, String secretary, String president) {
        this.clubName = clubName;
        this.objective = objective;
        this.Secretary = secretary;
        this.president = president;
        //this.members = members;
        //this.guests = guests;
    }

}
