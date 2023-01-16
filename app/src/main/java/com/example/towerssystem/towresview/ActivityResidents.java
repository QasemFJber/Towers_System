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
import com.example.towerssystem.adapters.UserAdapter;
import com.example.towerssystem.controller.ResidentController;
import com.example.towerssystem.databinding.ActivityResidentsBinding;
import com.example.towerssystem.interfaces.AuthCallBack;
import com.example.towerssystem.interfaces.ContentCallBack;
import com.example.towerssystem.models.Resident;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class ActivityResidents extends AppCompatActivity implements View.OnClickListener {
    private ActivityResidentsBinding binding;
    private UserAdapter adapter;
    private  ResidentController controller = new ResidentController();;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityResidentsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        OperationsSccren();
        getAllResident();
    }
    private void OperationsSccren() {
        setTitle("Residents");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.yl)));
        getWindow().setStatusBarColor(ContextCompat.getColor(ActivityResidents.this,R.color.black));
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
            Intent intent = new Intent(getApplicationContext(), AddResident.class);
            intent.putExtra("id",1);
            startActivity(intent);
        }else if (item.getItemId() == R.id.update){
            Intent intent = new Intent(getApplicationContext(), AddResident.class);
            intent.putExtra("id",2);
            startActivity(intent);
        }else if (item.getItemId() == R.id.delete){
            deleteResident();

        }else if (item.getItemId() == R.id.addOperations){
            Intent intent = new Intent(getApplicationContext(),AddOperations.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    private void getAllResident(){
        controller.getAllResident(new ContentCallBack<Resident>() {
            @Override
            public void onSuccess(List<Resident> list) {
                adapter = new UserAdapter(list);
                binding.rvUsers.setAdapter(adapter);
                binding.rvUsers.setHasFixedSize(true);
                binding.rvUsers.setLayoutManager(new LinearLayoutManager(ActivityResidents.this));

            }

            @Override
            public void onFailure(String message) {
                Snackbar.make(binding.getRoot(),message,Snackbar.LENGTH_LONG).show();

            }
        });
    }

    private  void deleteResident(){
        controller.deleteResident(83, new AuthCallBack() {
            @Override
            public void onSuccess(String message) {
                Intent intent = new Intent(getApplicationContext(), ActivityResidents.class);
                startActivity(intent);

            }

            @Override
            public void onFailure(String message) {

            }
        });

    }

}