package com.example.towerssystem.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.towerssystem.databinding.ItemResidentBinding;
import com.example.towerssystem.interfaces.ItemClickResident;
import com.example.towerssystem.models.Resident;
import com.squareup.picasso.Picasso;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {
    private List<Resident> residents;
    private ItemClickResident clickResident ;

    public UserAdapter(List<Resident> residents,ItemClickResident clickResident) {
        this.residents = residents;
        this.clickResident=clickResident;
    }

    public void setResidents(List<Resident> residents) {
        this.residents = residents;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemResidentBinding binding = ItemResidentBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new UserViewHolder(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        Resident resident = residents.get(position);
        holder.binding.tvUsername.setText(resident.name);
        holder.binding.tvItemMobile.setText(resident.mobile);
        holder.binding.tvItemTowresName.setText(resident.towerName);
        holder.binding.tvUserEmail.setText(resident.email);
        Picasso.get().load(resident.imageUrl).into(holder.binding.imag);

        holder.binding.card.setOnClickListener(v -> {
            clickResident.onClick(resident);
        });

    }

    @Override
    public int getItemCount() {
        return residents.size();
    }

    class UserViewHolder extends RecyclerView.ViewHolder{
        ItemResidentBinding binding;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = ItemResidentBinding.bind(itemView);
        }
    }
}
