package com.example.towerssystem.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.towerssystem.databinding.ItemCategorieBinding;
import com.example.towerssystem.interfaces.CategoryClickRecycler;
import com.example.towerssystem.models.Categorie;

import java.util.List;

public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.CategoriesViewHolder> {

    private List<Categorie> categories ;
    private CategoryClickRecycler categoryClickRecycler;

    public CategoriesAdapter(List<Categorie> categories, CategoryClickRecycler categoryClickRecycler) {
        this.categories = categories;
        this.categoryClickRecycler=categoryClickRecycler;
    }

    public void setCategories(List<Categorie> categories) {
        this.categories = categories;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CategoriesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemCategorieBinding binding = ItemCategorieBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new CategoriesViewHolder(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(@NonNull CategoriesViewHolder holder, int position) {
        Categorie categorie = categories.get(position);
        holder.binding.tvName.setText(categorie.name);
        holder.binding.tvActionsCount.setText(categorie.actionsCount);
        holder.binding.tvTotal.setText(categorie.total);

        holder.binding.card.setOnClickListener(v -> {
            categoryClickRecycler.onClick(categorie);
        });

    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    class CategoriesViewHolder extends RecyclerView.ViewHolder{
        ItemCategorieBinding binding;
        public CategoriesViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = ItemCategorieBinding.bind(itemView);
        }
    }
}
