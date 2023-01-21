package com.example.towerssystem.towresview;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Canvas;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.towerssystem.Broadcastreciver.NetworkChangeListiners;
import com.example.towerssystem.Dialog.CustomDialog;
import com.example.towerssystem.Dialog.DeletedDialog;
import com.example.towerssystem.R;
import com.example.towerssystem.adapters.OperationsAdapter;
import com.example.towerssystem.controller.OperationsController;
import com.example.towerssystem.databinding.ActivityOperationsBinding;
import com.example.towerssystem.interfaces.AuthCallBack;
import com.example.towerssystem.interfaces.ClickItem;
import com.example.towerssystem.interfaces.ContentCallBack;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

public class Operations extends AppCompatActivity implements ClickItem {
    private ActivityOperationsBinding binding;
    private OperationsController controller = new OperationsController();
    private OperationsAdapter adapter = new OperationsAdapter(new ArrayList<>(),this);
    NetworkChangeListiners networkChangeListiners = new NetworkChangeListiners();
    private CustomDialog dialog = new CustomDialog(this);
    private List<com.example.towerssystem.models.Operations> operations;
    private DeletedDialog deletedDialog = new DeletedDialog(this);
    private static int ID;
    private static int  POSITION;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOperationsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initializeView();
    }
    private void initializeView(){

        OperationsSccren();
        getAllOperations();
        dialogLoad();
        SwipeRight();
        SwipeLeft();
        DroptoReorder();

    }
    private void OperationsSccren() {
        setTitle("Operations");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.yl)));
        getWindow().setStatusBarColor(ContextCompat.getColor(Operations.this,R.color.black));

    }





    private void dialogLoad() {
        dialog.startLoading();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                dialog.dismissDialog();
            }
        },2000);
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(networkChangeListiners);
        onBackPressed();
    }

    @Override
    protected void onStart() {
        super.onStart();
        IntentFilter intentFilter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(networkChangeListiners,intentFilter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menuempanuser, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.add) {
            Intent intent = new Intent(getApplicationContext(), AddOperations.class);
            intent.putExtra("id",1);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    private void getAllOperations(){
        controller.getAllOperations(new ContentCallBack<com.example.towerssystem.models.Operations>() {
            @Override
            public void onSuccess(List<com.example.towerssystem.models.Operations> list) {
                operations = list;
                if (list.size() ==0){
                    dialog.dismissDialog();
                    binding.tvData.setVisibility(View.VISIBLE);
                    binding.nodata.setVisibility(View.VISIBLE);
                }
                adapter.setOperationsList(list);
                binding.rvOperations.setAdapter(adapter);
                binding.rvOperations.setHasFixedSize(true);
                binding.rvOperations.setLayoutManager(new LinearLayoutManager(Operations.this));
            }

            @Override
            public void onFailure(String message) {
                Snackbar.make(binding.getRoot(),message,Snackbar.LENGTH_LONG).show();

            }
        });
    }

    @Override
    public void onClick(com.example.towerssystem.models.Operations operations) {
        Intent intent = new Intent(getApplicationContext(),ResEmpAvdDetails.class);
        intent.putExtra("categoryName",operations.categoryName);
        intent.putExtra("amount",operations.amount);
        intent.putExtra("employee_name",operations.employee.name);
        intent.putExtra("image",operations.employee.imageUrl);
        intent.putExtra("date",operations.date);
        intent.putExtra("details",operations.details);
        intent.putExtra("iddetails",4);
        startActivity(intent);

    }

    private void DroptoReorder(){
        new ItemTouchHelper(callback).attachToRecyclerView(binding.rvOperations);
    }
    private void SwipeRight(){
        new ItemTouchHelper(simpleCallback2).attachToRecyclerView(binding.rvOperations);
    }
    private void SwipeLeft(){
        new ItemTouchHelper(simpleCallback).attachToRecyclerView(binding.rvOperations);
    }

    private  void deleteOperations(int id){
        controller.deleteOperations(id, new AuthCallBack() {
            @Override
            public void onSuccess(String message) {
                deletedDialog.startLoading();
                adapter.notifyDataSetChanged();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        deletedDialog.dismissDialog();
                    }
                },2000);

            }

            @Override
            public void onFailure(String message) {
                Snackbar.make(binding.getRoot(),message,Snackbar.LENGTH_LONG).show();
            }
        });

    }


    ItemTouchHelper.SimpleCallback callback = new ItemTouchHelper.SimpleCallback(ItemTouchHelper.DOWN | ItemTouchHelper.START | ItemTouchHelper.END,0) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            int fromPosition = viewHolder.getAdapterPosition();
            int toPosition = target.getAdapterPosition();
            Collections.swap(operations,fromPosition,toPosition);
            adapter.notifyItemMoved(fromPosition,toPosition);
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

        }
    };
    String deleteData;

    ItemTouchHelper.SimpleCallback simpleCallback2 = new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.RIGHT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            int position = viewHolder.getAdapterPosition();
            int id = operations.get(position).id;
            Intent intent = new Intent(getApplicationContext(),AddOperations.class);
            intent.putExtra("id",2);
            intent.putExtra("operationsID",id);
            startActivity(intent);


        }

        @Override
        public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
            new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                    .addBackgroundColor(ContextCompat.getColor(Operations.this, R.color.teal_200))
                    .addActionIcon(R.drawable.ic_baseline_loop_24)
                    .addSwipeRightLabel("UPDATE ")
                    .setSwipeRightLabelTextSize(1,33)
                    .setSwipeRightLabelColor(R.color.white)
                    .create()
                    .decorate();
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        }
    };

    ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            POSITION = viewHolder.getAdapterPosition();
            int id = operations.get(POSITION).id;
            deleteOperations(id);
            operations.remove(POSITION);


        }

        @Override
        public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
            new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                    .addBackgroundColor(ContextCompat.getColor(Operations.this, R.color.my_background))
                    .addActionIcon(R.drawable.ic_baseline_delete_24)
                    .addSwipeLeftLabel("DELETE ")
                    .setSwipeLeftLabelTextSize(1,33)
                    .setSwipeLeftLabelColor(R.color.white)
                    .create()
                    .decorate();
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        }
    };


}