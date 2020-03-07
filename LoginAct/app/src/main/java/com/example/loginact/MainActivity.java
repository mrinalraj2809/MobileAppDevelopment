package com.example.loginact;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {



    private EditText EmailField;
    private EditText PassField;

    private Button Loginbtn;
    private int flag = 0;

    private Button SignupBtn;

    private TextView ForgotPass;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    private LinearLayout constraintLayout;

    private String m_Text = "";
// ...
// Initialize Firebase Auth




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        View decorView = getWindow().getDecorView();
// Hide the status bar.
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        //hide action bar

        //end hiding status & action bar


        mAuth = FirebaseAuth.getInstance();


        EmailField = (EditText) findViewById(R.id.email);
        PassField = (EditText) findViewById(R.id.pass);
        Loginbtn = (Button) findViewById(R.id.login);
        SignupBtn = (Button) findViewById(R.id.button_Signup_login);
        ForgotPass = (TextView) findViewById(R.id.ForgotPasswordText);


        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                if(firebaseAuth.getCurrentUser() != null){


                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    if(user.isEmailVerified()) {
                        FirebaseDatabase.getInstance().getReference("users_profile")
                                .child(FirebaseAuth.getInstance().getCurrentUser().getEmail().replace(".",","))
                                .addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        String Profile_Flag = dataSnapshot.child("flag").getValue(String.class);
                                        if(Profile_Flag.equals("0"))
                                            startActivity(new Intent(MainActivity.this,Finish_Profile.class));
                                        else
                                            startActivity(new Intent(MainActivity.this,Logged_in.class));
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });
//                        startActivity(new Intent(MainActivity.this, Logged_in.class));
                    }
                }
            }
        };

        ForgotPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Enter Your Email");

                final EditText input = new EditText(MainActivity.this);

                input.setInputType(InputType.TYPE_CLASS_TEXT);
                input.setPadding(60,19,19,30);
                builder.setView(input);


                builder.setPositiveButton("Send Password Reset Link", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        m_Text = input.getText().toString();
                        FirebaseAuth.getInstance().sendPasswordResetEmail(m_Text)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
//                                            Toast.makeText(MainActivity.this,"Email Sent with Password Reset Link",Toast.LENGTH_SHORT).show();

                                            constraintLayout = (LinearLayout) findViewById(R.id.Snackbar2);
                                            Snackbar snackbar1 = Snackbar.make(constraintLayout, "Email Sent with Password Reset Link", Snackbar.LENGTH_LONG);
                                            snackbar1.show();
                                        }
                                    }
                                });
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                builder.show();
            }
        });

        Loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startSignin();

            }
        });

        SignupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,SignUp.class));
            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser currentUser = mAuth.getCurrentUser();
        mAuth.addAuthStateListener(mAuthListener);
    }



    private void startSignin()
    {
        String emailid = EmailField.getText().toString();
        String password = PassField.getText().toString();


        if(emailid.isEmpty())
        {
            EmailField.setError("Email is Empty");
            EmailField.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(emailid).matches())
        {
            EmailField.setError("Enter a valid EMail");
            EmailField.requestFocus();
            return;
        }
        if(password.isEmpty())
        {
            PassField.setError("Password is Empty");
            PassField.requestFocus();
            return;
        }

        else{
            mAuth.signInWithEmailAndPassword(emailid,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if(!task.isSuccessful())
                    {

                        Toast.makeText(MainActivity.this, "Problem With Sign In",Toast.LENGTH_LONG).show();
                    }
                    else {
                        checkIfEmailVerified();
                    }
                }
            });
        }
    }

    private void checkIfEmailVerified()
    {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user.isEmailVerified())
        {
            // user is verified
            flag = 1;

            Toast.makeText(MainActivity.this, "Successfully logged in", Toast.LENGTH_SHORT).show();
        }
        else
        {
            // email is not verified
            FirebaseAuth.getInstance().signOut();


//            Toast.makeText(MainActivity.this, "Verify your Email", Toast.LENGTH_SHORT).show();

            constraintLayout = (LinearLayout) findViewById(R.id.Snackbar2);
            Snackbar snackbar1 = Snackbar.make(constraintLayout, "Verify your Email", Snackbar.LENGTH_LONG);
            snackbar1.show();

        }
    }
}
