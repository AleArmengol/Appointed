package com.example.appointed.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.TextView;

import com.example.appointed.R;
import com.example.appointed.models.Patient;
import com.google.android.material.navigation.NavigationView;

import java.util.logging.Logger;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class PatientHome extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private Patient loggedPatient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        Log.d("TEST", "ONCREATEEEEEEEEE");
        setContentView(R.layout.activity_patient_home);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.

        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_new_appointment, R.id.nav_my_user)
                .setDrawerLayout(drawer)
                .build();
        loggedPatient = (Patient) getIntent().getSerializableExtra("loggedPatient");
        TextView namePatient = (TextView) navigationView.getHeaderView(0).findViewById(R.id.nameText);
        TextView emailPatient = (TextView) navigationView.getHeaderView(0).findViewById(R.id.emailText);
        namePatient.setText(loggedPatient.getName() + " " + loggedPatient.getLast_name());
        emailPatient.setText(loggedPatient.getEmail());
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
//        Log.d("TEST", "CREATE OPTIOOOOOOOOONS");
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
//        Log.d("TEST", "navigateUUUUUUUUUUP");
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}
