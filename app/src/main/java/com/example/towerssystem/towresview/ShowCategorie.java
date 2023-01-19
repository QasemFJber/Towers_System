package com.example.towerssystem.towresview;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.IntentFilter;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.example.towerssystem.Broadcastreciver.NetworkChangeListiners;
import com.example.towerssystem.Dialog.CustomDialog;
import com.example.towerssystem.R;
import com.example.towerssystem.adapters.CategoriesAdapter;
import com.example.towerssystem.adapters.ShowAdapter;
import com.example.towerssystem.controller.CategoriesController;
import com.example.towerssystem.databinding.ActivityShowCategorieBinding;
import com.example.towerssystem.interfaces.ContentCallBack;
import com.example.towerssystem.interfaces.ShowListenirs;
import com.example.towerssystem.models.Categorie;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class ShowCategorie extends AppCompatActivity {
    private ActivityShowCategorieBinding binding;
    CategoriesController categoriesController = new CategoriesController();
    private List<com.example.towerssystem.models.ShowCategorie> categories = new ArrayList<>();
    NetworkChangeListiners networkChangeListiners = new NetworkChangeListiners();
    private CustomDialog dialog = new CustomDialog(this);
    private ShowAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityShowCategorieBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initializeView();
        dialog.startLoading();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                dialog.dismissDialog();
            }
        }, 3000);
    }

    private void initializeView() {
        getCategoriesOfId();
        operationsSccren();
    }

    @Override
    protected void onStart() {
        setDataInRecycler();
        IntentFilter intentFilter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(networkChangeListiners, intentFilter);
        super.onStart();


    }

    private void setDataInRecycler() {
        adapter = new ShowAdapter(categories);

    }


    @Override
    protected void onStop() {
        unregisterReceiver(networkChangeListiners);
        super.onStop();
    }


    private void getCategoriesOfId() {
        categoriesController.getAllCategoriesOfCategorieId(1, new ShowListenirs() {
            @Override
            public void onSuccess(List<com.example.towerssystem.models.ShowCategorie> list) {
                adapter.setCategorieList(list);
                binding.rvCateg.setAdapter(adapter);
                binding.rvCateg.setLayoutManager(new LinearLayoutManager(ShowCategorie.this));
                binding.rvCateg.setHasFixedSize(true);
            }

            @Override
            public void onFailure(String message) {

            }
        });


    }
    private void operationsSccren () {
        setTitle("OperationsOfIdCategories");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.yl)));
        getWindow().setStatusBarColor(ContextCompat.getColor(ShowCategorie.this, R.color.black));
    }
}