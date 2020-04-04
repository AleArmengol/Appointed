package com.example.appointed;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    Button btnHello;
    TextView txtHello;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtHello = (TextView) findViewById(R.id.txtHello);
        btnHello = (Button) findViewById(R.id.btnHello);

        btnHello.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtHello.setText("Apretaste el boton!");
            }
        });


    }


}
