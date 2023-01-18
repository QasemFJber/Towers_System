package com.example.towerssystem.towresview;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;

import com.example.towerssystem.Broadcastreciver.NetworkChangeListiners;
import com.example.towerssystem.R;
import com.example.towerssystem.controller.AuthController;
import com.example.towerssystem.databinding.ActivityForgotPasswordBinding;
import com.example.towerssystem.interfaces.AuthCallBack;
import com.google.android.material.snackbar.Snackbar;

public class ChangePassword extends AppCompatActivity  implements View.OnClickListener {
    private ActivityForgotPasswordBinding binding;
    AuthController authController = new AuthController();
    NetworkChangeListiners networkChangeListiners = new NetworkChangeListiners();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityForgotPasswordBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setTitle("Change Password");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.yl)));
        getWindow().setStatusBarColor(ContextCompat.getColor(ChangePassword.this,R.color.black));
        setOnClick();

    }

    private void setOnClick() {
        binding.btnSave.setOnClickListener(this::onClick);
        binding.tvBack.setOnClickListener(this::onClick);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_save){
            changePassword();
        } else if (v.getId() == R.id.tv_back) {
            Intent intent = new Intent(getApplicationContext(),MainActivity.class);
            startActivity(intent);
        }
    }

    public void changePassword(){
        authController.changePassword(binding.etCurrentPassword.getText().toString().trim(), binding.etNewPassword.getText().toString().trim(), binding.etNewPasswordConfirmation.getText().toString().trim(), new AuthCallBack() {
            @Override
            public void onSuccess(String message) {
                Snackbar.make(binding.getRoot(),message,Snackbar.LENGTH_LONG).show();
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);

            }

            @Override
            public void onFailure(String message) {

                Snackbar.make(binding.getRoot(),message,Snackbar.LENGTH_LONG).show();

            }
        });

    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(networkChangeListiners);
    }

    @Override
    protected void onStart() {
        super.onStart();
        IntentFilter intentFilter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(networkChangeListiners,intentFilter);
    }
}