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
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.towerssystem.Broadcastreciver.NetworkChangeListiners;
import com.example.towerssystem.Dialog.AddedDialog;
import com.example.towerssystem.Dialog.UpdatedDialog;
import com.example.towerssystem.controller.ResidentController;
import com.example.towerssystem.R;
import com.example.towerssystem.databinding.AddResidentBinding;
import com.example.towerssystem.interfaces.AuthCallBack;
import com.example.towerssystem.models.Resident;
import com.google.android.material.snackbar.Snackbar;

import java.io.ByteArrayOutputStream;

public class AddResident extends AppCompatActivity implements View.OnClickListener {
    AddResidentBinding binding;
    private ActivityResultLauncher<String> permissionResultLauncher;
    private ActivityResultLauncher<Void> cameraResultLauncher;
    UpdatedDialog updatedDialog = new UpdatedDialog(this);
    private Bitmap imageBitmap;
    private Dialog dialog;
    private Uri imagePick;
    private String gender;
    private static int idResident;
    private static int ID;
    private ResidentController controller = new ResidentController();
    NetworkChangeListiners networkChangeListiners = new NetworkChangeListiners();
    AddedDialog addedDialog = new AddedDialog(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding  = AddResidentBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
      initializeView();

    }
    private void initializeView() {
        setOnCilck();
        OperationsSccren();
        setupResultsLauncher();
        checkIdOfActivity();
        controlGenderSelection();
        Toast.makeText(this, "The Resident Id is : " + idResident, Toast.LENGTH_SHORT).show();
    }

    private void checkIdOfActivity() {
        Intent intent = getIntent();
        ID =  intent.getIntExtra("id",0);
        idResident =  intent.getIntExtra("residentID",0);
       if (ID == 1){
           binding.btnSave.setText("Save");
           setTitle("ADD RESIDENT");
       }else {
           binding.btnSave.setText("UPDATE");
           setTitle("UPDATE RESIDENT");

       }
    }

    private void OperationsSccren() {
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.yl)));
        getWindow().setStatusBarColor(ContextCompat.getColor(AddResident.this,R.color.black));
        setOnCilck();
    }


    private void setOnCilck() {
        binding.imageView2.setOnClickListener(this::onClick);
        binding.btnSave.setOnClickListener(this::onClick);
        binding.tvBack.setOnClickListener(this::onClick);
        binding.imageView4.setOnClickListener(this::onClick);
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


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_save){
            performData();
        }else if (v.getId() == R.id.imageView2){
            pickImage();
        }else if (v.getId() == R.id.tv_back) {
            Intent intent = new Intent(getApplicationContext(), ActivityResidents.class);
            startActivity(intent);
            finish();
        }else if (v.getId() == R.id.imageView4) {
            Intent intent = new Intent(getApplicationContext(), ActivityResidents.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode== RESULT_OK){
            imagePick = data.getData();
        }
    }


    private void performData(){
        if (checkData() && ID == 1){
            saveResident();
        }else if (checkData() && ID == 2){
            updateResident();
        }else {
            Toast.makeText(this, " PLEASE ENTER DATA", Toast.LENGTH_SHORT).show();
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

    private void pickImage() {
        permissionResultLauncher.launch(Manifest.permission.CAMERA);
    }

    private void saveResident() {
        String name =binding.etFirstname.getText().toString().trim();
        String email = binding.etEmailUser.getText().toString().trim();
        String mobile = binding.etMobile.getText().toString().trim();
        String number =binding.etNationalNumber.getText().toString().trim();
        String family =binding.etFamilyMembers.getText().toString().trim();
        controller.insertResident(name,email,mobile,number,family,gender,bitmapToBytes(), new AuthCallBack() {
            @Override
            public void onSuccess(String message) {
                addedDialog.startLoading();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        addedDialog.dismissDialog();
                        Intent intent = new Intent(getApplicationContext(),ActivityResidents.class);
                        startActivity(intent);
                        finish();
                    }
                },3200);

            }

            @Override
            public void onFailure(String message) {
                Snackbar.make(binding.getRoot(),message,Snackbar.LENGTH_LONG).show();

            }
        });


    }

    private byte[] bitmapToBytes() {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        imageBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        return stream.toByteArray();
    }

    private void controlGenderSelection() {
        binding.radiogroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                gender = checkedId == R.id.radiomale ? "M" : "F";
                Toast.makeText(AddResident.this, gender, Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void updateResident(){
        String name =binding.etFirstname.getText().toString().trim();
        String email = binding.etEmailUser.getText().toString().trim();
        String mobile = binding.etMobile.getText().toString().trim();
        String number =binding.etNationalNumber.getText().toString().trim();
        String family =binding.etFamilyMembers.getText().toString().trim();
        controller.updateResident(idResident,"PUT",name,email,mobile,number,family,gender,bitmapToBytes(), new AuthCallBack() {
            @Override
            public void onSuccess(String message) {
                updatedDialog.startLoading();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        updatedDialog.dismissDialog();
                        Intent intent = new Intent(getApplicationContext(),Resident.class);
                        startActivity(intent);
                        finish();
                    }
                },3000);

            }

            @Override
            public void onFailure(String message) {
                Snackbar.make(binding.getRoot(),message,Snackbar.LENGTH_LONG).show();

            }
        });

    }

}