package com.example.towerssystem.Dialog;

import android.app.Activity;
import android.view.LayoutInflater;

import androidx.appcompat.app.AlertDialog;

import com.example.towerssystem.R;

public class CustomDialog {
    private AlertDialog dialog;
    private Activity activity;

    public CustomDialog(Activity activity1){
        activity =activity1;
    }

   public void startLoading(){
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);

        LayoutInflater layoutInflater = activity.getLayoutInflater();
        builder.setView(layoutInflater.inflate(R.layout.custom_dialog,null));
        builder.setCancelable(false);

        dialog = builder.create();
        dialog.show();
    }


   public void dismissDialog(){
        dialog.dismiss();
    }
}
