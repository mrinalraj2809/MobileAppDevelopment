package com.example.collegeevent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;
//import android.widget.Toolbar;
import androidx.appcompat.widget.Toolbar;

import com.example.collegeevent.Lokesh.LoginActivity;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;

import java.util.ArrayList;
import java.util.Iterator;

public class ChannelGroup extends AppCompatActivity {
    private RecyclerView recyclerView;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference mRefChats;
    private DatabaseReference mRefAdmin;
    private ArrayList<Message_Model> message_list;
    private Toolbar mToolbar;
    private ImageView sendButton;
    private EditText textMessage;
    private String senderAdminName;
    private ImageView welcomeMessage;
    private LinearLayout messageLinearLayout;
    private ScrollView mScrollView;
    private ImageView attachfileImg;
    String groupName;
    FirebaseAuth mAuth;
    FirebaseUser currentUser;
    String checker = "", myUri="";
    StorageTask uploadTask;
    Uri fileUri;


    @Override
    protected void onStart() {
        super.onStart();
        if(mAuth== null)
        {
            startActivity(new Intent(this, LoginActivity.class));
        }
        mRefChats.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                if(dataSnapshot.exists())
                {
                    displayMessage(dataSnapshot);
//                    mScrollView.fullScroll(View.FOCUS_DOWN);
                    recyclerView.smoothScrollToPosition(recyclerView.getAdapter().getItemCount());
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                if(dataSnapshot.exists())
                {
                    displayMessage(dataSnapshot);
                    mScrollView.fullScroll(View.FOCUS_DOWN);
                }
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_channel_group);
        mAuth = FirebaseAuth.getInstance();

        currentUser = mAuth.getCurrentUser();
        welcomeMessage = findViewById(R.id.welcome_hi_message);
        mToolbar = (Toolbar) findViewById(R.id.group_page_toolbar);
        messageLinearLayout = findViewById(R.id.message_layout);
        mScrollView = (ScrollView)findViewById(R.id.chat_scroll);
        attachfileImg = (ImageView) findViewById(R.id.attach_file_image_view);
        //setSupportActionBar(mToolbar);
        setSupportActionBar(mToolbar);

        groupName = getIntent().getExtras().get("GROUPNAME").toString();
        getSupportActionBar().setTitle(groupName);

        firebaseDatabase = FirebaseDatabase.getInstance();
        mRefAdmin = firebaseDatabase.getReference(groupName).child("admin");

        mRefChats = firebaseDatabase.getReference(groupName).child("Chats");
        mRefAdmin.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot x: dataSnapshot.getChildren())
                {
//                    Toast.makeText(ChannelGroup.this, x.child("user_id").getValue().toString(), Toast.LENGTH_SHORT).show();
                    if (x.child("user_id").getValue().toString().equals(mAuth.getUid()))
                    {
                        messageLinearLayout.setVisibility(View.VISIBLE);
                        senderAdminName = x.child("name").getValue().toString();
                    }
                    else
                        messageLinearLayout.setVisibility(View.GONE);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        recyclerView = findViewById(R.id.chat_recycler_view);
        message_list = new ArrayList<Message_Model>();
        RecyclerView.LayoutManager recyce = new GridLayoutManager(recyclerView.getContext(), 1);
        recyclerView.setLayoutManager(recyce);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
//        recyclerView.setHasFixedSize(true);
//        recyclerView.clearOnScrollListeners();
//        recyclerView.clearOnChildAttachStateChangeListeners();
//        message_list = new ArrayList<Message_Model>();
//        mRefChats.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                Message_Model value;
//                message_list.clear();
//                for (DataSnapshot x : dataSnapshot.getChildren()) {
//                    value = x.getValue(Message_Model.class);
//                    message_list.add(value);
//                    // Toast.makeText(Main2Activity.this, "item: "+list.get(i).title, Toast.LENGTH_LONG).show();
//                }
//                recyclerView.clearOnScrollListeners();
//                recyclerView.clearOnChildAttachStateChangeListeners();
//
//                //This sets the all data from the firebase onto the adapter
//                MessageAdapter messageAdapter = new MessageAdapter(message_list);
//                RecyclerView.LayoutManager recyce = new GridLayoutManager(recyclerView.getContext(), 1);
//                recyclerView.setLayoutManager(recyce);
//                recyclerView.setItemAnimator(new DefaultItemAnimator());
//                recyclerView.setAdapter(messageAdapter);
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });


        // Sending the message
        sendButton = findViewById(R.id.send_button);
        textMessage = findViewById(R.id.editTextMessage);

        final Message_Model message_model = new Message_Model();
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = textMessage.getText().toString().trim();
                if (!message.equals(""))
                {
                    textMessage.setText("");
                    message_model.setMessage_content(message);
                    message_model.setSenders_name(senderAdminName);
                    message_model.setSenders_unique_id(mAuth.getUid());
                    message_model.setSent_date_time(""+System.currentTimeMillis());
                    recyclerView.scrollToPosition(message_list.size()-1);
                    mRefChats.child(""+System.currentTimeMillis()).setValue(message_model)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(ChannelGroup.this, "Message Sent", Toast.LENGTH_SHORT).show();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(ChannelGroup.this, "Message Failed", Toast.LENGTH_SHORT).show();
                                }
                            });
                    mScrollView.fullScroll(View.FOCUS_DOWN);
                }

            }
        });
        attachfileImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CharSequence options[] = new CharSequence[]
                        {
                                "Images",
                                "PDF Files",
                                "Ms Word Files"
                        };
                AlertDialog.Builder builder = new AlertDialog.Builder(ChannelGroup.this);
                builder.setTitle("Select the File");
                builder.setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which)
                        {
                            case 0:checker = "image";
                                    Intent intent = new Intent();
                                    intent.setAction(Intent.ACTION_GET_CONTENT);
                                    intent.setType("image/*");
                                    startActivityForResult(intent.createChooser(intent,"Select Image"),438);
                                     break;
                            case 1:checker = "pdf";break;
                            case 2:checker = "docx";break;
                        }
                    }
                });
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 438 && requestCode == RESULT_OK && data!= null && data.getData()!=null)
        {
            fileUri = data.getData();

            if(!checker.equals("image"))
            {

            }
            else if(checker.equals("image"))
            {
                StorageReference storageReference = FirebaseStorage.getInstance().getReference();
                DatabaseReference dbRef ;
                final StorageReference filePath = storageReference.child(groupName).child(mAuth.getUid()).child(""+System.currentTimeMillis()+".jpg");
                uploadTask = filePath.putFile(fileUri);
                uploadTask.continueWithTask(new Continuation() {
                    @Override
                    public Object then(@NonNull Task task) throws Exception {
                        if (!task.isSuccessful())
                        {
                            throw task.getException();

                        }
                        return filePath.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if (task.isSuccessful())
                        {
                            Uri downloadUrl = task.getResult();
                            myUri = downloadUrl.toString();
                        }
                    }
                });
            }
            else
            {
                Toast.makeText(this, "Nothing Selected Error!!!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void displayMessage(DataSnapshot dataSnapshot) {
        recyclerView.setHasFixedSize(true);
        recyclerView.clearOnScrollListeners();
        recyclerView.clearOnChildAttachStateChangeListeners();
        message_list.add(dataSnapshot.getValue(Message_Model.class));//add((Message_Model) ((DataSnapshot)iterator.next()).getValue(Message_Model.class));

        MessageAdapter messageAdapter = new MessageAdapter(message_list);

        recyclerView.setAdapter(messageAdapter);
        mScrollView.fullScroll(View.FOCUS_DOWN);
        messageAdapter.notifyDataSetChanged();
    }
}


