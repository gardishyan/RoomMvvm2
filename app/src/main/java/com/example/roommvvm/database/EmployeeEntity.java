package com.example.roommvvm.database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "employees")
public class EmployeeEntity implements Serializable {
    @PrimaryKey(autoGenerate = true)
    public long id;

    @ColumnInfo(name = "name")
    public String name;

    public String getName() {
        return name;
    }

    @ColumnInfo(name = "salary")
    public Integer salary;

    public EmployeeEntity(String name, Integer salary) {
        this.name = name;
        this.salary = salary;
    }
}
