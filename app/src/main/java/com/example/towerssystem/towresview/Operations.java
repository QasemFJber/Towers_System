package com.example.towerssystem.towresview;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.towerssystem.R;
import com.example.towerssystem.adapters.OperationsAdapter;
import com.example.towerssystem.adapters.UserAdapter;
import com.example.towerssystem.controller.OperationsController;
import com.example.towerssystem.databinding.ActivityOperationsBinding;
import com.example.towerssystem.interfaces.AuthCallBack;
import com.example.towerssystem.interfaces.ContentCallBack;
import com.example.towerssystem.models.Resident;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class Operations extends AppCompatActivity implements View.OnClickListener {
    private ActivityOperationsBinding binding;
    private OperationsController controller = new OperationsController();
    private OperationsAdapter adapter = new OperationsAdapter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOperationsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initializeView();
    }
    private void initializeView(){
        setOnCilck();
        OperationsSccren();

    }
    private void OperationsSccren() {
        setTitle("Operations");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.yl)));
        getWindow().setStatusBarColor(ContextCompat.getColor(Operations.this,R.color.black));
        setOnCilck();
    }

    private void setOnCilck() {
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    protected void onStop() {
        super.onStop();
        onBackPressed();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menuempanuser, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.add) {
            Intent intent = new Intent(getApplicationContext(), AddOperations.class);
            intent.putExtra("id",1);
            startActivity(intent);
        }else if (item.getItemId() == R.id.update){
            Intent intent = new Intent(getApplicationContext(), AddOperations.class);
            intent.putExtra("id",2);
            startActivity(intent);
        }else if (item.getItemId() == R.id.delete){
            deleteResident();

        }
        return super.onOptionsItemSelected(item);
    }

    private void getAllOperations(){
        controller.getAllOperations(new ContentCallBack<com.example.towerssystem.models.Operations>() {
            @Override
            public void onSuccess(List<com.example.towerssystem.models.Operations> list) {
                adapter = new OperationsAdapter();
                binding.rvOperations.setAdapter(adapter);
                binding.rvOperations.setHasFixedSize(true);
                binding.rvOperations.setLayoutManager(new LinearLayoutManager(Operations.this));
            }

            @Override
            public void onFailure(String message) {

            }
        });
    }

    private  void deleteResident(){
        controller.deleteOperations(1, new AuthCallBack() {
            @Override
            public void onSuccess(String message) {

            }

            @Override
            public void onFailure(String message) {

            }
        });

    }
}