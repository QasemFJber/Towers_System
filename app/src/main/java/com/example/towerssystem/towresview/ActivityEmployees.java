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
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.towerssystem.Broadcastreciver.NetworkChangeListiners;
import com.example.towerssystem.Dialog.CustomDialog;
import com.example.towerssystem.R;
import com.example.towerssystem.adapters.EmployeeAdapter;
import com.example.towerssystem.controller.EmployeeController;
import com.example.towerssystem.databinding.ActivityEmployeesBinding;
import com.example.towerssystem.interfaces.AuthCallBack;
import com.example.towerssystem.interfaces.ContentCallBack;
import com.example.towerssystem.models.Employee;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class ActivityEmployees extends AppCompatActivity implements View.OnClickListener {
    private ActivityEmployeesBinding binding;
    private EmployeeAdapter adapter;
    private EmployeeController controller = new EmployeeController();
    NetworkChangeListiners networkChangeListiners = new NetworkChangeListiners();
    private CustomDialog dialog = new CustomDialog(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEmployeesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initializeView();

    }


    private void initializeView() {
        setOnClick();
        operationsSccren();
        getAllEmployees();
        dialogLoad();
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
        setTitle("EMPLOYEES");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.yl)));
        getWindow().setStatusBarColor(ContextCompat.getColor(ActivityEmployees.this,R.color.black));
        setOnCilck();
    }

    private void setOnCilck() {
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    protected void onStop() {
        unregisterReceiver(networkChangeListiners);
        super.onStop();
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
            Intent intent = new Intent(getApplicationContext(),AddEmployee.class);
            intent.putExtra("id",1);
            startActivity(intent);
        }else if (item.getItemId() == R.id.update){
            Intent intent = new Intent(getApplicationContext(),AddEmployee.class);
            intent.putExtra("id",2);
            startActivity(intent);
        }else if (item.getItemId() == R.id.delete){
            deleteEmployee();

        }else if (item.getItemId() == R.id.addOperations){
            Intent intent = new Intent(getApplicationContext(),AddOperations.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    private void getAllEmployees(){
        controller.getAllEmployees(new ContentCallBack<Employee>() {
            @Override
            public void onSuccess(List<Employee> list) {
                adapter = new EmployeeAdapter(list);
                adapter.notifyItemRangeInserted(0,list.size());
                binding.rvUsers.setAdapter(adapter);
                binding.rvUsers.setHasFixedSize(true);
                binding.rvUsers.setLayoutManager(new LinearLayoutManager(ActivityEmployees.this));
                Log.d("API_REQUEST","LIST_SIZE"+list.size());
            }

            @Override
            public void onFailure(String message) {
                Snackbar.make(binding.getRoot(),message,Snackbar.LENGTH_LONG).show();

            }
        });
    }

    private void deleteEmployee(){
        controller.deleteEmployee(62, new AuthCallBack() {
            @Override
            public void onSuccess(String message) {
                Snackbar.make(binding.getRoot(),message,Snackbar.LENGTH_LONG).show();


            }

            @Override
            public void onFailure(String message) {
                Snackbar.make(binding.getRoot(),message,Snackbar.LENGTH_LONG).show();

            }
        });
    }



}