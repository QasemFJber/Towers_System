package com.example.towerssystem.towresview;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.ColorDrawable;
import android.icu.text.DateFormat;
import android.icu.text.SimpleDateFormat;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Toast;

import com.example.towerssystem.Broadcastreciver.NetworkChangeListiners;
import com.example.towerssystem.Dialog.AddedDialog;
import com.example.towerssystem.Dialog.UpdatedDialog;
import com.example.towerssystem.R;
import com.example.towerssystem.controller.CategoriesController;
import com.example.towerssystem.controller.EmployeeController;
import com.example.towerssystem.controller.OperationsController;
import com.example.towerssystem.controller.ResidentController;
import com.example.towerssystem.databinding.ActivityAddOperationsBinding;
import com.example.towerssystem.interfaces.AuthCallBack;
import com.example.towerssystem.interfaces.ContentCallBack;
import com.example.towerssystem.models.Categorie;
import com.example.towerssystem.models.Employee;
import com.example.towerssystem.models.Resident;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class AddOperations extends AppCompatActivity  implements DatePickerDialog.OnDateSetListener  {
    private ActivityAddOperationsBinding binding;
    private OperationsController controller = new OperationsController();
    UpdatedDialog updatedDialog = new UpdatedDialog(this);
    NetworkChangeListiners networkChangeListiners = new NetworkChangeListiners();
    CategoriesController categoriesController = new CategoriesController();
    ResidentController residentController = new ResidentController();
    EmployeeController employeeController = new EmployeeController();
    String[] Actor_Type ={"Employee","Resident"};
    private  int Id_CATEGORY;
    private  String Id_TYPE;
    private String type;
    AddedDialog addedDialog = new AddedDialog(this);
    private static int operationID;
    private static int ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddOperationsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        operationsSccren();
        initializeView();
        Date date = new Date();
        Toast.makeText(this, "THE ID IS : "+ID, Toast.LENGTH_SHORT).show();
        Toast.makeText(this, "The operation id is :"+operationID, Toast.LENGTH_SHORT).show();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
        String strDate = formatter.format(date);
        binding.btnSave.setOnClickListener(v -> {
           performData();

        });
        binding.etDate.setOnClickListener(v -> {
            DialogFragment dialogFragment = new com.example.towerssystem.Fragments.DatePickerDialog();
            dialogFragment.show(getSupportFragmentManager(),"DATE PICKER");
        });
        binding.tvBack.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(),Operations.class);
            startActivity(intent);
        });
        binding.imageView4.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(),Operations.class);
            startActivity(intent);
        });
    }
    private void initializeView(){
        checkIdOfActivity();
        getAllCategory();
        getType();

    }


    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR,year);
        calendar.set(Calendar.MONTH,month);
        calendar.set(Calendar.DAY_OF_MONTH,dayOfMonth);
        if (year > 2023 ){
//            Toast.makeText(this, " PLEASE ENTER DATE ", Toast.LENGTH_SHORT).show();
        }else {
            String curruntDate = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());
            binding.etDate.setText(curruntDate);
        }
    }

    private void checkIdOfActivity() {
        Intent intent = getIntent();
         ID = intent.getIntExtra("id",0);
        operationID = intent.getIntExtra("operationsID",0);
        if (ID == 1){
            binding.btnSave.setText("SAVE");
            setTitle("ADD OPERATIONS");
        }else if (ID == 2){
            binding.btnSave.setText("UPDATE");
            setTitle("UPDATE OPERATIONS");

        }else {
//            Toast.makeText(this, "No  Found  Id", Toast.LENGTH_SHORT).show();
        }
    }


    private void operationsSccren() {
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.yl)));
        getWindow().setStatusBarColor(ContextCompat.getColor(AddOperations.this,R.color.black));

    }

    @Override
    protected void onStart() {
        super.onStart();
        IntentFilter intentFilter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(networkChangeListiners,intentFilter);
    }


    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(networkChangeListiners);
        finish();
    }

    private void performData(){
        if (checkData() && ID == 1){
            insert();
        }else if (checkData() && ID == 2){
            updateOperations();
        }else {
            Toast.makeText(this, "Enter Data Please", Toast.LENGTH_SHORT).show();
        }
    }
    private void insert(){
        if (Id_CATEGORY == 1){
            insertOperations();

        }else if (Id_CATEGORY ==  2){
            insertOperations();

        }else if (Id_CATEGORY == 3){
            insertOperationsToType();

        }else if(Id_CATEGORY == 4){
            insertOperationsToType();

        }
    }



    private boolean checkData() {
        if (
                        !binding.etAmount.getText().toString().isEmpty() &&
                        !binding.etDetails.getText().toString().isEmpty() &&
                        !binding.etDate.getText().toString().isEmpty()
        ) {
            return true;
        }
        return false;
    }





    private void updateOperations(){
        controller.updateOperations(operationID, String.valueOf(Id_CATEGORY), binding.etAmount.getText().toString().trim(), binding.etDetails.getText().toString().trim(), Id_TYPE,type, binding.etDate.getText().toString().trim(), new AuthCallBack() {
            @Override
            public void onSuccess(String message) {
                updatedDialog.startLoading();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        updatedDialog.dismissDialog();
                        Intent intent = new Intent(getApplicationContext(),Operations.class);
                        startActivity(intent);
                    }
                },2000);

            }

            @Override
            public void onFailure(String message) {
                Snackbar.make(binding.getRoot(),message,Snackbar.LENGTH_LONG).show();

            }
        });
    }


    private void getAllCategory(){
        categoriesController.getAllCategories(new ContentCallBack<Categorie>() {
            @Override
            public void onSuccess(List<Categorie> list) {
                ArrayList<String> name = new ArrayList<>();
                for (int i = 0; i < list.size(); i++) {
                    name.add(list.get(i).name);
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(AddOperations.this, com.airbnb.lottie.R.layout.support_simple_spinner_dropdown_item,name);
                    adapter.setDropDownViewResource(androidx.constraintlayout.widget.R.layout.support_simple_spinner_dropdown_item);
                    binding.spinnerCategory.setAdapter(adapter);
                    binding.spinnerCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            Id_CATEGORY = position+1;
                            Toast.makeText(AddOperations.this, "The Category Id is : "+Id_CATEGORY, Toast.LENGTH_SHORT).show();
                            if (Id_CATEGORY == 3 ){
                                binding.tvNationalNumber.setVisibility(View.INVISIBLE);
                                binding.tvFamilyMembers.setVisibility(View.INVISIBLE);
                                binding.spinnerActorType.setVisibility(View.INVISIBLE);
                                binding.spinnerActorId.setVisibility(View.INVISIBLE);

                            }else if (Id_CATEGORY == 4){
                                binding.tvNationalNumber.setVisibility(View.INVISIBLE);
                                binding.tvFamilyMembers.setVisibility(View.INVISIBLE);
                                binding.spinnerActorType.setVisibility(View.INVISIBLE);
                                binding.spinnerActorId.setVisibility(View.INVISIBLE);
                            }else if (Id_CATEGORY == 1){
                                binding.tvNationalNumber.setVisibility(View.VISIBLE);
                                binding.tvFamilyMembers.setVisibility(View.VISIBLE);
                                binding.spinnerActorType.setVisibility(View.VISIBLE);
                                binding.spinnerActorId.setVisibility(View.VISIBLE);
                            }else  if (Id_CATEGORY == 2){
                                binding.tvNationalNumber.setVisibility(View.VISIBLE);
                                binding.tvFamilyMembers.setVisibility(View.VISIBLE);
                                binding.spinnerActorType.setVisibility(View.VISIBLE);
                                binding.spinnerActorId.setVisibility(View.VISIBLE);
                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                }

            }

            @Override
            public void onFailure(String message) {

            }
        });

    }
    private void getType(){
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(AddOperations.this, com.airbnb.lottie.R.layout.support_simple_spinner_dropdown_item,Actor_Type);
        adapter.setDropDownViewResource(androidx.constraintlayout.widget.R.layout.support_simple_spinner_dropdown_item);
        binding.spinnerActorType.setAdapter(adapter);
        binding.spinnerActorType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                            Toast.makeText(AddOperations.this,, Toast.LENGTH_SHORT).show();
                 type = parent.getItemAtPosition(position).toString();
                if (type.equals("Employee")){
                    getAllEmployee();

                }else {
                    getAllResident();
                }

//                Toast.makeText(AddOperations.this, "The Type is :"+type, Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
    private void getAllResident(){
        residentController.getAllResident(new ContentCallBack<Resident>() {
            @Override
            public void onSuccess(List<Resident> list) {
                ArrayList<Integer> name = new ArrayList<>();
                for (int i = 0; i < list.size(); i++) {
                    name.add(list.get(i).id);
                    ArrayAdapter<Integer> adapter = new ArrayAdapter<Integer>(AddOperations.this, com.airbnb.lottie.R.layout.support_simple_spinner_dropdown_item,name);
                    adapter.setDropDownViewResource(androidx.constraintlayout.widget.R.layout.support_simple_spinner_dropdown_item);
                    binding.spinnerActorId.setAdapter(adapter);
                    binding.spinnerActorId.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                            Toast.makeText(AddOperations.this,, Toast.LENGTH_SHORT).show();
                             Id_TYPE = parent.getItemAtPosition(position).toString();

                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                }

            }

            @Override
            public void onFailure(String message) {

            }
        });

    }
    private void getAllEmployee(){
        employeeController.getAllEmployees(new ContentCallBack<Employee>() {
            @Override
            public void onSuccess(List<Employee> list) {
                ArrayList<Integer> name = new ArrayList<>();
                for (int i = 0; i < list.size(); i++) {
                    name.add(list.get(i).id);
                    ArrayAdapter<Integer> adapter = new ArrayAdapter<Integer>(AddOperations.this, com.airbnb.lottie.R.layout.support_simple_spinner_dropdown_item,name);
                    adapter.setDropDownViewResource(androidx.constraintlayout.widget.R.layout.support_simple_spinner_dropdown_item);
                    binding.spinnerActorId.setAdapter(adapter);
                    binding.spinnerActorId.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                            Toast.makeText(AddOperations.this,, Toast.LENGTH_SHORT).show();
                            Id_TYPE = parent.getItemAtPosition(position).toString();
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                }

            }

            @Override
            public void onFailure(String message) {

            }
        });

    }
    private void insertOperations(){
        controller.insertOperations(String.valueOf(Id_CATEGORY), binding.etAmount.getText().toString().trim(), binding.etDetails.getText().toString().trim(), Id_TYPE,type, binding.etDate.getText().toString().trim(), new AuthCallBack() {
            @Override
            public void onSuccess(String message) {
               addedDialog.startLoading();
               new Handler().postDelayed(new Runnable() {
                   @Override
                   public void run() {
                       addedDialog.dismissDialog();
                       Intent intent = new Intent(getApplicationContext(),Operations.class);
                       startActivity(intent);
                   }
               },2000);


            }

            @Override
            public void onFailure(String message) {
                Snackbar.make(binding.getRoot(),message,Snackbar.LENGTH_LONG).show();

            }
        });
    }
    private void insertOperationsToType(){
        controller.insertOperations(String.valueOf(Id_CATEGORY), binding.etAmount.getText().toString().trim(), binding.etDetails.getText().toString().trim(), null,null, binding.etDate.getText().toString().trim(), new AuthCallBack() {
            @Override
            public void onSuccess(String message) {
                addedDialog.startLoading();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        addedDialog.dismissDialog();
                        Intent intent = new Intent(getApplicationContext(),Operations.class);
                        startActivity(intent);
                    }
                },2000);



            }

            @Override
            public void onFailure(String message) {
                Snackbar.make(binding.getRoot(),message,Snackbar.LENGTH_LONG).show();

            }
        });
    }


}