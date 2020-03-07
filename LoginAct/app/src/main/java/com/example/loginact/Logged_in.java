package com.example.loginact;

import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Logged_in extends AppCompatActivity {

    private Button LogoutBtn;
    private Button Home;
    private FirebaseAuth mAuth;
    //private ConstraintLayout constraintLayout;


    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logged_in);

        mAuth = FirebaseAuth.getInstance();
        if(mAuth.getCurrentUser() == null)
        {
            startActivity(new Intent(Logged_in.this,MainActivity.class));
        }

        LogoutBtn = (Button) findViewById(R.id.Logout);
        Home = (Button) findViewById(R.id.home_btn);


        Home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StartHome();
            }
        });
        LogoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startLogOut();

            }
        });
    }

    private void startLogOut()
    {
        FirebaseAuth.getInstance().signOut();
        Toast.makeText(Logged_in.this, "SignInOutSuccess",Toast.LENGTH_LONG).show();
//        constraintLayout = (ConstraintLayout) findViewById(R.id.Snackbar);
//        Snackbar snackbar1 = Snackbar.make(constraintLayout, "Message is restored!", Snackbar.LENGTH_SHORT);
//        snackbar1.show();
            startActivity(new Intent(Logged_in.this, MainActivity.class));

    }

    private void StartHome()
    {
        startActivity(new Intent(Logged_in.this, home_main.class));
    }

}
