package com.example.nisha;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;


public class mainpage_activity extends AppCompatActivity {

    ImageView all, tech, med, arts, business, law;
    Button btn_logout;
    FirebaseAuth  mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;






    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainpage);


        tech = findViewById(R.id.tech);
        med = findViewById(R.id.med);
        arts = findViewById(R.id.arts);
        business = findViewById(R.id.business);
        law = findViewById(R.id.law);
        all = findViewById(R.id.all);
        btn_logout = findViewById(R.id.btn_logout);
        mFirebaseAuth = FirebaseAuth.getInstance();



        tech.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i;
                i = new Intent(mainpage_activity.this, tech_activity.class);
                startActivity(i);

            }
        });

        med.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i;
                i = new Intent(mainpage_activity.this, med_activity.class);
                startActivity(i);

            }
        });


        arts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i;
                i = new Intent(mainpage_activity.this, arts_activity.class);
                startActivity(i);

            }
        });



        business.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i;
                i = new Intent(mainpage_activity.this, business_activity.class);
                startActivity(i);

            }
        });


        law.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i;
                i = new Intent(mainpage_activity.this, law_activity.class);
                startActivity(i);

            }
        });

        all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i;
                i = new Intent(mainpage_activity.this, all_activity.class);
                startActivity(i);
            }
        });


        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFirebaseAuth.signOut();
                Intent i = new Intent(mainpage_activity.this, login_activity.class);
                i.putExtra("finish", true); // if you are checking for this in your other Activities
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                        Intent.FLAG_ACTIVITY_CLEAR_TASK |
                        Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
                finish();
            }
        });



    }
}
