package com.example.appointed.ui.login;

import android.app.Activity;

import androidx.annotation.NonNull;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appointed.R;
import com.example.appointed.activities.DoctorHome;
import com.example.appointed.activities.PatientHome;
import com.example.appointed.endpoints.DoctorService;
import com.example.appointed.endpoints.PatientService;
import com.example.appointed.models.Doctor;
import com.example.appointed.models.Patient;
import com.example.appointed.network.RetrofitClientInstance;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private LoginViewModel loginViewModel;
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    private CheckBox rememberMeCb;
    Retrofit retrofit;
    Doctor loggedDoctor;
    DoctorService doctorService;
    PatientService patientService;
    Patient loggedPatient;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        //mini branch pull request test
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginViewModel = ViewModelProviders.of(this, new LoginViewModelFactory())
                .get(LoginViewModel.class);

        final EditText usernameEditText = findViewById(R.id.username);
        final EditText passwordEditText = findViewById(R.id.password);
        final Button loginButton = findViewById(R.id.login);
        rememberMeCb =  findViewById(R.id.rememberMeCb);
        loginButton.setText("Log In");

        if(getIntent().getStringExtra("isLoggingOut") != null) {
            if (getIntent().getStringExtra("isLoggingOut").equals("y")) {
                currentUser = null;
                FirebaseAuth.getInstance().signOut();
            }
        }
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            updateUiWithUser(new LoggedInUserView(currentUser.getEmail()));
        }
        final ProgressBar loadingProgressBar = findViewById(R.id.loading);

        loginViewModel.getLoginFormState().observe(this, new Observer<LoginFormState>() {
            @Override
            public void onChanged(@Nullable LoginFormState loginFormState) {
                if (loginFormState == null) {
                    return;
                }
                loginButton.setEnabled(loginFormState.isDataValid());
                if (loginFormState.getUsernameError() != null) {
                    usernameEditText.setError(getString(loginFormState.getUsernameError()));
                }
                if (loginFormState.getPasswordError() != null) {
                    passwordEditText.setError(getString(loginFormState.getPasswordError()));
                }
            }
        });

        loginViewModel.getLoginResult().observe(this, new Observer<LoginResult>() {
            @Override
            public void onChanged(@Nullable LoginResult loginResult) {
                if (loginResult == null) {
                    return;
                }
                loadingProgressBar.setVisibility(View.GONE);
                if (loginResult.getError() != null) {
                    showLoginFailed(loginResult.getError());
                }
                if (loginResult.getSuccess() != null) {
                    updateUiWithUser(loginResult.getSuccess());
                }
                setResult(Activity.RESULT_OK);

                //Complete and destroy login activity once successful
            }
        });

        TextWatcher afterTextChangedListener = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // ignore
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // ignore
            }

            @Override
            public void afterTextChanged(Editable s) {
                loginViewModel.loginDataChanged(usernameEditText.getText().toString(),
                        passwordEditText.getText().toString());
            }
        };
        usernameEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
//                    loginViewModel.login(usernameEditText.getText().toString(),
//                            passwordEditText.getText().toString());
                    loginButton.performClick();
                }
                return false;
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingProgressBar.setVisibility(View.VISIBLE);
                String email = usernameEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(android.os.Debug.isDebuggerConnected()){
                                    android.os.Debug.waitForDebugger();
                                }
                                if(task.isSuccessful()) {
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    currentUser = user;
                                    loginViewModel.login(usernameEditText.getText().toString(), passwordEditText.getText().toString());
                                } else {
                                    loginViewModel.setLoginFormFailed();
                                }
                            }
                        });
            }
        });
    }

    public void onDestroy() {
        super.onDestroy();
        if(!rememberMeCb.isChecked()){
            currentUser = null;
            FirebaseAuth.getInstance().signOut();
        }
    }

    private void updateUiWithUser(LoggedInUserView model) {
        String welcome = getString(R.string.welcome) + currentUser.getEmail();
        // TODO : initiate successful logged in experience
//        Intent welcomeIntent = new Intent(LoginActivity.this, MainActivity.class);
//        welcomeIntent.putExtra("userName", currentUser.getEmail());
//        welcomeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        startActivity(welcomeIntent);
        Toast.makeText(getApplicationContext(), welcome, Toast.LENGTH_LONG).show();


        retrofit = RetrofitClientInstance.getRetrofitInstance();
        doctorService = retrofit.create(DoctorService.class);
        String email = currentUser.getEmail();
        Call<Doctor> callDoctor = doctorService.getDoctor(email);

        callDoctor.enqueue(new Callback<Doctor>() {
            @Override
            public void onResponse(Call<Doctor> call, Response<Doctor> responseDoctor) {
                if(android.os.Debug.isDebuggerConnected()){
                    android.os.Debug.waitForDebugger();
                }
                if(responseDoctor.body() != null){
                    //TODO this is still not working, because a problem in the backend (doctors_controlelr.rb) Syntax Error (Probably git issue)
                    loggedDoctor = responseDoctor.body();
                    Intent doctorHomeIntent = new Intent(LoginActivity.this, DoctorHome.class);
                    doctorHomeIntent.putExtra("loggedDoctor", loggedDoctor);
                    Log.d("set loggedDoctor", loggedDoctor.getEmail());
                    doctorHomeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(doctorHomeIntent);
                } else { //if we didn't find the doctor, we will search for the patient

                    patientService = retrofit.create(PatientService.class);
                    Call<Patient> callPatient = patientService.getPatient(0, currentUser.getEmail());

                    callPatient.enqueue(new Callback<Patient>() {
                        @Override
                        public void onResponse(Call<Patient> call, Response<Patient> responsePatient) {
                            if(android.os.Debug.isDebuggerConnected()){
                                android.os.Debug.waitForDebugger();
                            }
                            loggedPatient = responsePatient.body();
                            Intent patientHomeIntent = new Intent(LoginActivity.this, PatientHome.class);
                            patientHomeIntent.putExtra("loggedPatient", loggedPatient);
                            Log.d("set loggedPatient", loggedPatient.getEmail());
                            patientHomeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(patientHomeIntent);
                            //finish();

                        }
                        @Override
                        public void onFailure(Call<Patient> call, Throwable t) {
                        }
                    });


                }
            }

            @Override
            public void onFailure(Call<Doctor> call, Throwable t) {

            }
        });

    }

    private void showLoginFailed(@StringRes Integer errorString) {
        Toast.makeText(getApplicationContext(), errorString, Toast.LENGTH_SHORT).show();
    }

}
