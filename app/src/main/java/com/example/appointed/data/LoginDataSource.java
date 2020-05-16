package com.example.appointed.data;

import android.util.Log;
import android.widget.Toast;

import com.example.appointed.data.model.LoggedInUser;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.Executor;

import androidx.annotation.NonNull;

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
public class LoginDataSource {

    public Result<LoggedInUser> login(final String username, String password) {
        try {
            // TODO: handle loggedInUser
            LoggedInUser user = new LoggedInUser(UUID.randomUUID().toString(), username);
            return new Result.Success<>(user);

        } catch (Exception e) {
            return new Result.Error(new IOException("Error logging in", e));
        }
    }

    public void logout() {
        // TODO: revoke authentication
    }
}
