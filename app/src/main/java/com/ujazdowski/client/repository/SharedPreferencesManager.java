package com.ujazdowski.client.repository;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencesManager {
    private final static String AUTHENTICATION_SHARED_PREFERENCES = "AUTHENTICATION_SHARED_PREFERENCES";
    private static final String LOGIN_TOKEN = "LOGIN_TOKEN";
    private static final String EMPTY_STRING = new String();

    private static Context applicationContext = null;

    public static void setApplicationContext(Context applicationContext) {
        SharedPreferencesManager.applicationContext = applicationContext;
    }

    public static void setToken(String token) {
        SharedPreferences preferences = applicationContext.getSharedPreferences(AUTHENTICATION_SHARED_PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(LOGIN_TOKEN, token);
        editor.apply();
    }

    public static String getToken() {
        SharedPreferences preferences = applicationContext.getSharedPreferences(AUTHENTICATION_SHARED_PREFERENCES, Context.MODE_PRIVATE);
        return preferences.getString(LOGIN_TOKEN, EMPTY_STRING);
    }

    public static void removeToken() {
        setToken(EMPTY_STRING);
    }
}
