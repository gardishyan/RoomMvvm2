package com.example.roommvvm;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.example.roommvvm.database.AppDatabase;
import com.example.roommvvm.database.DatabaseClient;
import com.example.roommvvm.database.EmployeeEntity;

import java.util.List;
import java.util.stream.Collectors;

public class EmployeeViewModel extends AndroidViewModel {
    private MutableLiveData<List<EmployeeEntity>> employees;
    private AppDatabase appDatabase;
    private String nameFilter;

    public EmployeeViewModel(@NonNull Application application) {
        super(application);
        Log.i("UWC", "ViewModel created");
        employees = new MutableLiveData<>();
        appDatabase = DatabaseClient.getInstance(getApplication()).getAppDatabase();

        AsyncTask.execute(() -> {
            Log.i("UWC", "ViewModel thread started DB reading");
            refreshEmployeeList();
        });
    }

    public LiveData<List<EmployeeEntity>> getEmployees() {
        return employees;
    }


    public void addEmployee(String name, Integer salary) {
        Log.i("UWC", "Adding new employee: " + name);
        //DB access must be done from background thread
        AsyncTask.execute(() -> {
            appDatabase.employeeDao().insertEmployee(new EmployeeEntity(name, salary));
            refreshEmployeeList();
        });
    }

    public void deleteEmployee(EmployeeEntity employee){
        AsyncTask.execute(() -> {
            appDatabase.employeeDao().deleteEmployee(employee);
            refreshEmployeeList();
        });
    }

    public void setSearchFilter(String f) {
        nameFilter = f.toLowerCase();
        AsyncTask.execute(() -> {
            refreshEmployeeList();
        });
    }
    private void refreshEmployeeList() {
        List<EmployeeEntity> all = appDatabase.employeeDao().getAll();
        if(nameFilter != null && !nameFilter.isEmpty()) {
            List<EmployeeEntity> filteredEmployees = all.stream().filter(employee -> employee.getName().toLowerCase().contains(nameFilter)).collect(Collectors.toList());
            employees.postValue(filteredEmployees);
        } else {
            employees.postValue(all);
        }
    }
}