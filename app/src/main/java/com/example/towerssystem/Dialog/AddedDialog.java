package com.example.towerssystem.Dialog;

import android.app.Activity;
import android.view.LayoutInflater;

import androidx.appcompat.app.AlertDialog;

import com.example.towerssystem.R;

public class AddedDialog {
    private AlertDialog dialog;
    private Activity activity;

    public AddedDialog(Activity activity1){
        activity =activity1;
    }

    public void startLoading(){
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);

        LayoutInflater layoutInflater = activity.getLayoutInflater();
        builder.setView(layoutInflater.inflate(R.layout.addedcustom,null));
        builder.setCancelable(false);

        dialog = builder.create();
        dialog.show();
    }


    public void dismissDialog(){
        dialog.dismiss();
    }
}
