package com.example.trippleatt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class VehicleOwnerRegistration extends AppCompatActivity {
    private Button next;
    EditText aadharID;
    EditText name;
    EditText shopLocation;
    EditText shopPincode;
    EditText shopMobile;
    EditText shopEmailId;
    EditText password;
    EditText re_password;
    private com.anton46.stepsview.StepsView mStepsView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicle_owner_registration);
        next = findViewById(R.id.btnVehicleOwnerResistration);

        aadharID = findViewById(R.id.vehicle_owner_aadhar);
        name = findViewById(R.id.vehicle_owner_name);
        shopLocation = findViewById(R.id.vehicle_location);
        shopPincode = findViewById(R.id.vehicle_pincode);
        shopMobile = findViewById(R.id.vehicle_owner_contact_number);
        shopEmailId = findViewById(R.id.vehicle_owner_contact_email_id);
        password = findViewById(R.id.vehicle_owner_owner_password);
        re_password = findViewById(R.id.vehicle_owner_re_password);
        shopLocation = findViewById(R.id.vehicle_location);
        shopPincode = findViewById(R.id.vehicle_pincode);

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
        aadharID.setText(aadhar);
        name.setText(names);
        shopLocation.setText(locations);
        shopPincode.setText(pincodes);
        shopMobile.setText(mobiles);
        shopEmailId.setText(emailIds);
        password.setText(passwords);
        re_password.setText(re_passwords);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(VehicleOwnerRegistration.this,VehicleDetails.class);
                intent.putExtra("aadhar",aadhar);
                intent.putExtra("names",names);
                intent.putExtra("locations",locations);
                intent.putExtra("pincodes",pincodes);
                intent.putExtra("mobiles",mobiles);
                intent.putExtra("emailIds",emailIds);
                intent.putExtra("passwords",passwords);
                intent.putExtra("re_passwords",re_passwords);
                startActivity(intent);
            }
        });
        String[] steps = {"Profile Details","Vehicle Details","Preview & Submit"};
        mStepsView = findViewById(R.id.stepsViewVehicleOwnerRegistration);
        mStepsView.setLabels(steps)
                .setBarColorIndicator(VehicleOwnerRegistration.this.getResources().getColor(R.color.grey))
                .setProgressColorIndicator(VehicleOwnerRegistration.this.getResources().getColor(R.color.yellowGreen))
                .setLabelColorIndicator(VehicleOwnerRegistration.this.getResources().getColor(R.color.black))
                .setCompletedPosition(0)
                .animate()
        .setStartDelay(1010);
    }
}
