package com.example.towerssystem.towresview;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.towerssystem.databinding.ActivityAdvertisementsBinding;

public class Advertisements extends AppCompatActivity {
    ActivityAdvertisementsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAdvertisementsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}