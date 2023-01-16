package com.example.towerssystem.adapters;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class OperationsAdapter extends RecyclerView.Adapter<OperationsAdapter.OperationsViewHolder> {

    @NonNull
    @Override
    public OperationsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull OperationsViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class OperationsViewHolder extends RecyclerView.ViewHolder{

        public OperationsViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
