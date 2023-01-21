package com.example.towerssystem.towresview;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.widget.Toast;

import com.example.towerssystem.Broadcastreciver.NetworkChangeListiners;
import com.example.towerssystem.R;
import com.example.towerssystem.databinding.ActivityDetailsBinding;
import com.squareup.picasso.Picasso;

public class Details extends AppCompatActivity {
    ActivityDetailsBinding binding;
    NetworkChangeListiners networkChangeListiners = new NetworkChangeListiners();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initializeView();


    }


    private void initializeView() {
        operationsSccren();
        setDataInTextViews();
        setOnClickListeners();
    }

    private void setOnClickListeners() {
        binding.imageView4.setOnClickListener(v -> {
            onBackPressed();
        });
        binding.tvBack.setOnClickListener(v -> {
            onBackPressed();
        });
    }


    @Override
    protected void onStart() {

        IntentFilter intentFilter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(networkChangeListiners, intentFilter);
        super.onStart();


    }

    private void setDataInTextViews(){
        Intent intent = getIntent();
        Picasso.get().load(intent.getStringExtra("image")).into(binding.imageView2);
        binding.etCategoryNameDetails.setText(intent.getStringExtra("categoryName"));
        binding.etAmountDetails.setText(intent.getStringExtra("amount"));
        binding.etResidentNameDetails.setText(intent.getStringExtra("residentname"));
        binding.etDateDetails.setText(intent.getStringExtra("date"));
        binding.etEmailDetails.setText(intent.getStringExtra("residentemail"));
        binding.etTowresNameDetails.setText(intent.getStringExtra("residenttowerName"));
        binding.etDetailsDeta.setText(intent.getStringExtra("details"));
    }

    @Override
    protected void onStop() {
        unregisterReceiver(networkChangeListiners);
        super.onStop();
    }

    private void operationsSccren () {
        setTitle("DETAILS");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.yl)));
        getWindow().setStatusBarColor(ContextCompat.getColor(Details.this, R.color.black));
    }
}