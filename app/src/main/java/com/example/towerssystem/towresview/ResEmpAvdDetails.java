package com.example.towerssystem.towresview;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.towerssystem.Broadcastreciver.NetworkChangeListiners;
import com.example.towerssystem.R;

import com.example.towerssystem.databinding.ActivityResEmpAvdDetailsBinding;
import com.squareup.picasso.Picasso;

public class ResEmpAvdDetails extends AppCompatActivity {
    ActivityResEmpAvdDetailsBinding binding;
    NetworkChangeListiners networkChangeListiners = new NetworkChangeListiners();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityResEmpAvdDetailsBinding.inflate(getLayoutInflater());
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
        int id =intent.getIntExtra("iddetails",0);
        Toast.makeText(this, "Id is :"+ id, Toast.LENGTH_SHORT).show();
        if (id == 1){
            setTitle("EMPLOYEES DETAILS");
        Picasso.get().load(intent.getStringExtra("image")).into(binding.imageView2);
        binding.etFirstname.setText(intent.getStringExtra("name"));
        binding.etMobile.setText(intent.getStringExtra("mobile"));
        binding.etNationalNumber.setText(intent.getStringExtra("nationalNumber"));
        binding.tvEmail.setVisibility(View.INVISIBLE);
        binding.etEmailUser.setVisibility(View.INVISIBLE);
        binding.tvFamilyMembers.setText("Towers Name");
        binding.etFamilyMembers.setText(intent.getStringExtra("towerName"));

        }else if (id == 2){
            setTitle("RESIDENTS DETAILS");
            Picasso.get().load(intent.getStringExtra("image")).into(binding.imageView2);
            binding.etFirstname.setText(intent.getStringExtra("name"));
            binding.etMobile.setText(intent.getStringExtra("mobile"));
            binding.etNationalNumber.setText(intent.getStringExtra("nationalNumber"));
            binding.etEmailUser.setText(intent.getStringExtra("email"));
            binding.etFamilyMembers.setText(intent.getStringExtra("familyMembers"));

        }else if (id == 3){
            setTitle(" ADVERTISEMENTS DETAILS");
         Picasso.get().load(intent.getStringExtra("image")).into(binding.imageView2);
         binding.tvFirstname.setText("TITLE");
         binding.etFirstname.setText(intent.getStringExtra("title"));
         binding.tvMobile.setText("INFO");
         binding.etMobile.setText(intent.getStringExtra("info"));
         binding.tvNationalNumber.setText("Towers Name");
         binding.etNationalNumber.setText(intent.getStringExtra("towerName"));
         binding.tvEmail.setVisibility(View.INVISIBLE);
         binding.etEmailUser.setVisibility(View.INVISIBLE);
         binding.tvFamilyMembers.setVisibility(View.INVISIBLE);
         binding.etFamilyMembers.setVisibility(View.INVISIBLE);
        }else if (id == 4){
            setTitle("OPERATIONS DETAILS");
            Picasso.get().load(intent.getStringExtra("image")).into(binding.imageView2);
            binding.tvFirstname.setText("CATEGORY NAME");
            binding.tvMobile.setText("AMOUNT");
            binding.tvNationalNumber.setText("EMPLOYEE NAME");
            binding.tvFamilyMembers.setText("DATE");
            binding.tvEmail.setText("DETAILS");
            binding.etFirstname.setText(intent.getStringExtra("categoryName"));
            binding.etMobile.setText(intent.getStringExtra("amount"));
            binding.etNationalNumber.setText(intent.getStringExtra("employee_name"));
            binding.etFamilyMembers.setText(intent.getStringExtra("date"));
            binding.etEmailUser.setText(intent.getStringExtra("details"));
        }


    }
    @Override
    protected void onStop() {
        unregisterReceiver(networkChangeListiners);
        super.onStop();
    }

    private void operationsSccren () {

        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.yl)));
        getWindow().setStatusBarColor(ContextCompat.getColor(ResEmpAvdDetails.this, R.color.black));
    }
}