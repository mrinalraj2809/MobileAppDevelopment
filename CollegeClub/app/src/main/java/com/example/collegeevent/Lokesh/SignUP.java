package com.example.collegeevent.Lokesh;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewStub;
import android.widget.*;

import com.example.collegeevent.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUP extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    TextInputEditText txtnmStudent,txtusnStudent,txtpnoStudent,txtsemStudent,txtdepStudent,txtemailStudent,txtpwdStudent,txtcnfpwdStudent;
    TextInputEditText signinnameTeacher,signinUniqueIdTeacher,signinpnoTeacher,signInSpecialisationTeacher,signinbranchTeacher,signinDesignationTeacher,signinemailTeacher,signinpwdTeacher,signinpwdcnfrmTeacher;
    Button requestPermissionTeacher;
    Button requestPermissionAdmin;
    Button btnRegisterStudent;
    ViewStub stubStudent;
    ViewStub stubTeacher;
    ViewStub stubAdmin;
    ViewStub stubSuperAdmin;
    private FirebaseAuth mAuth;
    DatabaseReference dbref;
    //long maxid=111;
    Spinner spinnerUserType;
    MemberStudent memberStudent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        spinnerUserType = findViewById(R.id.spinnerUserType);
        stubStudent = (ViewStub) findViewById(R.id.layout_stub_student);
        stubTeacher = (ViewStub) findViewById(R.id.layout_stub_teacher);
        stubAdmin = (ViewStub) findViewById(R.id.layout_stub_admin);
        //stubSuperAdmin = (ViewStub) findViewById(R.id.layout_stub_super_admin);

        mAuth= FirebaseAuth.getInstance();
        dbref = FirebaseDatabase.getInstance().getReference();
        memberStudent= new MemberStudent();
