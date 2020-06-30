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

public class PreviewVehicleOwner extends AppCompatActivity {
    private com.anton46.stepsview.StepsView mStepsView;

    ImageView profile;

    TextView personNames;
    TextView personAddresss;
    TextView personEmails;
    TextView personAadhars;
    TextView personPhones;
    TextView deliverThrough;
    TextView licenceNo;
    TextView vehicleNo;

    Button submit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview_vehicle_owner);
        String[] steps = {"Profile Details", "Vehicle Details", "Preview & Submit"};

//        profile.setImageURI(fileUri);
        submit = findViewById(R.id.btnLoginz);
        mStepsView = findViewById(R.id.stepsViewPreviewVehicleOwner);
        mStepsView.setLabels(steps)
                .setBarColorIndicator(PreviewVehicleOwner.this.getResources().getColor(R.color.grey))
                .setProgressColorIndicator(PreviewVehicleOwner.this.getResources().getColor(R.color.yellowGreen))
                .setLabelColorIndicator(PreviewVehicleOwner.this.getResources().getColor(R.color.black))
                .setCompletedPosition(2)
                .animate();
        profile = findViewById(R.id.profile_images);
        personNames = findViewById(R.id.t_names);
        personAddresss = findViewById(R.id.t_usns);
        personEmails = findViewById(R.id.t_emails);
        personAadhars = findViewById(R.id.t_user_types);
        personPhones = findViewById(R.id.t_phone_numbers);
        deliverThrough = findViewById(R.id.t_deliver_through);
        licenceNo = findViewById(R.id.t_driving_licence_number);
        vehicleNo = findViewById(R.id.t_vehicle_number);

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
        Uri licence, vehicle_no;
        String licenceNoz = getIntent().getStringExtra("fileUriDriveLicence");
        String vehicleNumberz = getIntent().getStringExtra("fileUriVehicleNumber");
        String vehicleTypesz = getIntent().getStringExtra("vehicleTypes");


        licence = Uri.parse(licenceNoz);
//        vehicle_no = Uri.parse(vehicleTypesz);
        deliverThrough.setText(vehicleTypesz);
        licenceNo.setText(licenceNoz);
        vehicleNo.setText(vehicleNumberz);

        personNames.setText(names);
        personAddresss.setText(locations);
        personEmails.setText(emailIds);
        personAadhars.setText(aadhar);
        personPhones.setText(mobiles);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(PreviewVehicleOwner.this);
                builder.setTitle("Profile Created");
                builder.setMessage("Thank You for connecting with us. Please Download & install admin panel");
                builder.setPositiveButton("Download", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(PreviewVehicleOwner.this, "Download Complete", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(PreviewVehicleOwner.this, "Cancelled", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}
