package EmployeeManagementSystem;

import java.util.InputMismatchException;
import java.util.Scanner;

public class EmployeeManagement {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        EmployeeManager manager = new EmployeeManager();

        int choice = 0;

        do {

            System.out.println("\n===== EMPLOYEE MANAGEMENT SYSTEM =====");
            System.out.println("1. Employee Operations");
            System.out.println("2. Exit");
            System.out.print("Enter Choice: ");

            try {

                choice = sc.nextInt();

                switch (choice) {

                    case 1:
                        manager.employeeMenu(sc);
                        break;

                    case 2:
                        System.out.println("Thank You!");
                        break;

                    default:
                        System.out.println("Invalid Choice.");

                }

            } catch (InputMismatchException e) {

                System.out.println("Please Enter Numbers Only.");
                sc.nextLine(); // Clear invalid input
            }

        } while (choice != 2);

        sc.close();
    }
}