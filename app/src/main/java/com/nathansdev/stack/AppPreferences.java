package com.nathansdev.stack;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * Application preferences backed by shared preference.
 */
public class AppPreferences {
    private static final String PREFS_NAME = "app-prefs";
    private final SharedPreferences prefs;

    public AppPreferences(Application app) {
        this.prefs = app.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }
}
