package EmployeeManagementSystem;

public class Employee {

    private int empId;
    private String name;
    private String department;
    private double salary;

    // Default Constructor
    public Employee() {

    }

    // Parameterized Constructor
    public Employee(int empId, String name, String department, double salary) {
        this.empId = empId;
        this.name = name;
        this.department = department;
        this.salary = salary;
    }

    // Getters
    public int getEmpId() {
        return empId;
    }

    public String getName() {
        return name;
    }

    public String getDepartment() {
        return department;
    }

    public double getSalary() {
        return salary;
    }

    // Setters
    public void setEmpId(int empId) {
        this.empId = empId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    // Display Employee Details
    @Override
    public String toString() {
        return "Employee ID : " + empId +
               "\nName : " + name +
               "\nDepartment : " + department +
               "\nSalary : " + salary;
    }
}