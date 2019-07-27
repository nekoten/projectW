package com.example.warehouseapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class CheckOrder  extends AppCompatActivity implements View.OnClickListener {
    private Button textstatus;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_check);
        super.onCreate(savedInstanceState);

        setid();

    }   //onCreate

    private void setid() {

        textstatus = (Button) findViewById(R.id.textstatus);

        textstatus.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {

        int colorres = 0;

        if (view == textstatus) {

            colorres = R.color.colorPrimary;

        } else {

        }

    }
}   //Main Class
