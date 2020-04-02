package com.example.collegeevent.Lokesh;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.collegeevent.Permission;
import com.example.collegeevent.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class Apply_Permission extends AppCompatActivity {
    EditText txtgrpnm,txtgrpdesc,txtgrptype,txtpersonname;
    TextView txtfilename;
    Button btnupload,btnapply;
    ProgressDialog progressDialog;
    Uri pdfUri;
    int maxid=0;
    com.example.collegeevent.Permission permission;
    FirebaseStorage firebaseStorage;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference dbref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apply__permission);
        txtgrpdesc= findViewById(R.id.groupdesc);
        txtpersonname= findViewById(R.id.personname);
        txtfilename= findViewById(R.id.permissiontext);
        txtgrpnm= findViewById(R.id.groupname);
        txtgrptype= findViewById(R.id.typeOfgroup);
        btnapply= findViewById(R.id.apply);
        btnupload= findViewById(R.id.uploadpdf);
        permission= new Permission();
        firebaseStorage = FirebaseStorage.getInstance();
        firebaseDatabase= FirebaseDatabase.getInstance();
        btnupload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED)
                {
                    selectPdf();
                }
                else
                {
                    ActivityCompat.requestPermissions(Apply_Permission.this,new  String[]{Manifest.permission.READ_EXTERNAL_STORAGE},9);
                }
            }
        });

        btnapply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(pdfUri!=null)
                {
                    uploadfile(pdfUri);
                    save_details();
                }
                else
                {
                    Toast.makeText(Apply_Permission.this, "Select a file!!!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void save_details() {
        dbref= FirebaseDatabase.getInstance().getReference().child("Admin");

    }

    private void uploadfile(Uri pdfUri) {
        progressDialog= new ProgressDialog(this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setTitle("Uploading file...");
        progressDialog.setProgress(0);
        progressDialog.show();
        final String name= System.currentTimeMillis()+"";
        StorageReference storageReference= firebaseStorage.getReference();
        storageReference.child("UploadedPDF").child(name).putFile(pdfUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                final String url= taskSnapshot.getStorage().getDownloadUrl().toString();
                final String groupname=txtgrpnm.getText().toString();
                final String person= txtpersonname.getText().toString();
                final String groupdesc= txtgrpdesc.getText().toString();
                final String grouptype= txtgrptype.getText().toString();
                final DatabaseReference reference= firebaseDatabase.getReference();
                reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.exists())
                        {
                            maxid=(int) dataSnapshot.getChildrenCount();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                reference.child("Admin").child("admin"+String.valueOf(maxid+1)).setValue(url).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful())
                        {
                            permission.setName(person);
                            permission.setGroup(groupname);
                            permission.setDesc(groupdesc);
                            permission.setType(grouptype);
                            permission.setPdf(url);
                            reference.child("Admin").child("admin"+String.valueOf(maxid)).setValue(permission);

                            Toast.makeText(Apply_Permission.this, "File Successfully Uploaded", Toast.LENGTH_SHORT).show();
                            txtgrpnm.setText("");
                            txtfilename.setText("");
                            txtgrpdesc.setText("");
                            txtgrptype.setText("");
                        }
                        else
                        {
                            Toast.makeText(Apply_Permission.this, "File not uploaded", Toast.LENGTH_SHORT).show();
                        }

                    }
                });

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                int currentProgress=   (int)(100*taskSnapshot.getBytesTransferred()/taskSnapshot.getTotalByteCount());
                progressDialog.setProgress(currentProgress);
            }
        });
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if(requestCode==9 && grantResults[0]==PackageManager.PERMISSION_GRANTED)
        {
            selectPdf();
        }
        else
        {
            Toast.makeText(this, "Please provide permission!!!", Toast.LENGTH_SHORT).show();
        }

        //        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private void selectPdf() {
        Intent i= new Intent();
        i.setType("application/pdf");
        i.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(i,86);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if(requestCode==86 && resultCode== RESULT_OK && data!=null)
        {
            pdfUri= data.getData();
            txtfilename.setText(""+data.getData().getLastPathSegment());
            Toast.makeText(this, "File Selected Successfully", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(this, "Please Select a file!!!", Toast.LENGTH_SHORT).show();
        }
    }
}
