package com.example.nisha;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


@SuppressLint("Registered")
public class login_activity extends AppCompatActivity {


    EditText username, password;
    Button btn_login, btn_reg;
    FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        setContentView(R.layout.activity_login);


        mFirebaseAuth = FirebaseAuth.getInstance();
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        btn_login = findViewById(R.id.btn_login);
        btn_reg = findViewById(R.id.btn_reg);
        progressDialog = new ProgressDialog(this);


        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser mFirebaseUser = mFirebaseAuth.getCurrentUser();

                if (mFirebaseUser != null ) {

                    progressDialog.setMessage("Logging Im...");
                    progressDialog.show();
                    Toast.makeText(login_activity.this, "You Have Successfully Logged In", Toast.LENGTH_LONG).show();
                    Intent i = new Intent(login_activity.this, mainpage_activity.class);
                    startActivity(i);
                }

                else {

                    Toast.makeText(login_activity.this, "Please Login", Toast.LENGTH_SHORT).show();

                }

            }
        };

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String email = username.getText().toString();
                String pwd = password.getText().toString();
                if (email.isEmpty()){
                    username.setError(getString(R.string.emailerror));
                    username.requestFocus();
                }

                else if(pwd.isEmpty()){
                    password.setError(getString(R.string.pwderror));
                    password.requestFocus();
                }

                else if (!(email.isEmpty() && pwd.isEmpty())){
                    mFirebaseAuth.signInWithEmailAndPassword(email, pwd).addOnCompleteListener(login_activity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (!task.isSuccessful()){
                                Toast.makeText(login_activity.this, "Incorrect email or password! Try Again", Toast.LENGTH_SHORT).show();

                            }
                            else {
                                Intent i = new Intent(login_activity.this, mainpage_activity.class);
                                startActivity(i);

                            }

                        }
                    });

                }

                else {
                    Toast.makeText(login_activity.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
                }

            }
        });



        btn_reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent (login_activity.this, activity_registration.class);
                startActivity(i);
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        mFirebaseAuth.addAuthStateListener(mAuthStateListener);
    }


}
