package com.codecool;

import com.codecool.model.Employee;

import java.math.BigDecimal;

public class Main {
    public static void main(String[] args) {
        Employee emp = Employee.getById(1);
        emp.setFirstName("James");
        emp.setLastName("Bond");
        emp.setSalary(BigDecimal.valueOf(4123));
        emp.setEmploymentDate("2017-07-07");
        emp.setHolidays("2019-07-14", "2019-07-28");

        System.out.println(emp.toJSON());
        System.out.println(emp);
        System.out.println("==============");

        emp.display();
        System.out.println("I earn: $" + emp.getSalary() +
                ". But only $" + emp.getNetSalary() + " stays in my pocket :(");
        System.out.println("However, I have " + emp.getAvailableHoliday() + " days off left.");
        System.out.println("==============");

        emp.saveToSQL();
        emp.saveToCSV();
        System.out.println("==============");



    }
}
