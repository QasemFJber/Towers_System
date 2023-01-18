package com.example.towerssystem.towresview;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.towerssystem.Broadcastreciver.NetworkChangeListiners;
import com.example.towerssystem.R;
import com.example.towerssystem.adapters.AdvertisementsAdapter;
import com.example.towerssystem.controller.AdvertisementsController;
import com.example.towerssystem.databinding.ActivityAdvertisementsBinding;
import com.example.towerssystem.interfaces.AuthCallBack;
import com.example.towerssystem.interfaces.ContentCallBack;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class Advertisements extends AppCompatActivity {
    ActivityAdvertisementsBinding binding;
    NetworkChangeListiners networkChangeListiners = new NetworkChangeListiners();
    private AdvertisementsController controller = new AdvertisementsController();
    private AdvertisementsAdapter adapter ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAdvertisementsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initializeView();
    }


    private void initializeView(){
        operationsSccren();
        getAllAdvertisements();

    }
    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(networkChangeListiners);
    }


    private void operationsSccren() {
        setTitle("Advertisements");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.yl)));
        getWindow().setStatusBarColor(ContextCompat.getColor(Advertisements.this,R.color.black));

    }

    @Override
    protected void onStart() {
        super.onStart();
        IntentFilter intentFilter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(networkChangeListiners,intentFilter);
    }
    private void getAllAdvertisements (){
        controller.getAllAdvertisements(new ContentCallBack<com.example.towerssystem.models.Advertisements>() {
            @Override
            public void onSuccess(List<com.example.towerssystem.models.Advertisements> list) {
                adapter = new AdvertisementsAdapter(list);
                binding.rvAdvertisements.setAdapter(adapter);
                binding.rvAdvertisements.setHasFixedSize(true);
                binding.rvAdvertisements.setLayoutManager(new LinearLayoutManager(Advertisements.this));


            }

            @Override
            public void onFailure(String message) {
                Snackbar.make(binding.getRoot(),message,Snackbar.LENGTH_LONG).show();

            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menuempanuser, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.add) {
            Intent intent = new Intent(getApplicationContext(), AddAdvertisements.class);
            intent.putExtra("id",1);
            startActivity(intent);
        }else if (item.getItemId() == R.id.update){
            Intent intent = new Intent(getApplicationContext(), AddAdvertisements.class);
            intent.putExtra("id",2);
            startActivity(intent);
        }else if (item.getItemId() == R.id.delete){
            deleteAdvertisements();

        }
        return super.onOptionsItemSelected(item);
    }

    private void deleteAdvertisements() {
        controller.deleteAdvertisements(1, new AuthCallBack() {
            @Override
            public void onSuccess(String message) {

            }

            @Override
            public void onFailure(String message) {

            }
        });
    }



}