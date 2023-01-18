package com.example.towerssystem.towresview;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.ColorDrawable;
import android.icu.text.DateFormat;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.Toast;

import com.example.towerssystem.Broadcastreciver.NetworkChangeListiners;
import com.example.towerssystem.R;
import com.example.towerssystem.controller.OperationsController;
import com.example.towerssystem.databinding.ActivityAddOperationsBinding;
import com.example.towerssystem.interfaces.AuthCallBack;

import java.util.Calendar;

public class AddOperations extends AppCompatActivity  implements DatePickerDialog.OnDateSetListener  {
    private ActivityAddOperationsBinding binding;
    private OperationsController controller = new OperationsController();
    NetworkChangeListiners networkChangeListiners = new NetworkChangeListiners();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddOperationsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        operationsSccren();
        initializeView();
        binding.btnSave.setOnClickListener(v -> {
            performData();

        });
        binding.etDate.setOnClickListener(v -> {
            DialogFragment dialogFragment = new com.example.towerssystem.Fragments.DatePickerDialog();
            dialogFragment.show(getSupportFragmentManager(),"DATE PICKER");
        });
        binding.tvBack.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(),Operations.class);
            startActivity(intent);
        });
    }
    private void initializeView(){
        checkIdOfActivity();

    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR,year);
        calendar.set(Calendar.MONTH,month);
        calendar.set(Calendar.DAY_OF_MONTH,dayOfMonth);
        if (year > 2023 ){
            Toast.makeText(this, " PLEASE ENTER DATE ", Toast.LENGTH_SHORT).show();
        }else {
            String curruntDate = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());
            binding.etDate.setText(curruntDate);
        }
    }
    private void operationsSccren() {
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.yl)));
        getWindow().setStatusBarColor(ContextCompat.getColor(AddOperations.this,R.color.black));

    }

    @Override
    protected void onStart() {
        super.onStart();
        IntentFilter intentFilter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(networkChangeListiners,intentFilter);
    }


    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(networkChangeListiners);
    }

    private void performData(){
        if (checkData()){
            saveOperations();
            Intent intent = new Intent(getApplicationContext(),MainActivity.class);
            startActivity(intent);
        }else {
            Toast.makeText(this, "ENTER REQUERD DATA", Toast.LENGTH_SHORT).show();
        }
    }

    private void saveOperations() {
    }

    private boolean checkData() {
        if (!binding.etCategoryId.getText().toString().isEmpty() &&
                !binding.etAmount.getText().toString().isEmpty() &&
                !binding.etDetails.getText().toString().isEmpty()&&
                !binding.etActorId.getText().toString().isEmpty() &&
                !binding.etActorType.getText().toString().isEmpty() &&
                !binding.etDate.getText().toString().isEmpty()
        ) {
            return true;
        }
        return false;
    }

    private void insertOperations(){
        controller.insertOperations(null, null, null, null, null, null, new AuthCallBack() {
            @Override
            public void onSuccess(String message) {

            }

            @Override
            public void onFailure(String message) {

            }
        });
    }


    private void updateOperations(){
        controller.updateOperations(1, new AuthCallBack() {
            @Override
            public void onSuccess(String message) {

            }

            @Override
            public void onFailure(String message) {

            }
        });
    }

    private void checkIdOfActivity() {
        Intent intent = getIntent();
        int id =  intent.getIntExtra("id",0);
        if (id == 1){
            binding.btnSave.setText("SAVE");
            setTitle("ADD OPERATIONS");
        }else {
            binding.btnSave.setText("UPDATE");
            setTitle("UPDATE OPERATIONS");

        }
    }

}