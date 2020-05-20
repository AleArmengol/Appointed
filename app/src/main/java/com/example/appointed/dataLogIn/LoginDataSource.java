package com.example.appointed.dataLogIn;

import com.example.appointed.dataLogIn.model.LoggedInUser;

import java.io.IOException;
import java.util.UUID;

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
