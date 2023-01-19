package com.example.towerssystem.towresview;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
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
import android.widget.Toast;

import com.example.towerssystem.Broadcastreciver.NetworkChangeListiners;
import com.example.towerssystem.Dialog.AddedDialog;
import com.example.towerssystem.R;
import com.example.towerssystem.controller.AdvertisementsController;
import com.example.towerssystem.databinding.ActivityAddAdvertisementsBinding;
import com.example.towerssystem.interfaces.AuthCallBack;
import com.google.android.material.snackbar.Snackbar;

import java.io.ByteArrayOutputStream;

public class AddAdvertisements extends AppCompatActivity implements View.OnClickListener {
    private ActivityAddAdvertisementsBinding binding;
    NetworkChangeListiners networkChangeListiners = new NetworkChangeListiners();
    AdvertisementsController controller = new AdvertisementsController();
    private ActivityResultLauncher<String> permissionResultLauncher;
    private ActivityResultLauncher<Void> cameraResultLauncher;
    private Bitmap imageBitmap;
    private Dialog dialog;
    private Uri imagePick;
    AddedDialog addedDialog = new AddedDialog(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddAdvertisementsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initializeView();
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
        finish();
    }


    private void initializeView() {
        setOnClick();
        operationsSccren();
        setupResultsLauncher();
        checkIdOfActivity();
    }

    private void setOnClick() {
        binding.tvBack.setOnClickListener(this::onClick);
        binding.imageView2.setOnClickListener(this::onClick);
        binding.btnSave.setOnClickListener(this::onClick);
    }

    private void performData(){
        if (checkData()){
            saveOperations();
            Intent intent = new Intent(getApplicationContext(),Advertisements.class);
            startActivity(intent);
        }else {
            Toast.makeText(this, "ENTER REQUERD DATA", Toast.LENGTH_SHORT).show();
        }
    }

    private void saveOperations() {
    }

    private void operationsSccren() {
        setTitle("Advertisements");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.yl)));
        getWindow().setStatusBarColor(ContextCompat.getColor(AddAdvertisements.this,R.color.black));

    }

    private boolean checkData() {
        if (!binding.etTitle.getText().toString().isEmpty() &&
                !binding.etInfo.getText().toString().isEmpty()

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
                    binding.tvAddImage.setText("");
                }
            }
        });
    }

    private void pickImage() {
        permissionResultLauncher.launch(Manifest.permission.CAMERA);
    }

    private void checkIdOfActivity() {
        Intent intent = getIntent();
        int id =  intent.getIntExtra("id",0);
        if (id == 1){
            binding.btnSave.setText("SAVE");
            setTitle("ADD ADVERTISEMENT");
        }else {
            binding.btnSave.setText("UPDATE");
            setTitle("UPDATE ADVERTISEMENT");

        }
    }

    private byte[] bitmapToBytes() {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        imageBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        return stream.toByteArray();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_save){
            performData();
        }else if (v.getId() == R.id.imageView2){
            pickImage();
        }else if (v.getId() == R.id.tv_back) {
            Intent intent = new Intent(getApplicationContext(),Advertisements.class);
            startActivity(intent);
            finish();
        }
    }

    private void insertAdvertisements(){
        controller.insertAdvertisements(new com.example.towerssystem.models.Advertisements(), new AuthCallBack() {
            @Override
            public void onSuccess(String message) {
                addedDialog.startLoading();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        addedDialog.dismissDialog();
                        Intent intent = new Intent(getApplicationContext(),Advertisements.class);
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

    private void updateAdvertisements(){
        controller.updateAdvertisements(1, new AuthCallBack() {
            @Override
            public void onSuccess(String message) {
                addedDialog.startLoading();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        addedDialog.dismissDialog();
                        Intent intent = new Intent(getApplicationContext(),Operations.class);
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
}