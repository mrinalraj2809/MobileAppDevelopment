package com.example.loginact;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class SignUp extends AppCompatActivity {



    private EditText EmailField_s;
    private EditText PassField_s;
    private EditText NameField;
    private EditText PhoneNumberField;

    private TextView AlreadyMember;

    private Button Signupbtn;

    private LinearLayout constraintLayout;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mAuth = FirebaseAuth.getInstance();

        EmailField_s = (EditText) findViewById(R.id.editText_email);
        PassField_s = (EditText) findViewById(R.id.editText_Password);
        NameField = (EditText) findViewById(R.id.editText_Name);
        PhoneNumberField = (EditText) findViewById(R.id.editText_PhoneNumber);

        Signupbtn = (Button) findViewById(R.id.button_signUp);

        AlreadyMember = (TextView) findViewById(R.id.AlreadyAccountText);

        AlreadyMember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignUp.this,MainActivity.class));
            }
        });

        Signupbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StartSignup();

            }
        });

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    // NOTE: this Activity should get open only when the user is not signed in, otherwise
                    // the user will receive another verification email.
                    sendVerificationEmail();
                }
                // ...
            }
        };
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }

    @Override
    protected void onStart() {
        super.onStart();

        mAuth.addAuthStateListener(mAuthListener);
    }

    private void sendVerificationEmail()
    {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        user.sendEmailVerification()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            // email sent

                            Toast.makeText(SignUp.this, "Email Verification sent",Toast.LENGTH_LONG).show();
                            // after email is sent, logout the user
                            FirebaseAuth.getInstance().signOut();
                            startActivity(new Intent(SignUp.this, MainActivity.class));
                            finish();
                        }
                        else
                        {
                            // email not sent, so display message and restart the activity or do whatever you wish to do

                            //restart this activity
                            overridePendingTransition(0, 0);
                            finish();
                            overridePendingTransition(0, 0);
                            startActivity(getIntent());

                        }
                    }
                });
    }

    private void StartSignup()
    {
        final String email = EmailField_s.getText().toString();
        String pass = PassField_s.getText().toString();
        final String  phone_no = PhoneNumberField.getText().toString();
        final String name = NameField.getText().toString();

        if(name.isEmpty())
        {
            NameField.setError("Name is Required");
            NameField.requestFocus();
            return;
        }
        if(email.isEmpty())
        {
            EmailField_s.setError("Email is Required");
            EmailField_s.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            EmailField_s.setError("Enter a valid mail");
            EmailField_s.requestFocus();
            return;
        }
        if(phone_no.isEmpty())
        {
            PhoneNumberField.setError("Phone Number is Required");
            PhoneNumberField.requestFocus();
            return;
        }
        if(phone_no.length()!=10)
        {
            PhoneNumberField.setError("Enter a valid Phone number");
            PhoneNumberField.requestFocus();
            return;
        }
        if(pass.isEmpty())
        {
            PassField_s.setError("Password is Required");
            PassField_s.requestFocus();
            return;
        }
        if(pass.length()<6)
        {
            PassField_s.setError("Minimum Length of Password Should be 6");
            PassField_s.requestFocus();
            return;
        }


        mAuth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {

                    User_Profile userp = new User_Profile();
                    User user = new User(
                            email,
                            name,
                            phone_no

                    );
                    FirebaseDatabase.getInstance().getReference("users")
                            .child(email.replace(".",","))
                            .setValue(user);
                    FirebaseDatabase.getInstance().getReference("users_profile")
                            .child(email.replace(".",","))
                            .setValue(userp);


                    Toast.makeText(SignUp.this, "User Registered Successfully",Toast.LENGTH_LONG).show();
                }
                else
                {
                    if (task.getException() instanceof FirebaseAuthUserCollisionException) {
//                        Toast.makeText(SignUp.this,
//                                "User with this email already exist.", Toast.LENGTH_SHORT).show();
                                constraintLayout = (LinearLayout) findViewById(R.id.Snackbar);
        Snackbar snackbar1 = Snackbar.make(constraintLayout, "User with this email already exist.", Snackbar.LENGTH_LONG);
        snackbar1.show();
                    }
                    else
                    Toast.makeText(SignUp.this, "Some Error Occured",Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
