package com.example.loginact;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

public class Main2 extends AppCompatActivity {
    Toolbar toolbar;
    private TabLayout tabLayout;
    private AppBarLayout appBarLayout;
    public ViewPager viewPager;
    private NavigationView navigationView;
    Button bn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        //toolbar =(Toolbar)findViewById(R.id.toolid);
        //  setSupportActionBar(toolbar);
        tabLayout=(TabLayout)findViewById(R.id.tablayout_id);

        viewPager=(ViewPager)findViewById(R.id.pager);
        viewpageradapter adapter =new viewpageradapter(getSupportFragmentManager());
        adapter.AddFragment(new clubs(),"CLUBS");
        adapter.AddFragment(new events(),"EVENTS");
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        switch (item.getItemId())
        {
            case R.id.create:
                startActivity(new Intent(Main2.this,create.class));
                break;
            case R.id.join:
                startActivity(new Intent(Main2.this,ClubList.class));
                break;

        }
        return super.onOptionsItemSelected(item);
    }


}