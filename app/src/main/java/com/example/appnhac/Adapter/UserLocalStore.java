package com.example.appnhac.Adapter;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.appnhac.Model.User;

public class UserLocalStore {

    public static final String SP_NAME = "userDetails";

    SharedPreferences userLocalDatabase;

    //Khởi tạo
    public UserLocalStore(Context context) {
        userLocalDatabase = context.getSharedPreferences(SP_NAME, 0);
    }

    public void storeUserData(User user) {
        SharedPreferences.Editor userLocalDatabaseEditor = userLocalDatabase.edit();
        userLocalDatabaseEditor.putString("email", user.email);
        userLocalDatabaseEditor.putString("username", user.username);
        userLocalDatabaseEditor.putString("password", user.password);
        userLocalDatabaseEditor.commit();
    }

    public void setUserLoggedIn(boolean loggedIn) {
        SharedPreferences.Editor userLocalDatabaseEditor = userLocalDatabase.edit();
        userLocalDatabaseEditor.putBoolean("loggedIn", loggedIn);
        userLocalDatabaseEditor.commit();
    }

    public void clearUserData() {
        SharedPreferences.Editor userLocalDatabaseEditor = userLocalDatabase.edit();
        userLocalDatabaseEditor.clear();
        userLocalDatabaseEditor.commit();
    }

    //Lấy giá trị userLocalDatabase
    public User getLoggedInUser() {
        if (userLocalDatabase.getBoolean("loggedIn", false) == false) {
            return null;
        }

        String email = userLocalDatabase.getString("email", "");
        String username = userLocalDatabase.getString("username", "");
        String password = userLocalDatabase.getString("password", "");

        User user = new User(email, username, password);
        return user;
    }
}
