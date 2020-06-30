package com.example.trippleatt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class ShopOwnerRegistration extends AppCompatActivity {

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
        setContentView(R.layout.activity_shop_owner_registration);
        next = findViewById(R.id.btnShopOwnerRegistrations);

        aadharID = findViewById(R.id.shop_owner_aadhars);
        name = findViewById(R.id.shop_owner_names);
        shopLocation = findViewById(R.id.shop_locations);
        shopPincode = findViewById(R.id.shop_pincodes);
        shopMobile = findViewById(R.id.shop_contact_numbers);
        shopEmailId = findViewById(R.id.shop_contact_email_ids);
        password = findViewById(R.id.shop_owner_passwords);
        re_password = findViewById(R.id.shop_owner_re_passwords);
        shopLocation = findViewById(R.id.shop_locations);
        shopPincode = findViewById(R.id.shop_pincodes);
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

                Intent intent = new Intent(ShopOwnerRegistration.this,ShopDetails.class);
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
        mStepsView = findViewById(R.id.stepsViewShopOwnerRegistration);
        String[] steps = {"Owner Details","Shop Details","Preview & Submit"};
        mStepsView.setLabels(steps)
                .setBarColorIndicator(ShopOwnerRegistration.this.getResources().getColor(R.color.grey))
                .setProgressColorIndicator(ShopOwnerRegistration.this.getResources().getColor(R.color.yellowGreen))
                .setLabelColorIndicator(ShopOwnerRegistration.this.getResources().getColor(R.color.black))
                .setCompletedPosition(0)
                .drawView();
    }
}
