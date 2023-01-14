package com.example.towerssystem.prefs;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.towerssystem.models.Admin;
import com.example.towerssystem.towers.towrescontroller.AppController;

enum PrefKeys {
    id, loggedIn, name, email, token ,tower_name
}

public class AppSharedPreferences {

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    private AppSharedPreferences() {
        sharedPreferences = AppController.getInstance().getSharedPreferences("app_prefs", Context.MODE_PRIVATE);
    }

    private static AppSharedPreferences instance;

    public static AppSharedPreferences getInstance() {
        if (instance == null) {
            instance = new AppSharedPreferences();
        }

        return instance;
    }

    public SharedPreferences.Editor getEditor() {
        return editor;
    }


    public SharedPreferences getSharedPreferences() {
        return sharedPreferences;

    }


    public void save(Admin admin) {
        editor = sharedPreferences.edit();
        editor.putBoolean(PrefKeys.loggedIn.name(), true);
        editor.putInt(PrefKeys.id.name(), admin.id);
        editor.putString(PrefKeys.name.name(), admin.name);
        editor.putString(PrefKeys.email.name(), admin.email);
        editor.putString(PrefKeys.tower_name.name(), admin.towerName);
        editor.putString(PrefKeys.token.name(), "Bearer " + admin.token);
        editor.apply();
    }

    public String getToken() {
        return sharedPreferences.getString(PrefKeys.token.name(), "");
    }

    public boolean isLoggedIn() {
        return sharedPreferences.getBoolean(PrefKeys.loggedIn.name(), false);
    }

    public void clear() {
        editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }
}
