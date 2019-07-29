package com.codecool.dao;

import com.codecool.model.Employee;

public class FakeDB {
    public static void save(Employee employee) {
        System.out.println("Saving to DB...");
    }

    public static Employee getById(int id) {
        return new Employee();
    }
}
