package com.example.collegeevent.Lokesh;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.collegeevent.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    Button btn;
    EditText idedittext,pasSedittext;
    TextView signtext, forgotPassword;
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    ProgressDialog loadingBar;

    // Redirects user to Home Page if user is already logged in.
    @Override
    protected void onStart() {
        super.onStart();
        if(currentUser != null)
        {
            startActivity(new Intent(this,HomeActivity.class));
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loadingBar = new ProgressDialog(this);
        idedittext=findViewById(R.id.emailid);
        pasSedittext=findViewById(R.id.password);
        signtext= findViewById(R.id.signintext);
        btn= findViewById(R.id.btnLogin);
        forgotPassword = (TextView)findViewById(R.id.forgotPassword);
        mAuth= FirebaseAuth.getInstance();
        // to get current user
        currentUser = mAuth.getCurrentUser();
        signtext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,SignUP.class));
            }
        });
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String uName = idedittext.getText().toString();
                final String pass = pasSedittext.getText().toString();
                if(uName.equals(""))
                {
                    Toast.makeText(LoginActivity.this, "Email ID space is empty!!!", Toast.LENGTH_SHORT).show();
                    idedittext.requestFocus();
                }
                else if(pass.equals(""))
                {
                    Toast.makeText(LoginActivity.this, "Password space is empty!!!", Toast.LENGTH_SHORT).show();
                    pasSedittext.requestFocus();
                }
                else if(uName.equals("reachcclub@gmail.com") && pass.equals("re@chTCCO2020:D"))
                {
                    startActivity(new Intent(getApplicationContext(),superAdmin.class));
                }
                else
                {
                    loadingBar.setTitle("Logging in");
                    loadingBar.setMessage("Please wait, while we are verifying the account...");
                    loadingBar.setCanceledOnTouchOutside(true);
                    loadingBar.show();
                    mAuth.signInWithEmailAndPassword(uName,pass)
                            .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // Sign in success, update UI with the signed-in user's information
                                        loadingBar.dismiss();
                                        startActivity(new Intent(getApplicationContext(),HomeActivity.class));
                                        //updateUI(user);
                                    } else {
                                        loadingBar.dismiss();
                                        Toast.makeText(getApplicationContext(), "Login Failed", Toast.LENGTH_SHORT).show();
                                    }

                                    // ...
                                }
                            });
                }




            }
        });
    }
}
