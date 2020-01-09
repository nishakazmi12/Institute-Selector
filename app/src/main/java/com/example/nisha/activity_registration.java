package com.example.nisha;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

import java.util.Objects;


public class activity_registration extends AppCompatActivity {

    EditText fname, lname, username, password;
    Button btn_register;
    private FirebaseAuth mFirebaseAuth;
    private ProgressDialog progressDialog;
    FirebaseDatabase Firebasedb ;
    DatabaseReference databaseref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        mFirebaseAuth = FirebaseAuth.getInstance();
        Firebasedb = FirebaseDatabase.getInstance();
        databaseref = Firebasedb.getReference("RegData");
        progressDialog = new ProgressDialog(this);



        fname = findViewById(R.id.fname);
        lname = findViewById(R.id.lname);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        btn_register = findViewById(R.id.btn_register);


        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = username.getText().toString();
                String pwd = password.getText().toString();
                String Fname = fname.getText().toString();
                String Lname = lname.getText().toString();


                if (Fname.isEmpty()) {
                    fname.setError("Please enter first name");
                    fname.requestFocus();
                    return;
                }

                if (Lname.isEmpty()) {
                    lname.setError("Please enter last name");
                    lname.requestFocus();
                    return;
                }


                if (email.isEmpty()) {
                    username.setError(getString(R.string.emailerror));
                    username.requestFocus();
                    return;
                }
                if (pwd.isEmpty()) {
                    password.setError(getString(R.string.pwderror));
                    password.requestFocus();
                    return;
                }




                mFirebaseAuth.createUserWithEmailAndPassword(email, pwd).addOnCompleteListener(activity_registration.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {
                            regdata information = new regdata(
                                    fname.getText().toString(),
                                    lname.getText().toString(),
                                    username.getText().toString()

                            );

                            databaseref.child(mFirebaseAuth.getCurrentUser().getUid()).setValue(information).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {


                                    progressDialog.setMessage("Registering user...");
                                    progressDialog.show();

                                    Toast.makeText(activity_registration.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                                    Intent i = new Intent(getApplicationContext(), login_activity.class);
                                    startActivity(i);

                                }
                            });

                        }


                        else {


                            Toast.makeText(activity_registration.this, "Incorrect Email! Try Again", Toast.LENGTH_SHORT).show();

                        }

                    }

                });

            }

        });
    }
}