package com.example.trippleatt;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;

import java.util.Arrays;
import java.util.List;

public class VehicleDetails extends AppCompatActivity {

    private Button submit;
    EditText vehicleType;
    int count=0;

    Button driveLicence;
    Button vehicleNumber;
    EditText edit_driving_licence_number;
    EditText edit_vehilcle_number;
    Uri fileUriDriveLicence;
    Uri fileUriVehicleNumber;
    private com.anton46.stepsview.StepsView mStepsView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicle_details);
        driveLicence = findViewById(R.id.licence_choose_button);
        vehicleNumber = findViewById(R.id.vehicle_number_choose_file);
        edit_driving_licence_number = findViewById(R.id.edit_driving_licence_number);
        edit_vehilcle_number = findViewById(R.id.edit_vehilcle_number);
        vehicleType = findViewById(R.id.vehicile_type);
        driveLicence.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edit_driving_licence_number.setText("Image Uploaded");
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent.createChooser(intent,"Select Image"),538);
                count++;
            }
        });
        vehicleNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent.createChooser(intent,"Select Image"),438);
                edit_vehilcle_number.setText("Image Uploaded");
                count++;
            }
        });
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
        submit = findViewById(R.id.btnSubmitVehicleDetails);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count =0;
                String vehicleTypes = vehicleType.getText().toString();;
                Intent intent = new Intent(VehicleDetails.this, PreviewVehicleOwner.class);
                intent.putExtra("aadhar",aadhar);
                intent.putExtra("names",names);
                intent.putExtra("locations",locations);
                intent.putExtra("pincodes",pincodes);
                intent.putExtra("mobiles",mobiles);
                intent.putExtra("emailIds",emailIds);
                intent.putExtra("passwords",passwords);
                intent.putExtra("re_passwords",re_passwords);

                //new details
                intent.putExtra("fileUriDriveLicence",fileUriDriveLicence.toString());
                intent.putExtra("fileUriVehicleNumber",fileUriVehicleNumber.toString());
                intent.putExtra("vehicleTypes",vehicleTypes);
                startActivity(intent);
            }
        });
        String[] steps = {"Profile Details", "Vehicle Details", "Preview & Submit"};
        mStepsView = findViewById(R.id.stepsViewVehicleDetails);
        mStepsView.setLabels(steps)
                .setBarColorIndicator(VehicleDetails.this.getResources().getColor(R.color.grey))
                .setProgressColorIndicator(VehicleDetails.this.getResources().getColor(R.color.yellowGreen))
                .setLabelColorIndicator(VehicleDetails.this.getResources().getColor(R.color.black))
                .setCompletedPosition(1)
                .drawView();


    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        Toast.makeText(this, "", Toast.LENGTH_SHORT).show();
        if(requestCode == 538 && resultCode == RESULT_OK && data!= null && data.getData()!=null)
        {
            count++;
            fileUriDriveLicence = data.getData();
//            shopFrontEdit.setText("Image Uploaded Successfully*");

        }
        else if(requestCode == 438 && resultCode == RESULT_OK && data!= null && data.getData()!=null)
        {
            count++;
            fileUriVehicleNumber = data.getData();
//            shopInteriorEdit.setText("Image Uploaded Successfully*");
        }
    }
}
