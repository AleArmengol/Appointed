package com.example.appointed.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.appointed.R;

public class ConfirmationActivity extends AppCompatActivity {

    private TextView name;
    private TextView address;
    private TextView number;
    private TextView email;
    private Button cancel_button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmation);

        name = (TextView)findViewById(R.id.textNameConfirmation);
        //name.setText("1");

        address = (TextView)findViewById(R.id.textAddressConfirmation);
        //address.setText("2");

        number = (TextView)findViewById(R.id.textNumberConfirmation);
        //number.setText("3");

        email = (TextView)findViewById(R.id.textEmailConfirmation);
        //email.setText("4");

        cancel_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });











    }
}
