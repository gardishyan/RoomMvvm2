package com.example.roommvvm;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddFragment extends Fragment {
    private TextView addButton;
    private TextView itemCount;

    public AddFragment() {
        // Required empty public constructor
    }

    public static AddFragment newInstance() {
        AddFragment fragment = new AddFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        addButton = view.findViewById(R.id.add_item);
        itemCount = view.findViewById(R.id.item_count);
        EmployeeViewModel employeeViewModel = new ViewModelProvider(getActivity()).get(EmployeeViewModel.class);
        addButton.setOnClickListener(v -> employeeViewModel.addEmployee("Ruben Melikyan", 7774555));

        employeeViewModel.getEmployees().observe(getActivity(), employees ->  {
            Log.i("UWC", "Employees live data in fragment: ");
            itemCount.setText(String.valueOf(employees.size()));
        });
    }

}