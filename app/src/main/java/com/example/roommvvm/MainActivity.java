package com.example.roommvvm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.roommvvm.database.EmployeeEntity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private EmployeeAdapter adapter;
    private EmployeeViewModel employeeViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.i("UWC", "Activity created");
        //RecyclerView init to display the data
        RecyclerView rv = findViewById(R.id.item_list);
        rv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        adapter = new EmployeeAdapter();
        rv.setAdapter(adapter);

        //Retrieving data from ViewModel and passing to RecyclerView
//        EmployeeViewModel employeeViewModel = new EmployeeViewModel(getApplication());
        employeeViewModel = new ViewModelProvider(this).get(EmployeeViewModel.class);

        employeeViewModel.getEmployees().observe(this, employees ->  {
            Log.i("UWC", "Employees live data changed");
            adapter.setEmployees(employees);
        });

        TextView searchBtn = findViewById(R.id.search_btn);
        EditText searchText = findViewById(R.id.search_bar);

        searchBtn.setOnClickListener(v -> {
            employeeViewModel.setSearchFilter(searchText.getText().toString());
        });
    }
    private class EmployeeAdapter extends RecyclerView.Adapter<EmployeeAdapter.EmployeeCardHolder> {
        List<EmployeeEntity> employees;

        public void setEmployees(List<EmployeeEntity> employees) {
            this.employees = new ArrayList<>(employees);
            notifyDataSetChanged();
        }

        @NonNull
        @Override
        public EmployeeAdapter.EmployeeCardHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            Context context = parent.getContext();
            LayoutInflater inflater = LayoutInflater.from(context);
            return new EmployeeAdapter.EmployeeCardHolder(inflater.inflate(R.layout.view_employee_item, parent, false));
        }
        @Override
        public void onBindViewHolder(@NonNull EmployeeAdapter.EmployeeCardHolder holder, int position) {
            holder.id.setText(String.valueOf(employees.get(position).id));
            holder.name.setText(employees.get(position).name);
            holder.salary.setText(String.valueOf(employees.get(position).salary));
        }

        @Override
        public int getItemCount() {
            return employees != null ? employees.size() : 0;
        }
        class EmployeeCardHolder extends RecyclerView.ViewHolder {
            TextView id;
            TextView name;
            TextView salary;
            ImageView delete;

            public EmployeeCardHolder(@NonNull View itemView) {
                super(itemView);
                id = itemView.findViewById(R.id.employee_id);
                name = itemView.findViewById(R.id.employee_name);
                salary = itemView.findViewById(R.id.employee_salary);
                delete = itemView.findViewById(R.id.employee_delete);

                delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        employeeViewModel.deleteEmployee(employees.get(getAdapterPosition()));
                    }
                });
            }
        }
    }
}