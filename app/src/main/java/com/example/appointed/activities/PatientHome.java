package com.example.appointed.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.appointed.R;
import com.example.appointed.models.Patient;
import com.example.appointed.ui.login.LoginActivity;
import com.google.android.material.navigation.NavigationView;

import java.util.logging.Logger;

import androidx.appcompat.app.AlertDialog;
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
                R.id.nav_home, R.id.nav_new_appointment, R.id.nav_my_user, R.id.nav_log_out)
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
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final AlertDialog.Builder payment = new AlertDialog.Builder(this);

        navigationView.getMenu().findItem(R.id.nav_log_out).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {

                builder.setCancelable(true);
                builder.setTitle("Atención");
                builder.setMessage("Realmente desea cerrar sesión ?");
                builder.setPositiveButton("Confirmar",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent logInIntent = new Intent(PatientHome.this, LoginActivity.class);
                                logInIntent.putExtra("isLoggingOut", "y");
                                logInIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(logInIntent);
                                finish();
                            }
                        });
                builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
                return true;
            }
        });

        navigationView.getMenu().findItem(R.id.nav_new_appointment).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                if(loggedPatient.isPayment_uptodate()) {
                    Intent specialityActivity = new Intent(PatientHome.this, SpecialityActivity.class);
                    specialityActivity.putExtra("loggedPatient", loggedPatient);
                    //patientHomeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(specialityActivity);
                }
                else{
                    payment.setCancelable(true);
                    payment.setTitle("ERROR");
                    payment.setMessage("Usted no podrá sacar un turno hasta que se encuentre al día con los pagos");
//                    LayoutInflater image = LayoutInflater.from(getActivity());
//                    final View view = image.inflate(R.layout.error_pop_up, null);
                    payment.setPositiveButton("ACEPTAR", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });
//                    payment.setView(view);
                    AlertDialog dialog = payment.create();
                    dialog.show();
                }
                return false;
            }
        });

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
