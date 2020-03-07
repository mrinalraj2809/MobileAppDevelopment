package com.example.loginact;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.UUID;

public class Finish_Profile extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private EditText whatdoido;
    private EditText whoami;
    private EditText whatcani;
    private Button update;
    private TextView businesscategory;
    String state,city;

    private ImageView profileimg;
    private final int PICK_IMAGE_REQUEST = 71;
    private Uri filePath;

    FirebaseStorage storage;
    StorageReference storageReference;

    DatabaseReference mRef;
    FirebaseAuth mAuth;

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finish__profile);

        whatdoido = (EditText) findViewById(R.id.editText_Whatdoido);
        whoami = (EditText) findViewById(R.id.editText_WhoAmI);
        whatcani = (EditText) findViewById(R.id.editText_WhatCanIProvide);
        update = (Button) findViewById(R.id.button_submit_update);
        profileimg = (ImageView) findViewById(R.id.imageView_profile);
        businesscategory = (TextView) findViewById(R.id.editText_Category);

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        mAuth = FirebaseAuth.getInstance();

        Spinner spinner2 = findViewById(R.id.Statespinner);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this, R.array.state_choices, android.R.layout.simple_spinner_item
        );
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(adapter2);
        spinner2.setOnItemSelectedListener(this);


        Spinner spinner = findViewById(R.id.Cityspinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.city_choices, android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        final String email = mAuth.getCurrentUser().getEmail();
        //Toast.makeText(getApplicationContext(),email,Toast.LENGTH_SHORT).show();

        mRef = FirebaseDatabase.getInstance().getReference("users_profile");

        profileimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooseImage();
            }
        });

        businesscategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final CharSequence[] items = {"Agricultural Products", "Art and Craft (Production and sale)", "Beauty and Body Services" , "Clothing and Designers", "Construction, Carpentry and Design", "Cultural events","E-commerce","Education","Finance","Food Services","Home Services","Locomotive","Medical","Marketing","Office Services","Online Service","Real Estate","Retail","Software","Social Media","Technology","Telecommunications","Tourist Services"};

                AlertDialog.Builder builder = new AlertDialog.Builder(Finish_Profile.this);
                builder.setTitle("Select your business Category");
                builder.setItems(items, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {

                        //Toast.makeText(Finish_Profile.this,"Choose :" + items[which],Toast.LENGTH_SHORT).show();
                        businesscategory.setText(getString(R.string.business_category) + items[which]);
                    }

                });


                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                builder.show();
            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadImage();
                User_Profile user_profile = new User_Profile(whatdoido.getText().toString(),whoami.getText().toString(),whatcani.getText().toString(),"1",businesscategory.getText().toString(), "0",city,state);
                mRef.child(email.replace(".",",")).setValue(user_profile);
                //Toast.makeText(getApplicationContext(),email,Toast.LENGTH_SHORT).show();

            }
        });

    }

    private void chooseImage()
    {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null )
        {
            filePath = data.getData();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                profileimg.setImageBitmap(bitmap);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }

            uploadImage();

        }
    }

    private void uploadImage()
    {

        if(filePath != null)
        {
            final String email = mAuth.getCurrentUser().getEmail();
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            StorageReference ref = storageReference.child("images/users/"+ email.replace(".",",") + "/" + "profile");
            ref.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.dismiss();
                            Toast.makeText(Finish_Profile.this, "Uploaded", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(Finish_Profile.this, "Failed "+e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0*taskSnapshot.getBytesTransferred()/taskSnapshot
                                    .getTotalByteCount());
                            progressDialog.setMessage("Uploaded "+(int)progress+"%");
                        }
                    });
        }
    }



    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        switch (adapterView.getId()) {
            case R.id.spinner:
                city = adapterView.getSelectedItem().toString();
                break;
            case R.id.spinner2:
                state = adapterView.getSelectedItem().toString();
                break;
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