//        To be deleted later
//        dbref= FirebaseDatabase.getInstance().getReference().child("Student");
//        dbref.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                if(dataSnapshot.exists())
//                {
//                    maxid= dataSnapshot.getChildrenCount();
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
        ArrayAdapter<CharSequence> arrayAdapter = ArrayAdapter.createFromResource(this,R.array.user_type,android.R.layout.simple_spinner_dropdown_item);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerUserType.setAdapter(arrayAdapter);
        spinnerUserType.setOnItemSelectedListener(this);

    }
    public void studentRegister(View view)
    {

    }
    @Override
    public void onItemSelected(AdapterView<?> parent,View view, int position, long id) {
        String userType = parent.getItemAtPosition(position).toString();
        if(position == 0) {
            stubStudent.setVisibility(View.GONE);// Safe way to use is VISIBLE and GONE.
            stubTeacher.setVisibility(View.GONE);
            stubAdmin.setVisibility(View.GONE);
            //stubSuperAdmin.setVisibility(View.GONE);
        }
        else if (position == 1) {
            stubStudent.setVisibility(View.GONE);// Safe way to use is VISIBLE and GONE.
            stubTeacher.setVisibility(View.GONE);
            stubAdmin.setVisibility(View.GONE);
//            stubSuperAdmin.setVisibility(View.GONE);
            stubStudent.setLayoutResource(R.layout.student_sign_up_form);
            stubStudent.setVisibility(View.VISIBLE);// Or else call using inflateId after inflate is called once.
            txtnmStudent= findViewById(R.id.signinnameStudent);
            txtusnStudent= findViewById(R.id.signinusnStudent);
            txtpnoStudent= findViewById(R.id.signinpnoStudent);
            txtsemStudent= findViewById(R.id.signinsemStudent);
            txtdepStudent= findViewById(R.id.signinbranchStudent);
            txtemailStudent= findViewById(R.id.signinemailStudent);
            txtpwdStudent= findViewById(R.id.signinpwdStudent);
            txtcnfpwdStudent= findViewById(R.id.signinpwdcnfrmStudent);
            btnRegisterStudent = findViewById(R.id.signinbtnStudent);
            btnRegisterStudent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final String name=txtnmStudent.getText().toString();
                    final String usn= txtusnStudent.getText().toString();
                    final String pno= txtpnoStudent.getText().toString();
                    final String sem= txtsemStudent.getText().toString();
                    final String branch= txtdepStudent.getText().toString();
                    final String email= txtemailStudent.getText().toString();
                    final String pwd= txtpwdStudent.getText().toString();
                    final String cnfpwd= txtcnfpwdStudent.getText().toString();
                    if(!name.isEmpty() && !usn.isEmpty() && !pno.isEmpty() &&
                            !sem.isEmpty() && !branch.isEmpty() &&
                            !email.isEmpty() && !pwd.isEmpty() && !cnfpwd.isEmpty() && pwd.equals(cnfpwd)) {
                        if (cnfpwd.equals(pwd)) {
                            mAuth.createUserWithEmailAndPassword(email, pwd)
                                    .addOnCompleteListener(SignUP.this, new OnCompleteListener<AuthResult>() {
                                        @Override
                                        public void onComplete(@NonNull Task<AuthResult> task) {
                                            if (task.isSuccessful()) {
                                                memberStudent.setStudent_name(name);
                                                memberStudent.setStudent_USN(usn);
                                                memberStudent.setStudent_phone_number(pno);
                                                memberStudent.setStudent_semester(sem);
                                                memberStudent.setStudent_branch(branch);
                                                memberStudent.setStudent_email(email);
                                                memberStudent.setStudent_user_Id(mAuth.getUid());
                                                dbref.child("Student").child(mAuth.getUid()).setValue(memberStudent);
                                                Toast.makeText(SignUP.this, "Data Inserted Successfully!!!", Toast.LENGTH_SHORT).show();
                                                startActivity(new Intent(getApplicationContext(), MainActivityLokesh.class));
                                            } else {

                                                Toast.makeText(SignUP.this, "Authentication failed.", Toast.LENGTH_SHORT).show();

                                            }

                                        }
                                    });
                        } else {
                            Toast.makeText(SignUP.this, "Password Not Confirmed", Toast.LENGTH_SHORT).show();
                        }
                    }
                            else {
                            Snackbar.make(v, "All fields are required.", Snackbar.LENGTH_LONG).show();
                        }

                }
            });

        }
        else if (position == 2){
            stubStudent.setVisibility(View.GONE);// Safe way to use is VISIBLE and GONE.
            stubTeacher.setVisibility(View.GONE);
            stubAdmin.setVisibility(View.GONE);
            //stubSuperAdmin.setVisibility(View.GONE);
            stubTeacher.setLayoutResource(R.layout.teacher_sign_up_form);stubTeacher.setVisibility(View.VISIBLE);

            signinnameTeacher = findViewById(R.id.signinnameTeacher);
            signinUniqueIdTeacher = findViewById(R.id.signinUniqueIdTeacher);
            signinpnoTeacher = findViewById(R.id.signinpnoTeacher);
            signInSpecialisationTeacher = findViewById(R.id.signInSpecialisationTeacher);
            signinbranchTeacher = findViewById(R.id.signinbranchTeacher);
            signinDesignationTeacher = findViewById(R.id.signinDesignationTeacher);
            signinemailTeacher = findViewById(R.id.signinemailTeacher);
            signinpwdcnfrmTeacher = findViewById(R.id.signinpwdcnfrmTeacher);
            signinpwdTeacher = findViewById(R.id.signinpwdTeacher);
            requestPermissionTeacher = stubTeacher.findViewById(R.id.requestPermissionButtonTeacher);
            requestPermissionTeacher.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(SignUP.this,Permission.class));
                }
            });

        }
        else if (position == 3){
            stubStudent.setVisibility(View.GONE);// Safe way to use is VISIBLE and GONE.
            stubTeacher.setVisibility(View.GONE);
            stubAdmin.setVisibility(View.GONE);
            //stubSuperAdmin.setVisibility(View.GONE);
            stubAdmin.setLayoutResource(R.layout.admin_sign_up_form);stubAdmin.setVisibility(View.VISIBLE);
            requestPermissionAdmin =  stubAdmin.findViewById(R.id.requestPermissionButtonAdmin);
        }
        else if (position == 4){
            stubStudent.setVisibility(View.GONE);// Safe way to use is VISIBLE and GONE.
            stubTeacher.setVisibility(View.GONE);
            stubAdmin.setVisibility(View.GONE);
            //stubSuperAdmin.setVisibility(View.GONE);
            stubStudent.setLayoutResource(R.layout.student_sign_up_form);stubStudent.setVisibility(View.VISIBLE);
        }

    }
//    ViewStub v =((ViewStub)findViewById(R.id.message_layout)).setOnInflateListener(new ViewStub.OnInflateListener() {
//        @Override
//        public void onInflate(ViewStub stub, View inflated) {
//            Button register = inflated.findViewById(R.id.signinbtn);
//        }
//    });

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
