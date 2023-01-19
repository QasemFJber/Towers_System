package com.example.towerssystem.towresview;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;

import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.towerssystem.Broadcastreciver.NetworkChangeListiners;
import com.example.towerssystem.Dialog.CustomDialog;
import com.example.towerssystem.R;
import com.example.towerssystem.adapters.CategoriesAdapter;
import com.example.towerssystem.controller.AuthController;
import com.example.towerssystem.controller.CategoriesController;
import com.example.towerssystem.databinding.ActivityMainBinding;
import com.example.towerssystem.interfaces.AuthCallBack;
import com.example.towerssystem.interfaces.ContentCallBack;
import com.example.towerssystem.models.Categorie;
import com.example.towerssystem.prefs.AppSharedPreferences;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private  ActivityMainBinding binding;
    AuthController authController = new AuthController();
    CategoriesController categoriesController = new CategoriesController();
    private List<Categorie> categories = new ArrayList<>();
    NetworkChangeListiners networkChangeListiners = new NetworkChangeListiners();
    private CustomDialog dialog = new CustomDialog(this);

    CategoriesAdapter adapter ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initializeView();
        dialog.startLoading();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                dialog.dismissDialog();
            }
        },3000);
    }

    private void operationsSccren() {
        setTitle("HOME");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.yl)));
        getWindow().setStatusBarColor(ContextCompat.getColor(MainActivity.this,R.color.black));
    }

    @Override
    protected void onStart() {
        setDataInRecycler();
        IntentFilter intentFilter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(networkChangeListiners,intentFilter);
        super.onStart();


    }

    private void setDataInRecycler() {
        adapter = new CategoriesAdapter(categories);

    }


    @Override
    protected void onStop() {
        unregisterReceiver(networkChangeListiners);
        super.onStop();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.logout) {
            logout();
        }else if (item.getItemId() == R.id.change_password){
            Intent intent = new Intent(getApplicationContext(), ChangePassword.class);
            startActivity(intent);
        }else if (item.getItemId() == R.id.add_employee){
            Intent intent = new Intent(getApplicationContext(), ActivityEmployees.class);
            startActivity(intent);
        }else if (item.getItemId() == R.id.add_user){
            Intent intent = new Intent(getApplicationContext(), ActivityResidents.class);
            startActivity(intent);
        }else if (item.getItemId() == R.id.operations){
            Intent intent = new Intent(getApplicationContext(), Operations.class);
            startActivity(intent);
        }else if (item.getItemId() == R.id.advertisements){
            Intent intent = new Intent(getApplicationContext(), Advertisements.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    private void initializeView() {
        getCategories();
        operationsSccren();
    }


    private void getCategories() {
        categoriesController.getAllCategories(new ContentCallBack<Categorie>() {
            @Override
            public void onSuccess(List<Categorie> list) {
                adapter.setCategories(list);
                binding.rvCategories.setAdapter(adapter);
                binding.rvCategories.setLayoutManager(new GridLayoutManager(MainActivity.this,2));
                binding.rvCategories.setHasFixedSize(true);

                Log.v("QASEM_LIST","LIST"+list.size());
            }

            @Override
            public void onFailure(String message) {
                Snackbar.make(binding.getRoot(),message, Snackbar.LENGTH_LONG).show();

            }
        });
    }

    private void logout() {
        authController.logout(new AuthCallBack() {
            @Override
            public void onSuccess(String message) {
                Intent intent = new Intent(getApplicationContext(),Login.class);
                startActivity(intent);
            }

            @Override
            public void onFailure(String message) {
                Snackbar.make(binding.getRoot(),message,Snackbar.LENGTH_LONG).show();

            }
        });
    }
}