package com.example.towerssystem.Broadcastreciver;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;

import androidx.appcompat.widget.AppCompatButton;

import com.example.towerssystem.R;
import com.example.towerssystem.controller.InternetControllers;

public class NetworkChangeListiners extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (!InternetControllers.isConnectedToInternet(context)){
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            View view = LayoutInflater.from(context).inflate(R.layout.internet_check,null);
            builder.setView(view);

            AppCompatButton button = view.findViewById(R.id.btn_retry);

            AlertDialog alertDialog = builder.create();
            alertDialog.show();
            alertDialog.setCancelable(false);
            alertDialog.getWindow().setGravity(Gravity.CENTER);

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alertDialog.dismiss();
                    onReceive(context,intent);
                }
            });

        }
    }
}
