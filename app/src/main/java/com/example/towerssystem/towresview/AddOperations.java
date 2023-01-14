package com.example.towerssystem.towresview;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.towerssystem.databinding.ActivityAddOperationsBinding;

public class AddOperations extends AppCompatActivity {
    private ActivityAddOperationsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddOperationsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}