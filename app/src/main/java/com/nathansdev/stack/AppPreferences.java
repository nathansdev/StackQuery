package com.nathansdev.stack;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * Application preferences backed by shared preference.
 */
public class AppPreferences {
    private static final String PREFS_NAME = "app-prefs";
    private static final String IS_LOGGEDIN = "isLoggedIn";
    private static final String ACCESS_TOKEN = "accessToken";
    private final SharedPreferences prefs;

    public AppPreferences(Application app) {
        this.prefs = app.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    public boolean isLoggedIn() {
        return prefs.getBoolean(IS_LOGGEDIN, Boolean.FALSE);
    }

    public void setIsLoggedin(boolean isLoggedin) {
        prefs.edit().putBoolean(IS_LOGGEDIN, isLoggedin).apply();
    }

    public String getAccessToken() {
        return prefs.getString(ACCESS_TOKEN, "");
    }

    public void setAccessToken(String token) {
        prefs.edit().putString(ACCESS_TOKEN, token).apply();
    }
}
