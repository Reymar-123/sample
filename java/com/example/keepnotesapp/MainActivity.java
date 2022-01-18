package com.example.keepnotesapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void Register(View view) {
        switch (view.getId()){
            case R.id.registerbutton:
                startActivity(new Intent(this, register.class));
                break;
        }
    }
}