package com.example.trippleatt;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.gms.common.api.Status;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;

import java.util.Arrays;
import java.util.List;

public class CustomerRegistration extends AppCompatActivity {


    EditText aadharID;
    EditText name;
    EditText location;
    EditText pincode;
    EditText mobile;
    EditText emailId;
    EditText password;
    EditText re_password;
    Button deliveryPartner;
    Button shopPartner;
    int flag =0;
    
    Button next;
    String loginType ="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_resgistration);
        deliveryPartner = findViewById(R.id.button_delivery_partner);
        shopPartner = findViewById(R.id.button_shop_partner);

        next = findViewById(R.id.btnLogin);
        aadharID = findViewById(R.id.shop_owner_aadhar);
        name = findViewById(R.id.shop_owner_name);
        location = findViewById(R.id.shop_location);
        pincode = findViewById(R.id.shop_pincode);
        mobile = findViewById(R.id.shop_contact_number);
        emailId = findViewById(R.id.shop_contact_email_id);
        password = findViewById(R.id.shop_owner_password);
        re_password = findViewById(R.id.shop_owner_re_password);

        


        deliveryPartner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (flag == 0)
                {
                    flag =1;
                    loginType = "driver";
                    deliveryPartner.setBackgroundResource(R.color.blue);
                    shopPartner.setBackgroundResource(R.color.white);
                }
                else
                {
                    loginType = "";
                    deliveryPartner.setBackgroundResource(R.color.white);
                    shopPartner.setBackgroundResource(R.color.white);
                    flag =0;
                }
            }
        });
        
        shopPartner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                

                if(flag == 0)
                {
                    flag =1;
                    loginType = "shop";
                    shopPartner.setBackgroundResource(R.color.blue);
                    deliveryPartner.setBackgroundResource(R.color.white);
                }
                else
                {
                    flag = 0;
                    loginType = "";
                    shopPartner.setBackgroundResource(R.color.white);
                    deliveryPartner.setBackgroundResource(R.color.white);
                }

                
            }
        });
        
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(loginType == "driver")
                {
                    deliveryPartner.setBackgroundResource(R.color.blue);
                    final String aadhar;
                    String names;
                    String locations;
                    String pincodes;
                    String mobiles;
                    String emailIds;
                    String passwords;
                    String re_passwords;
                    aadhar = aadharID.getText().toString();
                    names = name.getText().toString();
                    locations = location.getText().toString();
                    pincodes = pincode.getText().toString();
                    mobiles = mobile.getText().toString();
                    emailIds = emailId.getText().toString();
                    passwords = password.getText().toString();
                    re_passwords = re_password.getText().toString();

                    if (!aadhar.equals("")
                        && !names.equals("")
                    && !locations.equals("")
                    && !pincodes.equals("")
                    && !mobiles.equals("")
                    && !emailIds.equals("")
                    && !passwords.equals("")
                    && !re_passwords.equals("") && passwords.equals(re_passwords))
                    {
                        Intent intent = new Intent(CustomerRegistration.this,VehicleOwnerRegistration.class);
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
                    else
                    {
                        Toast.makeText(CustomerRegistration.this, "All Feilds are Required", Toast.LENGTH_SHORT).show();
                    }

                }
                else if (loginType == "shop")
                {

                    final String aadhar;
                    String names;
                    String locations;
                    String pincodes;
                    String mobiles;
                    String emailIds;
                    String passwords;
                    String re_passwords;
                    aadhar = aadharID.getText().toString();
                    names = name.getText().toString();
                    locations = location.getText().toString();
                    pincodes = pincode.getText().toString();
                    mobiles = mobile.getText().toString();
                    emailIds = emailId.getText().toString();
                    passwords = password.getText().toString();
                    re_passwords = re_password.getText().toString();

                    if (!aadhar.equals("")
                            && !names.equals("")
                            && !locations.equals("")
                            && !pincodes.equals("")
                            && !mobiles.equals("")
                            && !emailIds.equals("")
                            && !passwords.equals("")
                            && !re_passwords.equals("") && passwords.equals(re_passwords))
                    {
                        Intent intent = new Intent(CustomerRegistration.this,ShopOwnerRegistration.class);
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
                    else
                    {
                        Toast.makeText(CustomerRegistration.this, "All Feilds are Required", Toast.LENGTH_SHORT).show();
                    }

                }
                else 
                {
                    Toast.makeText(CustomerRegistration.this, "All Feilds are Required", Toast.LENGTH_SHORT).show();
                }
            }
        });
        
        
        String API_KEY = "AIzaSyCYd9DNtP8fAnic_H5XwgCef7dmqj_7vB0" ;
        // My tripleatt project.
        Places.initialize(getApplicationContext(),API_KEY);
        PlacesClient placesClient = Places.createClient(this);

        location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Place.Field> feildList = Arrays.asList(Place.Field.ADDRESS,
                        Place.Field.PLUS_CODE,Place.Field.NAME);
                Intent intent = new Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY,feildList).build(CustomerRegistration.this);
                startActivityForResult(intent,100);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == RESULT_OK)
        {
            //When success
            //Initialize place
            Place place = Autocomplete.getPlaceFromIntent(data);
            //set address on Edit text
            location.setText(place.getAddress());
            Toast.makeText(this, place.getName(), Toast.LENGTH_SHORT).show();

        }
        else if (resultCode == AutocompleteActivity.RESULT_ERROR)
        {
            Status status = Autocomplete.getStatusFromIntent(data);
            Toast.makeText(this, status.getStatusMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}
