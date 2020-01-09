package com.example.nisha;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class law_activity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    Toolbar toolbar;
    FirebaseAuth  mFirebaseAuth;
    private DrawerLayout drawer;



    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        mFirebaseAuth = FirebaseAuth.getInstance();

        switch (menuItem.getItemId()){
            case R.id.domainpage:
                Intent i;
                i = new Intent(law_activity.this, mainpage_activity.class);
                i.putExtra("finish", true); // if you are checking for this in your other Activities
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                        Intent.FLAG_ACTIVITY_CLEAR_TASK |
                        Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
                break;
            case R.id.names:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new law_name_frag()).commit();
                break;
                case R.id.rates:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new law_rank_frag()).commit();
                break;
            case R.id.budget:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new law_budget_frag()).commit();
                break;
            case R.id.combine:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new law_combine_frag()).commit();
                break;
            case R.id.feedback:
                Toast.makeText(this, "feedback", Toast.LENGTH_SHORT).show();
                break;
            case R.id.logout:
                mFirebaseAuth.signOut();
                Intent intent;
                intent = new Intent(law_activity.this, login_activity.class);
                intent.putExtra("finish", true); // if you are checking for this in your other Activities
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                        Intent.FLAG_ACTIVITY_CLEAR_TASK |
                        Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
                break;

        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_law);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(true);
        toolbar.setTitle("Law");

        drawer = findViewById(R.id.law_drawer_layout);
        NavigationView navigationView = findViewById(R.id.law_nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(law_activity.this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new law_name_frag()).commit();
        navigationView.setCheckedItem(R.id.names);



    }


    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


}