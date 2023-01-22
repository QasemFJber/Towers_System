package com.example.towerssystem.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.towerssystem.databinding.ItemOperationsBinding;
import com.example.towerssystem.interfaces.ClickItem;
import com.example.towerssystem.models.Operations;
import com.squareup.picasso.Picasso;

import java.util.List;

public class OperationsAdapter extends RecyclerView.Adapter<OperationsAdapter.OperationsViewHolder> {
    private List<Operations> operationsList ;
    private ClickItem clickItem;

    public OperationsAdapter(List<Operations> operationsList,ClickItem clickItem) {
        this.operationsList = operationsList;
        this.clickItem = clickItem;
    }

    public void setOperationsList(List<Operations> operationsList) {
        this.operationsList = operationsList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public OperationsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemOperationsBinding binding = ItemOperationsBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new OperationsViewHolder(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(@NonNull OperationsViewHolder holder, int position) {
        Operations operations = operationsList.get(position);
        holder.binding.tvItemAmount.setText(operations.amount);
        holder.binding.tvItemDate.setText(operations.date);
        holder.binding.tvItemDetails.setText(operations.details);
        holder.binding.tvCategoryName.setText(operations.categoryName);
        Picasso.get().load(operations.resident.imageUrl).into(holder.binding.imag);

        holder.binding.card.setOnClickListener(v -> {
            clickItem.onClick(operations);
        });

    }

    @Override
    public int getItemCount() {
        return operationsList.size();
    }

    class OperationsViewHolder extends RecyclerView.ViewHolder{
        ItemOperationsBinding binding;

        public OperationsViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = ItemOperationsBinding.bind(itemView);
        }
    }
}
