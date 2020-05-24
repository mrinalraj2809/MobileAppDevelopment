package com.example.mrinal_raj;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    public ArrayList<MessageModel> message_list;
    private Toolbar mToolbar;
    private ScrollView mScrollView;
//    ScrollView mMainScrollView;
    private ArrayList<MessageModel> main_message_list;
    private int counter =5;
    Button resetButton;
    Fragment fragment;
    MessageAdapter messageAdapter;
    View view;
    TextView profileText;


    @Override
    protected void onStart() {
        super.onStart();
        //todo check internet connectivity


    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        view = findViewById(R.id.view_to_listen_for_touch);
        main_message_list = new ArrayList<MessageModel>();
        mToolbar = (Toolbar) findViewById(R.id.group_page_toolbar);
//        mMainScrollView = findViewById(R.id.main_scroll_view);
//        mMainScrollView.setSmoothScrollingEnabled(true);
        mScrollView = (ScrollView)findViewById(R.id.chat_scroll);
        profileText = findViewById(R.id.profile_name);
        resetButton = findViewById(R.id.resetButton);
        resetButton.setVisibility(View.INVISIBLE);
        //setSupportActionBar(mToolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("");
        profileText.setText("Anushka");

        final MediaPlayer newMessageSound = MediaPlayer.create(this,R.raw.whatsapp_new_message);

        recyclerView = findViewById(R.id.chat_recycler_view);
        message_list = new ArrayList<MessageModel>();
        RecyclerView.LayoutManager recyce = new GridLayoutManager(recyclerView.getContext(), 1);
        recyclerView.setLayoutManager(recyce);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);
        recyclerView.clearOnScrollListeners();
        recyclerView.clearOnChildAttachStateChangeListeners();

        loadMessage();
        mScrollView.fullScroll(View.FOCUS_DOWN);
        mScrollView.setSmoothScrollingEnabled(true);
        main_message_list.add(message_list.get(0));
        main_message_list.add(message_list.get(1));
        main_message_list.add(message_list.get(2));
        main_message_list.add(message_list.get(3));
        main_message_list.add(message_list.get(4));
        displayMessage();

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!isInternetAvailable())
                {
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragment_frame, new NetworkError(), "createPost").addToBackStack("NetworkError").commit();
                }
                else {
                    if(counter<30) {
                        main_message_list.add(message_list.get(counter));
                        if (message_list.get(counter).getType().equals("receiveText") || message_list.get(counter).getType().equals("receiveImage"))
                        {
                            newMessageSound.start();
                        }
                        counter++;
                        displayMessage();
                        recyclerView.smoothScrollToPosition(recyclerView.getAdapter().getItemCount());
                    }
                    else if (counter>=30)
                    {
                        resetButton.setVisibility(View.VISIBLE);
                        resetButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                counter =5;
                                main_message_list.clear();main_message_list.add(message_list.get(0));
                                main_message_list.add(message_list.get(1));
                                main_message_list.add(message_list.get(2));
                                main_message_list.add(message_list.get(3));
                                main_message_list.add(message_list.get(4));
                                displayMessage();
                                resetButton.setVisibility(View.INVISIBLE);
                            }
                        });
                        Toast.makeText(MainActivity.this, "Story has ended.", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });


    }

    private void displayMessage() {

        //loadMessage();//add((Message_Model) ((DataSnapshot)iterator.next()).getValue(Message_Model.class));

        messageAdapter = new MessageAdapter(main_message_list);

        recyclerView.setAdapter(messageAdapter);
        mScrollView.fullScroll(View.FOCUS_DOWN);
        messageAdapter.notifyDataSetChanged();
    }

    public boolean isInternetAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(this.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if(networkInfo != null && networkInfo.isConnected())
            return true;
        else
            return false;
    }

    private void loadMessage() {
        String strDateFormat = "hh:mm a";
        Date date;
        DateFormat dateFormat = new SimpleDateFormat(strDateFormat);;
        String formattedDate;
        MessageModel messageModel = new MessageModel();

        //1
        messageModel.setMessage_content("Hello Mrinal!!!");
        date = new Date();
        formattedDate= dateFormat.format(date);
        messageModel.setSent_date_time(formattedDate);
        messageModel.setType("receiveText");
        message_list.add(messageModel);

        messageModel = new MessageModel();

        //2
        messageModel.setMessage_content("How are you?");
        date = new Date();
        formattedDate= dateFormat.format(date);
        messageModel.setSent_date_time(formattedDate);
        messageModel.setType("receiveText");
        message_list.add(messageModel);

        messageModel = new MessageModel();

        //3
        messageModel.setMessage_content("HI!!! Anushka.\nHow are you");
        date = new Date();
        formattedDate= dateFormat.format(date);
        messageModel.setSent_date_time(formattedDate);
        messageModel.setType("sendText");
        message_list.add(messageModel);

        messageModel = new MessageModel();


        //4
        messageModel.setMessage_content("I am fine.");
        date = new Date();
        formattedDate= dateFormat.format(date);
        messageModel.setSent_date_time(formattedDate);
        messageModel.setType("receiveText");
        message_list.add(messageModel);

        messageModel = new MessageModel();


        //5
        messageModel.setMessage_content("Where are u now?");
        date = new Date();
        formattedDate= dateFormat.format(date);
        messageModel.setSent_date_time(formattedDate);
        messageModel.setType("receiveText");
        message_list.add(messageModel);

        messageModel = new MessageModel();


        //6
        messageModel.setMessage_content("Standing at bus stop");
        date = new Date();
        formattedDate= dateFormat.format(date);
        messageModel.setSent_date_time(formattedDate);
        messageModel.setType("sendText");
        message_list.add(messageModel);

        messageModel = new MessageModel();


        //7
        messageModel.setMessage_content("Now?");
        date = new Date();
        formattedDate= dateFormat.format(date);
        messageModel.setSent_date_time(formattedDate);
        messageModel.setType("receiveText");
        message_list.add(messageModel);

        messageModel = new MessageModel();


        //8
        messageModel.setMessage_content("I mean it's 1:30?");
        date = new Date();
        formattedDate= dateFormat.format(date);
        messageModel.setSent_date_time(formattedDate);
        messageModel.setType("receiveText");
        message_list.add(messageModel);

        messageModel = new MessageModel();


        //9
        messageModel.setMessage_content("Surely all busses are gone by now?");
        date = new Date();
        formattedDate= dateFormat.format(date);
        messageModel.setSent_date_time(formattedDate);
        messageModel.setType("receiveText");
        message_list.add(messageModel);

        messageModel = new MessageModel();


        //10
        messageModel.setMessage_content("Yeah I couldn't hurry...");
        date = new Date();
        formattedDate= dateFormat.format(date);
        messageModel.setSent_date_time(formattedDate);
        messageModel.setType("sendText");
        message_list.add(messageModel);

        messageModel = new MessageModel();


        //11
        messageModel.setMessage_content("Wait I got the bus.");
        date = new Date();
        formattedDate= dateFormat.format(date);
        messageModel.setSent_date_time(formattedDate);
        messageModel.setType("sendText");
        message_list.add(messageModel);

        messageModel = new MessageModel();


        //12
        messageModel.setMessage_content("I am sending you the image");
        date = new Date();
        formattedDate= dateFormat.format(date);
        messageModel.setSent_date_time(formattedDate);
        messageModel.setType("sendText");
        message_list.add(messageModel);

        messageModel = new MessageModel();


        //13
        messageModel.setMessage_content("");
        date = new Date();
        formattedDate= dateFormat.format(date);
        messageModel.setSent_date_time(formattedDate);
        messageModel.setType("sendImage");
        message_list.add(messageModel);

        messageModel = new MessageModel();


        //14
        messageModel.setMessage_content("Anushka something is fishy");
//        final Handler handler = new Handler();
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                // Do something after 5s = 5000ms
//            }
//        }, 5000);
        date = new Date();
        formattedDate= dateFormat.format(date);
        messageModel.setSent_date_time(formattedDate);
        messageModel.setType("sendText");
        message_list.add(messageModel);

        messageModel = new MessageModel();


        //15
        messageModel.setMessage_content("The bus didn't stop");
        date = new Date();
        formattedDate= dateFormat.format(date);
        messageModel.setSent_date_time(formattedDate);
        messageModel.setType("sendText");
        message_list.add(messageModel);

        messageModel = new MessageModel();


        //16
        messageModel.setMessage_content("I saw the bus almost full");
        date = new Date();
        formattedDate= dateFormat.format(date);
        messageModel.setSent_date_time(formattedDate);
        messageModel.setType("sendText");
        message_list.add(messageModel);

        messageModel = new MessageModel();


        //17
        messageModel.setMessage_content("where as I remember there were \nonly 2passengers when I gotup.");
        date = new Date();
        formattedDate= dateFormat.format(date);
        messageModel.setSent_date_time(formattedDate);
        messageModel.setType("sendText");
        message_list.add(messageModel);

        messageModel = new MessageModel();


        //18
        messageModel.setMessage_content("What?");
        date = new Date();
        formattedDate= dateFormat.format(date);
        messageModel.setSent_date_time(formattedDate);
        messageModel.setType("receiveText");
        message_list.add(messageModel);

        messageModel = new MessageModel();


        //19
        messageModel.setMessage_content("Surely it had stopped");
        date = new Date();
        formattedDate= dateFormat.format(date);
        messageModel.setSent_date_time(formattedDate);
        messageModel.setType("receiveText");
        message_list.add(messageModel);

        messageModel = new MessageModel();


        //20
        messageModel.setMessage_content("You where asleep for GOD's sake XD");
        date = new Date();
        formattedDate= dateFormat.format(date);
        messageModel.setSent_date_time(formattedDate);
        messageModel.setType("receiveText");
        message_list.add(messageModel);

        messageModel = new MessageModel();


        //21
        messageModel.setMessage_content("I don't have a deep sleep");
        date = new Date();
        formattedDate= dateFormat.format(date);
        messageModel.setSent_date_time(formattedDate);
        messageModel.setType("sendText");
        message_list.add(messageModel);

        messageModel = new MessageModel();


        //22
        messageModel.setMessage_content("If the bus would have stopped I would have known");
        date = new Date();
        formattedDate= dateFormat.format(date);
        messageModel.setSent_date_time(formattedDate);
        messageModel.setType("sendText");
        message_list.add(messageModel);

        messageModel = new MessageModel();


        //23
        messageModel.setMessage_content("Wait");
        date = new Date();
        formattedDate= dateFormat.format(date);
        messageModel.setSent_date_time(formattedDate);
        messageModel.setType("sendText");
        message_list.add(messageModel);

        messageModel = new MessageModel();


        //24
        messageModel.setMessage_content("That person disappeared!!!");
        date = new Date();
        formattedDate= dateFormat.format(date);
        messageModel.setSent_date_time(formattedDate);
        messageModel.setType("sendText");
        message_list.add(messageModel);

        messageModel = new MessageModel();


        //25
        messageModel.setMessage_content("Mrinal are you serious");
        date = new Date();
        formattedDate= dateFormat.format(date);
        messageModel.setSent_date_time(formattedDate);
        messageModel.setType("receiveText");
        message_list.add(messageModel);

        messageModel = new MessageModel();


        //26
        messageModel.setMessage_content("Mrinal?");
        date = new Date();
        formattedDate= dateFormat.format(date);
        messageModel.setSent_date_time(formattedDate);
        messageModel.setType("receiveText");
        message_list.add(messageModel);

        messageModel = new MessageModel();


        //27
        messageModel.setMessage_content("Mriiiiial");
        date = new Date();
        formattedDate= dateFormat.format(date);
        messageModel.setSent_date_time(formattedDate);
        messageModel.setType("receiveText");
        message_list.add(messageModel);

        messageModel = new MessageModel();


        //28
        messageModel.setMessage_content("Send me you location");
        date = new Date();
        formattedDate= dateFormat.format(date);
        messageModel.setSent_date_time(formattedDate);
        messageModel.setType("receiveText");
        message_list.add(messageModel);

        messageModel = new MessageModel();


        //29
        messageModel.setMessage_content("Asleep?");
        date = new Date();
        formattedDate= dateFormat.format(date);
        messageModel.setSent_date_time(formattedDate);
        messageModel.setType("receiveText");
        message_list.add(messageModel);


        //30
        messageModel.setMessage_content("Please Respond\nWhat's wrong?");
        date = new Date();
        formattedDate= dateFormat.format(date);
        messageModel.setSent_date_time(formattedDate);
        messageModel.setType("receiveText");
        message_list.add(messageModel);

        messageModel = new MessageModel();





    }

}


