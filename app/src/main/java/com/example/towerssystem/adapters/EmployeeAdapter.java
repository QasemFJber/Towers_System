package com.example.towerssystem.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.towerssystem.databinding.ItemCategorieBinding;
import com.example.towerssystem.databinding.ItemEmployeeBinding;
import com.example.towerssystem.models.Categorie;
import com.example.towerssystem.models.Employee;
import com.example.towerssystem.towresview.Home;
import com.squareup.picasso.Picasso;

import java.util.List;

public class EmployeeAdapter extends RecyclerView.Adapter<EmployeeAdapter.EmployeeViewHolder>{
    private List<Employee> employees ;

    public EmployeeAdapter(List<Employee> employees) {
        this.employees = employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    @NonNull
    @Override
    public EmployeeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemEmployeeBinding binding = ItemEmployeeBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);

        return new EmployeeViewHolder(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(@NonNull EmployeeViewHolder holder, int position) {
        Employee employee = employees.get(position);
        holder.binding.tvEmployeeUsername.setText(employee.name);
        holder.binding.tvItememployeeMobile.setText(employee.mobile);
        holder.binding.tvItememployeeTowresName.setText(employee.towerName);
        Picasso.get().load(employee.imageUrl).into(holder.binding.imag);

    }

    @Override
    public int getItemCount() {
        return employees.size();
    }

    class EmployeeViewHolder extends RecyclerView.ViewHolder{
        ItemEmployeeBinding binding;

        public EmployeeViewHolder(@NonNull View itemView) {
            super(itemView);
            ItemEmployeeBinding.bind(itemView);
        }
    }
}
