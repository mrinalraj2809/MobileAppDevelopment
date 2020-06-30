package com.example.trippleatt;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class PreviewShopOwner extends AppCompatActivity {
    private com.anton46.stepsview.StepsView mStepsView;
    ImageView innerImage;
    ImageView frontImage;
    TextView shopNames;
    TextView addressDescriptions;
    TextView phones;
    TextView emails;

    TextView personNames;
    TextView personAddresss;
    TextView personEmails;
    TextView personAadhars;
    TextView personPhones;

    Button submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview_shop_owner);
        submit = findViewById(R.id.btnLogins);
        String[] steps = {"Owner Details","Shop Details","Preview & Submit"};
        mStepsView = findViewById(R.id.stepsViewPreviewShopOwner);
        mStepsView.setLabels(steps)
                .setBarColorIndicator(PreviewShopOwner.this.getResources().getColor(R.color.grey))
                .setProgressColorIndicator(PreviewShopOwner.this.getResources().getColor(R.color.yellowGreen))
                .setLabelColorIndicator(PreviewShopOwner.this.getResources().getColor(R.color.black))
                .setCompletedPosition(2)
                .drawView();
        innerImage = findViewById(R.id.inner_image);
        frontImage = findViewById(R.id.profile_image);
        shopNames = findViewById(R.id.shop_name);
        addressDescriptions = findViewById(R.id.description);
        phones = findViewById(R.id.t_phone_number);
        emails = findViewById(R.id.t_email);
        personNames = findViewById(R.id.t_name);
        personAddresss = findViewById(R.id.t_address);
        personEmails = findViewById(R.id.t_email);
        personAadhars = findViewById(R.id.t_aadhar);
        personPhones = findViewById(R.id.t_phone_number);


        final String aadhar;
        final String names;
        final String locations;
        final String pincodes;
        final String mobiles;
        final String emailIds;
        final String passwords;
        final String re_passwords;
        aadhar = getIntent().getStringExtra("aadhar");
        names = getIntent().getStringExtra("names");
        locations = getIntent().getStringExtra("locations");
        pincodes = getIntent().getStringExtra("pincodes");
        mobiles = getIntent().getStringExtra("mobiles");
        emailIds = getIntent().getStringExtra("emailIds");
        passwords = getIntent().getStringExtra("passwords");
        re_passwords = getIntent().getStringExtra("re_passwords");
        String fileUriFront;
        String fileUriInterior;
        String shopName;
        String category;
        String email;
        String contact;
        String gst;
        Uri front,interior;
        fileUriFront = getIntent().getStringExtra("fileUriFront");
        fileUriInterior = getIntent().getStringExtra("fileUriInterior");
        shopName = getIntent().getStringExtra("shopName");
        category = getIntent().getStringExtra("category");
        contact = getIntent().getStringExtra("contact");
        email = getIntent().getStringExtra("email");
        gst = getIntent().getStringExtra("gst");

        front = Uri.parse(fileUriFront);
        interior = Uri.parse(fileUriInterior);
        innerImage.setImageURI(interior);
        frontImage.setImageURI(front);
        shopNames.setText(shopName);
        addressDescriptions.setText(locations);
        phones.setText(mobiles);
        emails.setText(emailIds);

        personNames.setText(names);
        personAddresss.setText(locations);
        personEmails.setText(email);
        personAadhars.setText(aadhar);
        personPhones.setText(contact);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(PreviewShopOwner.this);
                builder.setTitle("Profile Created");
                builder.setMessage("Thank You for connecting with us. Please Download & install admin panel");
                builder.setPositiveButton("Download", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(PreviewShopOwner.this, "Download Complete", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(PreviewShopOwner.this, "Cancelled", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}
