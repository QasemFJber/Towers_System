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
import com.example.towerssystem.adapters.AdvertisementsAdapter;
import com.example.towerssystem.controller.AdvertisementsController;
import com.example.towerssystem.databinding.ActivityAdvertisementsBinding;
import com.example.towerssystem.interfaces.AuthCallBack;
import com.example.towerssystem.interfaces.ClickItemRecycler;
import com.example.towerssystem.interfaces.ContentCallBack;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

public class Advertisements extends AppCompatActivity implements ClickItemRecycler {
    ActivityAdvertisementsBinding binding;
    NetworkChangeListiners networkChangeListiners = new NetworkChangeListiners();
    private AdvertisementsController controller = new AdvertisementsController();
    private AdvertisementsAdapter adapter = new AdvertisementsAdapter(new ArrayList<>(),this) ;
    private CustomDialog dialog = new CustomDialog(this);
    private List<com.example.towerssystem.models.Advertisements> advertisements;
    private DeletedDialog deletedDialog = new DeletedDialog(this);
    private static int ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAdvertisementsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initializeView();
    }


    private void initializeView(){
        operationsSccren();
        getAllAdvertisements();
        dialogLoad();
        SwipeRight();
        SwipeLeft();
        DropToReorder();

    }
    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(networkChangeListiners);
    }

    private void dialogLoad() {
        dialog.startLoading();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                dialog.dismissDialog();
            }
        },3000);
    }

    private void operationsSccren() {
        setTitle("Advertisements");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.yl)));
        getWindow().setStatusBarColor(ContextCompat.getColor(Advertisements.this,R.color.black));

    }

    @Override
    protected void onStart() {
        super.onStart();
        IntentFilter intentFilter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(networkChangeListiners,intentFilter);
    }
    private void getAllAdvertisements (){
        controller.getAllAdvertisements(new ContentCallBack<com.example.towerssystem.models.Advertisements>() {
            @Override
            public void onSuccess(List<com.example.towerssystem.models.Advertisements> list) {
                advertisements = list;
                adapter.setAdvertisementsList(list);
                binding.rvAdvertisements.setAdapter(adapter);
                binding.rvAdvertisements.setHasFixedSize(true);
                binding.rvAdvertisements.setLayoutManager(new LinearLayoutManager(Advertisements.this));


            }

            @Override
            public void onFailure(String message) {
                Snackbar.make(binding.getRoot(),message,Snackbar.LENGTH_LONG).show();

            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menuempanuser, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.add) {
            Intent intent = new Intent(getApplicationContext(), AddAdvertisements.class);
            intent.putExtra("id",1);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    private void DropToReorder(){
        new ItemTouchHelper(callback).attachToRecyclerView(binding.rvAdvertisements);
    }
    private void SwipeRight(){
        new ItemTouchHelper(simpleCallback2).attachToRecyclerView(binding.rvAdvertisements);
    }
    private void SwipeLeft(){
        new ItemTouchHelper(simpleCallback).attachToRecyclerView(binding.rvAdvertisements);
    }

    @Override
    public void onClick(com.example.towerssystem.models.Advertisements advertisements) {
        ID = advertisements.id;

    }
    private void deleteAdvertisements() {
        controller.deleteAdvertisements(ID, new AuthCallBack() {
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
            Collections.swap(advertisements,fromPosition,toPosition);
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
            Intent intent = new Intent(getApplicationContext(),AddEmployee.class);
            intent.putExtra("id",2);
            startActivity(intent);
            Snackbar.make(binding.getRoot(),deleteData,Snackbar.LENGTH_LONG).setAction("GERI ALL", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(Advertisements.this, "UPDATE EMPLOYEE", Toast.LENGTH_SHORT).show();

                }
            }).show();


        }

        @Override
        public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
            new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                    .addBackgroundColor(ContextCompat.getColor(Advertisements.this, R.color.teal_200))
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
            int position = viewHolder.getAdapterPosition();
            deleteAdvertisements();
            advertisements.remove(position);
            Snackbar.make(binding.getRoot(),deleteData,Snackbar.LENGTH_LONG).setAction("GERI ALL", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(Advertisements.this, "RETRY", Toast.LENGTH_SHORT).show();

                }
            }).show();

        }

        @Override
        public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
            new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                    .addBackgroundColor(ContextCompat.getColor(Advertisements.this, R.color.my_background))
                    .addActionIcon(R.drawable.ic_baseline_delete_24)
                    .addSwipeLeftLabel("DELETE ")
                    .setSwipeLeftLabelTextSize(1,33)
                    .setSwipeLeftLabelColor(R.color.white)
                    .create()
                    .decorate();
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        }
    };

    private void insertAdvertisement(){
        controller.insertAdvertisements(advertisements.get(1), new AuthCallBack() {
            @Override
            public void onSuccess(String message) {
                Snackbar.make(binding.getRoot(),message,Snackbar.LENGTH_LONG).show();
                Intent intent = new Intent(getApplicationContext(),Advertisements.class);
                startActivity(intent);
            }

            @Override
            public void onFailure(String message) {
                Snackbar.make(binding.getRoot(),message,Snackbar.LENGTH_LONG).show();
            }
        });

    }



}