package com.example.towerssystem.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.towerssystem.databinding.ItemShowBinding;
import com.example.towerssystem.interfaces.DetailsClick;
import com.example.towerssystem.models.Resident;
import com.example.towerssystem.models.ShowCategorie;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ShowAdapter extends RecyclerView.Adapter<ShowAdapter.ShowViewHolder> {
    private List<ShowCategorie> categorieList;
    private DetailsClick click;

    public ShowAdapter(List<ShowCategorie> categorieList,DetailsClick click) {
        this.categorieList = categorieList;
        this.click=click;
    }

    public void setCategorieList(List<ShowCategorie> categorieList) {
        this.categorieList = categorieList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ShowViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemShowBinding binding = ItemShowBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new ShowViewHolder(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(@NonNull ShowViewHolder holder, int position) {
        ShowCategorie categorie = categorieList.get(position);

        holder.binding.tvIdCategorie.setText(categorie.categoryId);
        holder.binding.tvCategorieName.setText(categorie.categoryName);
        holder.binding.tvAmount.setText(categorie.amount);
        holder.binding.tvResidentName.setText(categorie.resident.name);
        holder.binding.tvDetilas.setText(categorie.details);
        holder.binding.tvDate.setText(categorie.date);
//        holder.binding.tvEmailRes.setText(categorie.resident.email);
//        holder.binding.tvTowresName.setText(categorie.resident.towerName);
//        Picasso.get().load(categorie.resident.imageUrl).into(holder.binding.imag);
        holder.binding.card.setOnClickListener(v -> {
            click.onClick(categorie);
        });

    }

    @Override
    public int getItemCount() {
        return categorieList.size();
    }

    static class ShowViewHolder extends RecyclerView.ViewHolder{
        ItemShowBinding binding;
        public ShowViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = ItemShowBinding.bind(itemView);
        }
    }
}
