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
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.towerssystem.Broadcastreciver.NetworkChangeListiners;
import com.example.towerssystem.Dialog.CustomDialog;
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
    private  ResidentController controller = new ResidentController();
    NetworkChangeListiners networkChangeListiners = new NetworkChangeListiners();
    private CustomDialog dialog = new CustomDialog(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityResidentsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initializeView();

    }
    private void initializeView() {
        setOnClick();
        operationsSccren();
        dialogLoad();
        getAllResident();
    }

    private void dialogLoad() {
        dialog.startLoading();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                dialog.dismissDialog();
            }
        },3000);
    }

    private void setOnClick() {
    }

    private void operationsSccren() {
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
        unregisterReceiver(networkChangeListiners);
        onBackPressed();
    }

    @Override
    protected void onStart() {
        super.onStart();
        IntentFilter intentFilter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(networkChangeListiners,intentFilter);
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