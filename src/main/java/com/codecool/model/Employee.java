package com.codecool.model;

import com.codecool.dao.FakeCSV;
import com.codecool.dao.FakeDB;

import java.math.BigDecimal;
import java.math.MathContext;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Employee {

    private static BigDecimal ONE_HUNDRED = new BigDecimal(100);
    private static BigDecimal RETIREMENT_CONTRIBUTION_RATE = new BigDecimal("9.76").divide(ONE_HUNDRED);
    private static BigDecimal PENSION_CONTRIBUTION_RATE = new BigDecimal("1.5").divide(ONE_HUNDRED);
    private static BigDecimal SICK_LEAVE_CONTRIBUTION_RATE = new BigDecimal("2.45").divide(ONE_HUNDRED);
    private static BigDecimal HEALTH_INSURANCE_CONTRIBUTION_RATE = new BigDecimal("9").divide(ONE_HUNDRED);
    private static BigDecimal HEALTH_INSURANCE_TAX_DISCOUNT = new BigDecimal("7.75").divide(ONE_HUNDRED);
    private static BigDecimal TAX_RATE = new BigDecimal("18").divide(ONE_HUNDRED);
    private static BigDecimal TAX_DISCOUNT = new BigDecimal("46.33");
    private static BigDecimal INCOME_COSTS = new BigDecimal("111.25");


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

    private BigDecimal getRetirementContribution(){
        return this.salary.multiply(RETIREMENT_CONTRIBUTION_RATE);
    }

    private BigDecimal getPensionContribution(){
        return this.salary.multiply(PENSION_CONTRIBUTION_RATE);
    }

    private BigDecimal getSickLeaveContribution(){
        return this.salary.multiply(SICK_LEAVE_CONTRIBUTION_RATE);
    }

    private BigDecimal getContributionsTotal(){
        return getRetirementContribution()
                .add(getPensionContribution())
                .add(getSickLeaveContribution());
    }

    private BigDecimal getHealthInsuranceBase(){
        return this.salary.subtract(getContributionsTotal());
    }

    private BigDecimal getHealthInsurance(){
        return getHealthInsuranceBase().multiply(HEALTH_INSURANCE_CONTRIBUTION_RATE);
    }

    private BigDecimal getHealthInsuranceTaxDiscount(){
        return getHealthInsuranceBase().multiply(HEALTH_INSURANCE_TAX_DISCOUNT);
    }

    private BigDecimal getTaxBase(){
        return this.salary
                .subtract(INCOME_COSTS)
                .subtract(getContributionsTotal())
                .setScale(0, BigDecimal.ROUND_UP);
    }

    private BigDecimal getTax(){
        return getTaxBase()
                .multiply(TAX_RATE)
                .subtract(TAX_DISCOUNT)
                .subtract(getHealthInsuranceTaxDiscount())
                .setScale(0, BigDecimal.ROUND_UP);
    }

    public BigDecimal getNetSalary(){
        return this.salary
                .subtract(getContributionsTotal())
                .subtract(getHealthInsurance())
                .subtract(getTax())
                .setScale(2, BigDecimal.ROUND_DOWN);
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
