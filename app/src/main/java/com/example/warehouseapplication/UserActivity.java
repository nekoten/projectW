package com.example.warehouseapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class UserActivity extends AppCompatActivity {

    TextView welcomeuser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        Intent intent = getIntent();
        String user = intent.getStringExtra("username");
        welcomeuser = findViewById(R.id.welcomeUser);
        welcomeuser.setText("ชื่อผู้ใช้: " + user);
    }
}
