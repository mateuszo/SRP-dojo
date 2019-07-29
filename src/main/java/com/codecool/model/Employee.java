package com.codecool.model;

import com.codecool.dao.FakeCSV;
import com.codecool.dao.FakeDB;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Employee {

    private String firstName;
    private String lastName;
    private int age;
    private LocalDate employmentDate;
    private BigDecimal salary;
    private String title;
    private int usedHolidays;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }

    public void setEmploymentDate(String employmentDate) {
        this.employmentDate = LocalDate.parse(employmentDate);
    }

    public void display(){
        System.out.println("Hello my name is " + lastName +
                ", " + firstName + " " + lastName);
    }

    @Override
    public String toString() {
        return "Employee{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", age=" + age +
                ", employmentDate=" + employmentDate +
                ", salary=" + salary +
                ", title='" + title + '\'' +
                ", usedHolidays=" + usedHolidays +
                '}';
    }


    public String toJSON(){
        return "{" +
                "firstName:'" + firstName + '\'' +
                ", lastName:'" + lastName + '\'' +
                ", age:" + age +
                ", employmentDate:" + employmentDate +
                ", salary:" + salary +
                ", title:'" + title + '\'' +
                ", usedHolidays:" + usedHolidays +
                '}';
    }

    public BigDecimal getNetSalary(){
        return salary.multiply(BigDecimal.valueOf(1.23));
    }

    public void saveToCSV(){
        FakeCSV.save(this);
    }

    public void saveToSQL(){
        FakeDB.save(this);
    }

    public static Employee getById(int id){
        return FakeDB.getById(id);
    }

    public LocalDate getEmploymentDate() {
        return employmentDate;
    }

    private boolean isWorkingDay(LocalDate date){
        return date.getDayOfWeek().getValue() < 6;
    }

    public void setHolidays(String start, String end){
        LocalDate startDate = LocalDate.parse(start);
        LocalDate endDate = LocalDate.parse(end);
        while (startDate.isBefore(endDate)){
            if (isWorkingDay(startDate)){
                this.usedHolidays++;
            }
            startDate = startDate.plusDays(1);
        }
    }

    public int getAvailableHoliday(){
        long months = ChronoUnit.MONTHS.between(employmentDate, LocalDate.now());
        return (int) (months * 2 - usedHolidays);
    }
}
