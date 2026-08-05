package EmployeeManagementSystem;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class EmployeeManager {

    HashMap<Integer, Employee> employees = new HashMap<>();

    // Add Employee
    public void addEmployee(Scanner sc) {

        try {

            System.out.print("Enter Employee ID: ");
            int empId = sc.nextInt();
            sc.nextLine();

            if (employees.containsKey(empId)) {
                throw new Exception("Employee ID Already Exists.");
            }

            System.out.print("Enter Employee Name: ");
            String name = sc.nextLine();

            System.out.print("Enter Department: ");
            String department = sc.nextLine();

            System.out.print("Enter Salary: ");
            double salary = sc.nextDouble();

            Employee emp = new Employee(empId, name, department, salary);

            employees.put(empId, emp);

            System.out.println("Employee Added Successfully.");

        } catch (Exception e) {

            System.out.println("Error : " + e.getMessage());
            sc.nextLine();

        }
    }

    // Update Employee
    public void updateEmployee(Scanner sc) {

        try {

            System.out.print("Enter Employee ID: ");
            int empId = sc.nextInt();
            sc.nextLine();

            if (!employees.containsKey(empId)) {
                throw new Exception("Employee Not Found.");
            }

            Employee emp = employees.get(empId);

            System.out.print("Enter New Name: ");
            emp.setName(sc.nextLine());

            System.out.print("Enter New Department: ");
            emp.setDepartment(sc.nextLine());

            System.out.print("Enter New Salary: ");
            emp.setSalary(sc.nextDouble());

            System.out.println("Employee Updated Successfully.");

        } catch (Exception e) {

            System.out.println("Error : " + e.getMessage());
            sc.nextLine();

        }
    }

    // Delete Employee
    public void deleteEmployee(Scanner sc) {

        try {

            System.out.print("Enter Employee ID: ");
            int empId = sc.nextInt();

            if (employees.remove(empId) == null) {
                throw new Exception("Employee Not Found.");
            }

            System.out.println("Employee Deleted Successfully.");

        } catch (Exception e) {

            System.out.println("Error : " + e.getMessage());
            sc.nextLine();

        }
    }

    // Search Employee
    public void searchEmployee(Scanner sc) {

        try {

            System.out.print("Enter Employee ID: ");
            int empId = sc.nextInt();

            if (!employees.containsKey(empId)) {
                throw new Exception("Employee Not Found.");
            }

            System.out.println(employees.get(empId));

        } catch (Exception e) {

            System.out.println("Error : " + e.getMessage());
            sc.nextLine();

        }
    }

    // Display Employees
    public void displayEmployees() {

        try {

            if (employees.isEmpty()) {
                throw new Exception("No Employees Available.");
            }

            System.out.println("\n===== Employee List =====");

            for (Map.Entry<Integer, Employee> entry : employees.entrySet()) {

                System.out.println(entry.getValue());
                System.out.println("--------------------------");

            }

        } catch (Exception e) {

            System.out.println("Error : " + e.getMessage());

        }
    }

    // Employee Menu
    public void employeeMenu(Scanner sc) {

        int choice;

        do {

            System.out.println("\n===== EMPLOYEE MENU =====");
            System.out.println("1. Add Employee");
            System.out.println("2. Update Employee");
            System.out.println("3. Delete Employee");
            System.out.println("4. Display Employees");
            System.out.println("5. Search Employee");
            System.out.println("6. Back");
            System.out.print("Enter Choice: ");

            choice = sc.nextInt();

            switch (choice) {

                case 1:
                    addEmployee(sc);
                    break;

                case 2:
                    updateEmployee(sc);
                    break;

                case 3:
                    deleteEmployee(sc);
                    break;

                case 4:
                    displayEmployees();
                    break;

                case 5:
                    searchEmployee(sc);
                    break;

                case 6:
                    System.out.println("Returning...");
                    break;

                default:
                    System.out.println("Invalid Choice.");

            }

        } while (choice != 6);
    }
}