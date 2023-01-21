package com.example.towerssystem.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.towerssystem.databinding.ItemAdvertisementsBinding;
import com.example.towerssystem.interfaces.ClickItemRecycler;
import com.example.towerssystem.models.Advertisements;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AdvertisementsAdapter extends RecyclerView.Adapter<AdvertisementsAdapter.AdvertisementsViewHolder> {
    private List<Advertisements> advertisementsList;
    private ClickItemRecycler clickItemRecycler;

    public AdvertisementsAdapter(List<Advertisements> advertisementsList,ClickItemRecycler clickItemRecycler) {
        this.advertisementsList = advertisementsList;
        this.clickItemRecycler=clickItemRecycler;
    }

    public void setAdvertisementsList(List<Advertisements> advertisementsList) {
        this.advertisementsList = advertisementsList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public AdvertisementsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemAdvertisementsBinding binding = ItemAdvertisementsBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);

        return new AdvertisementsViewHolder(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(@NonNull AdvertisementsViewHolder holder, int position) {
        Advertisements advertisements = advertisementsList.get(position);
        holder.binding.tvItemTitle.setText(advertisements.title);
        holder.binding.tvItemInfo.setText(advertisements.info);
        Picasso.get().load(advertisements.imageUrl).into(holder.binding.imag);
        holder.binding.card.setOnClickListener(v -> {
            clickItemRecycler.onClick(advertisements);

        });


    }

    @Override
    public int getItemCount() {
        return advertisementsList.size();
    }

    static class AdvertisementsViewHolder extends RecyclerView.ViewHolder{

        ItemAdvertisementsBinding binding;
        public AdvertisementsViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = ItemAdvertisementsBinding.bind(itemView);
        }
    }

}
