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
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.towerssystem.controller.ResidentController;
import com.example.towerssystem.R;
import com.example.towerssystem.databinding.ActivityAddUserBinding;
import com.example.towerssystem.interfaces.AuthCallBack;
import com.example.towerssystem.models.Resident;

import java.io.ByteArrayOutputStream;

public class AddUser extends AppCompatActivity implements View.OnClickListener {
    ActivityAddUserBinding binding;

    private ActivityResultLauncher<String> permissionResultLauncher;
    private ActivityResultLauncher<Void> cameraResultLauncher;
    private Bitmap imageBitmap;
    private Dialog dialog;
    private Uri imagePick;
    private String gender;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding  = ActivityAddUserBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
      initializeView();

    }
    private void initializeView() {
        setOnCilck();
        OperationsSccren();
        setupResultsLauncher();
        checkIdOfActivity();
    }

    private void checkIdOfActivity() {
        Intent intent = getIntent();
       int id =  intent.getIntExtra("id",0);
       if (id == 1){
           binding.btnSave.setText("Save");
           setTitle("ADD USER");
       }else {
           binding.btnSave.setText("UPDATE");
           setTitle("UPDATE USER");

       }
    }

    private void OperationsSccren() {

        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.yl)));
        getWindow().setStatusBarColor(ContextCompat.getColor(AddUser.this,R.color.black));
        setOnCilck();

    }


    private void setOnCilck() {
        binding.imageView2.setOnClickListener(this::onClick);
        binding.btnSave.setOnClickListener(this::onClick);
        binding.tvBack.setOnClickListener(this::onClick);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_save){
            performData();
        }else if (v.getId() == R.id.imageView2){
            pickImage();
        }else if (v.getId() == R.id.tv_back) {
            Intent intent = new Intent(getApplicationContext(),ActivityUsers.class);
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
        if (checkData()){
            saveResident();
        }else {
            Toast.makeText(this, "ENTER REQUERD DATA", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean checkData() {
        if (!binding.etFirstname.getText().toString().isEmpty() &&
                !binding.etMobile.getText().toString().isEmpty() &&
                !binding.etNationalNumber.getText().toString().isEmpty()&&
                bitmapToBytes() !=null
        ) {
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
                    binding.imageView3.setVisibility(View.GONE);
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
        Resident resident = new Resident(name,email,mobile,number,family,gender,bitmapToBytes());
        ResidentController residentController = new ResidentController();
        residentController.insertResident(resident, new AuthCallBack() {
            @Override
            public void onSuccess(String message) {

            }

            @Override
            public void onFailure(String message) {

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
            }
        });
    }

}