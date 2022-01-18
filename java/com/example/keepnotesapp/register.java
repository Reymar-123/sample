package com.example.keepnotesapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class register extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth mAuth;
    private EditText editID, editUsername, editAddress, editEmail, editPassword;
    private ProgressBar progressBar;
    private Button signupbutton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();

        signupbutton = (Button) findViewById(R.id.signupbutton);
        signupbutton.setOnClickListener(this);

        editID = (EditText) findViewById(R.id.regidnumber);
        editUsername = (EditText) findViewById(R.id.regusername);
        editAddress = (EditText) findViewById(R.id.regaddress);
        editEmail = (EditText) findViewById(R.id.regemail);
        editPassword = (EditText) findViewById(R.id.regpassword);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.signupbutton:
                signupbutton();
                break;
        }

    }

    private void signupbutton() {

        String id = editID.getText().toString().trim();
        String username = editUsername.getText().toString().trim();
        String address = editAddress.getText().toString().trim();
        String email = editEmail.getText().toString().trim();
        String password = editPassword.getText().toString().trim();

        if (id.isEmpty()){
            editID.setError("ID Number is required");
            editID.requestFocus();
            return;
        }
        if (username.isEmpty()){
            editUsername.setError("Username is required");
            editUsername.requestFocus();
            return;
        }
        if (address.isEmpty()){
            editAddress.setError("Address is required");
            editAddress.requestFocus();
            return;
        }
        if (email.isEmpty()){
            editEmail.setError("Email is required");
            editEmail.requestFocus();
            return;
        }
        if (password.isEmpty()){
            editPassword.setError("Password is required");
            editPassword.requestFocus();
            return;
        }
        if (password.length()>8){
            editPassword.setError("Password is maximum 8 characters");
            editPassword.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);
        
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()){

                            User user = new User(id, username, address, email);

                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                    if (task.isSuccessful()){
                                        Toast.makeText(register.this, "User has been registered successfully", Toast.LENGTH_LONG).show();
                                        startActivity(new Intent(register.this, MainActivity.class));
                                        progressBar.setVisibility(View.GONE);
                                    }
                                    else{
                                        Toast.makeText(register.this, "Registration Failed", Toast.LENGTH_LONG).show();
                                        progressBar.setVisibility(View.GONE);

                                    }
                                }
                            });

                        }
                        else{

                            Toast.makeText(register.this, "Registration Failed", Toast.LENGTH_LONG).show();
                            progressBar.setVisibility(View.GONE);

                        }


                    }
                });



    }
}