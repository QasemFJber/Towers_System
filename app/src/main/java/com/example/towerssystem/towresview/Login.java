package com.example.towerssystem.towresview;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.towerssystem.Broadcastreciver.NetworkChangeListiners;
import com.example.towerssystem.R;
import com.example.towerssystem.controller.AuthController;
import com.example.towerssystem.databinding.ActivityLoginBinding;
import com.example.towerssystem.interfaces.AuthCallBack;
import com.example.towerssystem.prefs.AppSharedPreferences;
import com.google.android.material.snackbar.Snackbar;

public class Login extends AppCompatActivity implements View.OnClickListener {
    private ActivityLoginBinding binding;
    AuthController authController = new AuthController();
    NetworkChangeListiners networkChangeListiners = new NetworkChangeListiners();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding =ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.yl)));
        getWindow().setStatusBarColor(ContextCompat.getColor(Login.this,R.color.black));
        initializeView();
    }

    private void setOnClick() {
        binding.btnLogin.setOnClickListener(this::onClick);


    }

    private void initializeView() {
        setOnClick();
    }



    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_login) {
            performLogin();
        }
    }

    private void performLogin() {
        login();
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(networkChangeListiners);
        finish();
    }

    private void login() {
       authController.login(binding.etEmail.getText().toString().trim(), binding.etPassword.getText().toString().trim(), new AuthCallBack() {
           @Override
           public void onSuccess(String message) {
               String type = AppSharedPreferences.getInstance().getActorType();
               if (type.equals(AppSharedPreferences.getInstance().getActorType())){
                   Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                   startActivity(intent);
                   finish();
               }else{
                   Intent intent = new Intent(getApplicationContext(),Home.class);
                   startActivity(intent);
                   finish();
               }


           }

           @Override
           public void onFailure(String message) {
               Log.d("Retrofit", "onFailure: " + message);
               Snackbar.make(binding.getRoot(), message, Snackbar.LENGTH_LONG).show();
           }
       });
    }

    @Override
    protected void onStart() {
        super.onStart();
        IntentFilter intentFilter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(networkChangeListiners,intentFilter);
    }
    //

}