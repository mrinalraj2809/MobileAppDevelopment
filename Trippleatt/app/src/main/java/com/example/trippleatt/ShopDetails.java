package com.example.trippleatt;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class ShopDetails extends AppCompatActivity {
    private com.anton46.stepsview.StepsView mStepsView;
    private Button shopFront;
    private Button shopInterior;
    private Button submit;
    EditText shopNameEdit;
    EditText shopPincodeEdit;
    EditText shopLocationEdit;
    EditText shopCategoryEdit;
    EditText shopContactNumberEdit;
    EditText shopContactEmailEdit;
    EditText shopGSTEdit;


    EditText shopFrontEdit;
    EditText shopInteriorEdit;
    int count = 0;

    String checker = "", myUri="";
//    StorageTask uploadTask;
    Uri fileUriFront;
    Uri fileUriInterior;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_details);
        submit = findViewById(R.id.btnSubmitShopDetails);

        shopNameEdit = findViewById(R.id.shop_name);
        shopPincodeEdit = findViewById(R.id.shop_pincode);
        shopLocationEdit = findViewById(R.id.shop_location);
        shopCategoryEdit = findViewById(R.id.shop_category);
        shopContactNumberEdit = findViewById(R.id.shop_contact_number);
        shopContactEmailEdit = findViewById(R.id.shop_contact_email_id);
        shopGSTEdit = findViewById(R.id.shop_gst_no);

        //get intent values
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

        shopPincodeEdit.setText(pincodes);
        shopLocationEdit.setText(locations);
        shopFront = findViewById(R.id.shop_front_choose_button);
        shopFrontEdit = findViewById(R.id.edit_shop_front);
        shopInteriorEdit = findViewById(R.id.edit_shop_interior);
        shopFront.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent.createChooser(intent,"Select Image"),538);
            }
        });
        shopInterior = findViewById(R.id.shop_interior_choose_file);
        shopInterior.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent.createChooser(intent,"Select Image"),438);
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String gst = shopGSTEdit.getText().toString();
                String shopName = shopNameEdit.getText().toString();
                String category = shopCategoryEdit.getText().toString();
                String contact = shopContactNumberEdit.getText().toString();
                String email = shopContactEmailEdit.getText().toString();
                if (!gst.equals("")
                &&!shopName.equals("")
                        &&!category.equals("")
                        &&!contact.equals("")
                        &&!email.equals("")
                        &&count==2)
                {
                    count =0;
                    Intent intent = new Intent(ShopDetails.this,PreviewShopOwner.class);
                    intent.putExtra("aadhar",aadhar);
                    intent.putExtra("names",names);
                    intent.putExtra("locations",locations);
                    intent.putExtra("pincodes",pincodes);
                    intent.putExtra("mobiles",mobiles);
                    intent.putExtra("emailIds",emailIds);
                    intent.putExtra("passwords",passwords);
                    intent.putExtra("re_passwords",re_passwords);

                    //new details
                    intent.putExtra("fileUriFront",fileUriFront.toString());
                    intent.putExtra("fileUriInterior",fileUriInterior.toString());
                    intent.putExtra("shopName",shopName);
                    intent.putExtra("category",category);
                    intent.putExtra("contact",contact);
                    intent.putExtra("email",email);
                    intent.putExtra("gst",gst);
                    startActivity(intent);

                }
                else
                    Toast.makeText(ShopDetails.this, "All Feilds are required", Toast.LENGTH_SHORT).show();

            }
        });
        mStepsView = findViewById(R.id.stepsViewShopDetails);
        String[] steps = {"Owner Details","Shop Details","Preview & Submit"};
        mStepsView.setLabels(steps)
                .setBarColorIndicator(ShopDetails.this.getResources().getColor(R.color.grey))
                .setProgressColorIndicator(ShopDetails.this.getResources().getColor(R.color.yellowGreen))
                .setLabelColorIndicator(ShopDetails.this.getResources().getColor(R.color.black))
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
            fileUriFront = data.getData();
            shopFrontEdit.setText("Image Uploaded Successfully*");

        }
        else if(requestCode == 438 && resultCode == RESULT_OK && data!= null && data.getData()!=null)
        {
            count++;
            fileUriInterior = data.getData();
            shopInteriorEdit.setText("Image Uploaded Successfully*");
        }
    }
}
