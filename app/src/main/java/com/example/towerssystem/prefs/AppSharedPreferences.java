package com.example.towerssystem.prefs;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.towerssystem.models.Admin;
import com.example.towerssystem.models.Resident;
import com.example.towerssystem.towers.towrescontroller.AppController;

enum PrefKeys {
    id, loggedIn, name, email, token ,tower_name ,actor_type
}

public class AppSharedPreferences {

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    private AppSharedPreferences() {
        sharedPreferences = AppController.getInstance().getSharedPreferences("app_prefs", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
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


    public void save(Resident resident) {
        editor = sharedPreferences.edit();
        editor.putBoolean(PrefKeys.loggedIn.name(), true);
        editor.putInt(PrefKeys.id.name(), resident.id);
        editor.putString(PrefKeys.name.name(), resident.name);
        editor.putString(PrefKeys.email.name(), resident.email);
        editor.putString(PrefKeys.tower_name.name(), resident.towerName);
        editor.putString(PrefKeys.token.name(), "Bearer " + resident.token);
        editor.apply();
    }


    public String getToken() {
        return sharedPreferences.getString(PrefKeys.token.name(), "");
    }

    public boolean isLoggedIn() {
        return sharedPreferences.getBoolean(PrefKeys.loggedIn.name(), false);
    }

    public void saveActorType(String type){
        editor.putString(PrefKeys.actor_type.name(),type);
        editor.apply();
    }

    public String getActorType(){
        return sharedPreferences.getString(PrefKeys.actor_type.name(),"");
    }
    public void clear() {
        editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }
}
