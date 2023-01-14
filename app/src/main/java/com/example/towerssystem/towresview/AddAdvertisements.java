package com.example.towerssystem.towresview;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.towerssystem.databinding.ActivityAddAdvertisementsBinding;

public class AddAdvertisements extends AppCompatActivity {
    private ActivityAddAdvertisementsBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddAdvertisementsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}