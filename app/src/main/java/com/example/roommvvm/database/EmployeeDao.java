package com.example.roommvvm.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import java.util.List;

@Dao
public interface EmployeeDao {
    @Query("SELECT * FROM employees")
    List<EmployeeEntity> getAll();

    @Insert
    long insertEmployee(EmployeeEntity employeeEntity);

    @Query("DELETE FROM employees WHERE salary > :top")
    void deleteEmployeeBySalary(Integer top);

    @Delete
    void deleteEmployee(EmployeeEntity employeeEntity);
}