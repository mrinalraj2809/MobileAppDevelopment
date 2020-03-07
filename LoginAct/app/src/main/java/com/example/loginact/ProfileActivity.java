package com.example.loginact;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.io.File;
import java.io.IOException;

public class ProfileActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private AppBarLayout appBarLayout;
    private ViewPager viewPager;
    public CircularImageView circularImageView;
    StorageReference storageReference;
    private TextView name_profile;
    DatabaseReference mref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        tabLayout = findViewById(R.id.tablayout);
        appBarLayout =  findViewById(R.id.apparid);
        viewPager =  findViewById(R.id.viewpager);
        circularImageView = (CircularImageView) findViewById(R.id.img);
        name_profile = (TextView) findViewById(R.id.name_profile);
        mref = FirebaseDatabase.getInstance().getReference();

        name_profile.setText(mref.child("users").child(FirebaseAuth.getInstance().getCurrentUser().getEmail().replace(".",",")).getKey());


        viewpageradapter adapter = new viewpageradapter(getSupportFragmentManager());
        adapter.AddFragment(new PERSONAL(),"PERSONAL");
        adapter.AddFragment(new BUISNESS(),"BUSINESS");
        adapter.AddFragment(new HAVESNEEDS(),"HAVES AND NEEDS");
        viewPager.setAdapter(adapter);

        tabLayout.setupWithViewPager(viewPager);

        String email = FirebaseAuth.getInstance().getCurrentUser().getEmail().toString().replace(".",",");

        storageReference = FirebaseStorage.getInstance().getReferenceFromUrl("gs://login-fbbd7.appspot.com/images/users/" + email);
        try {
            final File localFile = File.createTempFile("profile", "jpeg");
            storageReference.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    Bitmap bitmap = BitmapFactory.decodeFile(localFile.getAbsolutePath());

                    circularImageView.setImageBitmap(bitmap);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(ProfileActivity.this,"Failed Loading Image",Toast.LENGTH_SHORT).show();
                }
            });

        } catch (IOException e) {

        }

    }
}
