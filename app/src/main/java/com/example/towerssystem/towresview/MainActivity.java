package com.example.towerssystem.towresview;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

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
    CategoriesAdapter adapter ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initializeView();
        String name =AppSharedPreferences.getInstance().getToken();
        Toast.makeText(this, name, Toast.LENGTH_SHORT).show();
    }

    private void operationsSccren() {
        setTitle("HOME");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.yl)));
        getWindow().setStatusBarColor(ContextCompat.getColor(MainActivity.this,R.color.black));
    }

    @Override
    protected void onStart() {
        super.onStart();
        setDataInRecycler();
    }

    private void setDataInRecycler() {
        adapter = new CategoriesAdapter(categories);

    }


    @Override
    protected void onStop() {
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
            Intent intent = new Intent(getApplicationContext(), ActivityUsers.class);
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
                binding.rvCategories.setLayoutManager(new GridLayoutManager(MainActivity.this,1));
                binding.rvCategories.setHasFixedSize(true);
                Toast.makeText(MainActivity.this, list.size(), Toast.LENGTH_SHORT).show();
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