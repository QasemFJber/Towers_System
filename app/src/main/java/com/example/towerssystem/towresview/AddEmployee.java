package com.example.towerssystem.towresview;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.towerssystem.Broadcastreciver.NetworkChangeListiners;
import com.example.towerssystem.Dialog.AddedDialog;
import com.example.towerssystem.Dialog.UpdatedDialog;
import com.example.towerssystem.controller.EmployeeController;
import com.example.towerssystem.R;
import com.example.towerssystem.databinding.AddEmployeeBinding;
import com.example.towerssystem.interfaces.AuthCallBack;
import com.example.towerssystem.models.Employee;
import com.google.android.material.snackbar.Snackbar;

import java.io.ByteArrayOutputStream;

public class AddEmployee extends AppCompatActivity  implements View.OnClickListener {
    AddEmployeeBinding binding;
    UpdatedDialog updatedDialog = new UpdatedDialog(this);
    private ActivityResultLauncher<String> permissionResultLauncher;
    private ActivityResultLauncher<Void> cameraResultLauncher;
    private Bitmap imageBitmap;
    private Dialog dialog;
    private Uri imagePick;
    private static int idEmployee;
    private static int ID;
    private EmployeeController controller = new EmployeeController();
    NetworkChangeListiners networkChangeListiners = new NetworkChangeListiners();
    AddedDialog addedDialog = new AddedDialog(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = AddEmployeeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initializeView();

    }

    private void initializeView() {
        setOnClick();
        operationsSccren();
        setupResultsLauncher();
        checkIdOfActivity();
        Toast.makeText(this, "The Employee Id is :"+idEmployee, Toast.LENGTH_SHORT).show();
    }

    private void setOnClick() {
        binding.imageView2.setOnClickListener(this::onClick);
        binding.tvBack.setOnClickListener(this::onClick);
        binding.imageView4.setOnClickListener(this::onClick);
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(networkChangeListiners);


    }

    @Override
    protected void onStart() {
        super.onStart();
        IntentFilter intentFilter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(networkChangeListiners,intentFilter);
    }


    public void saveEmployee(){
        String name = binding.etFirstname.getText().toString().trim();
        String mobile =binding.etMobile.getText().toString().trim();
        String number =binding.etNationalNumber.getText().toString().trim();
        controller.insertEmployee(name,mobile,number,bitmapToBytes(), new AuthCallBack() {
            @Override
            public void onSuccess(String message) {
                addedDialog.startLoading();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        addedDialog.dismissDialog();
                        Intent intent = new Intent(getApplicationContext(),ActivityEmployees.class);
                        startActivity(intent);
                    }
                },2000);
            }

            @Override
            public void onFailure(String message) {
                Snackbar.make(binding.getRoot(),message,Snackbar.LENGTH_LONG).show();

            }
        });

    }

    private void checkIdOfActivity() {
        Intent intent = getIntent();
         ID =  intent.getIntExtra("id",0);
         idEmployee =  intent.getIntExtra("employeeID",0);
        if (ID == 1){
            binding.btnSave.setText("SAVE");
            setTitle("ADD EMPLOYEE");
        }else {
            binding.btnSave.setText("UPDATE");
            setTitle("UPDATE EMPLOYEE");

        }
    }

    private void operationsSccren() {
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.yl)));
        getWindow().setStatusBarColor(ContextCompat.getColor(AddEmployee.this,R.color.black));
        setOnCilck();
    }

    private void setOnCilck() {
        binding.imageView2.setOnClickListener(this::onClick);
        binding.imageView4.setOnClickListener(this::onClick);
        binding.btnSave.setOnClickListener(this::onClick);
        binding.tvBack.setOnClickListener(this::onClick);
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_save){
            performData();
        }else if (v.getId() == R.id.imageView2){
            pickImage();
        }else if (v.getId() == R.id.tv_back) {
            Intent intent = new Intent(getApplicationContext(),ActivityEmployees.class);
            startActivity(intent);
            finish();
        }else if (v.getId() == R.id.imageView4) {
            Intent intent = new Intent(getApplicationContext(),ActivityEmployees.class);
            startActivity(intent);
            finish();
        }
    }





    private void setupResultsLauncher() {
        permissionResultLauncher = registerForActivityResult(new ActivityResultContracts.RequestPermission(), new ActivityResultCallback<Boolean>() {
            @Override
            public void onActivityResult(Boolean result) {
                if (result) {
                    cameraResultLauncher.launch(null);

                }
            }
        });

        cameraResultLauncher = registerForActivityResult(new ActivityResultContracts.TakePicturePreview(), new ActivityResultCallback<Bitmap>() {
            @Override
            public void onActivityResult(Bitmap result) {
                if (result != null) {
                    imageBitmap = result;
                    binding.imageView2.setImageBitmap(imageBitmap);
                    binding.tvAddImage.setText("");
                }
            }
        });
    }

    private void performData(){
        if (checkData() && ID ==1){
            saveEmployee();
        }else if (checkData() && ID == 2){
            updateEmployee();
        }
    }

        private boolean checkData() {
        if (!binding.etFirstname.getText().toString().isEmpty() &&
                !binding.etMobile.getText().toString().isEmpty() &&
                !binding.etNationalNumber.getText().toString().isEmpty()
                ) {
            bitmapToBytes();
            return true;
        }
        return false;
    }

    private void pickImage() {
        permissionResultLauncher.launch(Manifest.permission.CAMERA);
    }


    private byte[] bitmapToBytes() {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        imageBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        return stream.toByteArray();
    }

    private void updateEmployee(){
        String name = binding.etFirstname.getText().toString().trim();
        String mobile =binding.etMobile.getText().toString().trim();
        String number =binding.etNationalNumber.getText().toString().trim();
        controller.updateEmployee(idEmployee,name,mobile,number,bitmapToBytes(),"PUT", new AuthCallBack() {
            @Override
            public void onSuccess(String message) {
                updatedDialog.startLoading();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        updatedDialog.dismissDialog();
                        Intent intent = new Intent(getApplicationContext(),Employee.class);
                        startActivity(intent);
                    }
                },2000);

            }

            @Override
            public void onFailure(String message) {

            }
        });
    }

//
//    private void controlGenderSelection() {
//        binding.genderRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(RadioGroup group, int checkedId) {
//                gender = checkedId == R.id.male_radio_button ? "M" : "F";
//            }
//        });
//    }

//    private void showDialog(){
//        dialog = new Dialog(AddEmployee.this);
//        dialog.setContentView(R.layout.customdialogpiak);
//
//        dialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.background));
//        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
//        dialog.setCancelable(false);
//        dialog.getWindow().getAttributes().windowAnimations = R.style.animation;
//        Button pick = dialog.findViewById(R.id.btn_pick);
//        Button upload = dialog.findViewById(R.id.btn_upload);
//        dialog.show();
//        pick.setOnClickListener(v -> {
//            Toast.makeText(this, "PICK IMAGE", Toast.LENGTH_SHORT).show();
//            dialog.dismiss();
//        });
//
//        upload.setOnClickListener(v -> {
//            Toast.makeText(this, "UPLOAD IMAGE", Toast.LENGTH_SHORT).show();
//            dialog.dismiss();
//
//        });
//    }
}