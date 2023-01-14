package com.example.towerssystem.towresview;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.towerssystem.R;
import com.example.towerssystem.adapters.EmployeeAdapter;
import com.example.towerssystem.controller.EmployeeController;
import com.example.towerssystem.databinding.ActivityEmployeesBinding;
import com.example.towerssystem.interfaces.ContentCallBack;
import com.example.towerssystem.models.Employee;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class ActivityEmployees extends AppCompatActivity implements View.OnClickListener {
    ActivityEmployeesBinding binding;
    private EmployeeAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEmployeesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        OperationsSccren();
        getAllEmployees();
    }
    private void OperationsSccren() {
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
            Intent intent = new Intent(getApplicationContext(),AddEmployee.class);
            intent.putExtra("id",1);
            startActivity(intent);
        }else if (item.getItemId() == R.id.update){
            Intent intent = new Intent(getApplicationContext(),AddEmployee.class);
            intent.putExtra("id",2);
            startActivity(intent);
        }else if (item.getItemId() == R.id.delete){

        }
        return super.onOptionsItemSelected(item);
    }

    private void getAllEmployees(){
        EmployeeController controller = new EmployeeController();
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

}