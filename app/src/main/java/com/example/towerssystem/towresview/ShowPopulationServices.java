package com.example.towerssystem.towresview;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.towerssystem.Broadcastreciver.NetworkChangeListiners;
import com.example.towerssystem.Dialog.CustomDialog;
import com.example.towerssystem.R;
import com.example.towerssystem.adapters.ShowAdapter;
import com.example.towerssystem.controller.CategoriesController;

import com.example.towerssystem.databinding.ActivityShowPopulationServicesBinding;
import com.example.towerssystem.interfaces.DetailsClick;
import com.example.towerssystem.interfaces.ShowListenirs;
import com.example.towerssystem.models.ShowCategorie;

import java.util.ArrayList;
import java.util.List;

public class ShowPopulationServices extends AppCompatActivity implements DetailsClick {
    ActivityShowPopulationServicesBinding binding;
    CategoriesController categoriesController = new CategoriesController();
    private List<ShowCategorie> categories = new ArrayList<>();
    NetworkChangeListiners networkChangeListiners = new NetworkChangeListiners();
    private CustomDialog dialog = new CustomDialog(this);
    private ShowAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityShowPopulationServicesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initializeView();

    }

    private void initializeView() {
        getCategoriesOfId();
        operationsSccren();
        loadDialog();
    }

    private void loadDialog() {
        dialog.startLoading();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                dialog.dismissDialog();
            }
        }, 3000);
    }

    @Override
    protected void onStart() {
        setDataInRecycler();
        IntentFilter intentFilter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(networkChangeListiners, intentFilter);
        super.onStart();


    }

    private void setDataInRecycler() {
        adapter = new ShowAdapter(categories,this);

    }


    @Override
    protected void onStop() {
        unregisterReceiver(networkChangeListiners);
        super.onStop();
    }

    private void getCategoriesOfId() {
        Intent intent = getIntent();
        int id = intent.getIntExtra("id",0);
        Toast.makeText(this, "The id is :"+ id, Toast.LENGTH_SHORT).show();
        categoriesController.getAllCategoriesOfCategorieId(id, new ShowListenirs() {
            @Override
            public void onSuccess(List<ShowCategorie> list) {
                adapter.setCategorieList(list);
                Log.d("LIST_SIZE","LIST : "+list.size());
                if (list.size() ==0){
                    dialog.dismissDialog();
                    binding.tvData.setVisibility(View.VISIBLE);
                    binding.nodata.setVisibility(View.VISIBLE);
                }
                binding.rvService.setAdapter(adapter);
                binding.rvService.setLayoutManager(new LinearLayoutManager(ShowPopulationServices.this));
                binding.rvService.setHasFixedSize(true);
            }

            @Override
            public void onFailure(String message) {

            }
        });

    }
//    private void getCategoriesOfId() {
//        Intent intent = getIntent();
//        int id = intent.getIntExtra("id",0);
//        categoriesController.getAllCategoriesOfCategorieId(id, new ShowListenirs() {
//            @Override
//            public void onSuccess(List<com.example.towerssystem.models.ShowCategorie> list) {
//                adapter.setCategorieList(list);
//                if (list.size() ==0){
//                    dialog.dismissDialog();
//                    binding.tvData.setVisibility(View.VISIBLE);
//                    binding.nodata.setVisibility(View.VISIBLE);
//                }
//                binding.rvService.setAdapter(adapter);
//                binding.rvService.setLayoutManager(new LinearLayoutManager(ShowPopulationServices.this));
//                binding.rvService.setHasFixedSize(true);
//            }
//
//            @Override
//            public void onFailure(String message) {
//
//            }
//        });
//
//    }
    private void operationsSccren() {
        setTitle("Population Services");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.yl)));
        getWindow().setStatusBarColor(ContextCompat.getColor(ShowPopulationServices.this,R.color.black));

    }

    @Override
    public void onClick(ShowCategorie showCategorie) {
        Intent intent = new Intent(getApplicationContext(),Details.class);
        intent.putExtra("categoryName",showCategorie.categoryName);
        intent.putExtra("amount",showCategorie.amount);
        intent.putExtra("residentname",showCategorie.resident.name);
        intent.putExtra("date",showCategorie.date);
        intent.putExtra("residentemail",showCategorie.resident.email);
        intent.putExtra("residenttowerName",showCategorie.resident.towerName);
        intent.putExtra("details",showCategorie.details);
        intent.putExtra("image",showCategorie.resident.imageUrl);
        startActivity(intent);

    }
}