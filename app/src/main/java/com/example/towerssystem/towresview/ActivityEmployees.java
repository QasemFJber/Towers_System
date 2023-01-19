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
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.towerssystem.Broadcastreciver.NetworkChangeListiners;
import com.example.towerssystem.Dialog.CustomDialog;
import com.example.towerssystem.Dialog.DeletedDialog;
import com.example.towerssystem.R;
import com.example.towerssystem.adapters.EmployeeAdapter;
import com.example.towerssystem.controller.EmployeeController;
import com.example.towerssystem.databinding.ActivityEmployeesBinding;
import com.example.towerssystem.interfaces.AuthCallBack;
import com.example.towerssystem.interfaces.ContentCallBack;
import com.example.towerssystem.interfaces.Item_Click;
import com.example.towerssystem.models.Employee;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

public class ActivityEmployees extends AppCompatActivity  implements Item_Click {
    private ActivityEmployeesBinding binding;
    private EmployeeAdapter adapter  = new EmployeeAdapter(new ArrayList<>(),this);;
    private EmployeeController controller = new EmployeeController();
    NetworkChangeListiners networkChangeListiners = new NetworkChangeListiners();
    private CustomDialog dialog = new CustomDialog(this);
    private DeletedDialog deletedDialog = new DeletedDialog(this);
    private List<Employee> employees;
        private static int ID ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEmployeesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initializeView();


    }


    private void initializeView() {
        setOnClick();
        operationsSccren();
        getAllEmployees();
        dialogLoad();
        SwipeRight();
        SwipeLeft();
        DroptoReorder();
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

    private void setOnClick() {
    }

    private void operationsSccren() {
        setTitle("EMPLOYEES");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.yl)));
        getWindow().setStatusBarColor(ContextCompat.getColor(ActivityEmployees.this,R.color.black));
        setOnCilck();
    }

    private void setOnCilck() {
    }


    @Override
    protected void onStop() {
        unregisterReceiver(networkChangeListiners);
        super.onStop();
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
            Intent intent = new Intent(getApplicationContext(),AddEmployee.class);
            intent.putExtra("id",1);
            startActivity(intent);
        }else if (item.getItemId() == R.id.addOperations){
            Intent intent = new Intent(getApplicationContext(),AddOperations.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    private void SwipeRight(){
        new ItemTouchHelper(simpleCallback2).attachToRecyclerView(binding.rvEmployees);
    }
    private void SwipeLeft(){
        new ItemTouchHelper(simpleCallback).attachToRecyclerView(binding.rvEmployees);
    }
    private void DroptoReorder(){
        new ItemTouchHelper(callback).attachToRecyclerView(binding.rvEmployees);
    }

    private void getAllEmployees(){
        controller.getAllEmployees(new ContentCallBack<Employee>() {
            @Override
            public void onSuccess(List<Employee> list) {
                employees = list;
                adapter.setEmployees(list);
                adapter.notifyItemRangeInserted(0,list.size());
                binding.rvEmployees.setAdapter(adapter);
                binding.rvEmployees.setHasFixedSize(true);
                binding.rvEmployees.setLayoutManager(new LinearLayoutManager(ActivityEmployees.this));
                Log.d("API_REQUEST","LIST_SIZE"+list.size());
            }

            @Override
            public void onFailure(String message) {
                Snackbar.make(binding.getRoot(),message,Snackbar.LENGTH_LONG).show();

            }
        });
    }

    private void deleteEmployee(){
        controller.deleteEmployee(ID, new AuthCallBack() {
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

    String deleteData;


    @Override
    public void onClick(Employee employee) {
        ID = employee.id;
    }
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
                    Toast.makeText(ActivityEmployees.this, "UPDATE EMPLOYEE", Toast.LENGTH_SHORT).show();

                }
            }).show();


        }

        @Override
        public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
            new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                    .addBackgroundColor(ContextCompat.getColor(ActivityEmployees.this, R.color.teal_200))
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
            deleteEmployee();
            employees.remove(position);
            Snackbar.make(binding.getRoot(),deleteData,Snackbar.LENGTH_LONG).setAction("GERI ALL", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(ActivityEmployees.this, "RETRY", Toast.LENGTH_SHORT).show();

                }
            }).show();

        }

        @Override
        public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
            new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                    .addBackgroundColor(ContextCompat.getColor(ActivityEmployees.this, R.color.my_background))
                    .addActionIcon(R.drawable.ic_baseline_delete_24)
                    .addSwipeLeftLabel("DELETE ")
                    .setSwipeLeftLabelTextSize(1,33)
                    .setSwipeLeftLabelColor(R.color.white)
                    .create()
                    .decorate();
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        }
    };
    ItemTouchHelper.SimpleCallback callback = new ItemTouchHelper.SimpleCallback(ItemTouchHelper.DOWN | ItemTouchHelper.START | ItemTouchHelper.END,0) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            int fromPosition = viewHolder.getAdapterPosition();
            int toPosition = target.getAdapterPosition();
            Collections.swap(employees,fromPosition,toPosition);
            adapter.notifyItemMoved(fromPosition,toPosition);
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {


        }
    };
}