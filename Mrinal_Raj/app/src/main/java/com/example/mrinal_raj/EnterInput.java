package com.example.mrinal_raj;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import java.util.ArrayList;

public class EnterInput extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_input);
        MainActivity mainActivity = new MainActivity();
        ArrayList<MessageModel> messageModels = mainActivity.message_list;
    }
}
