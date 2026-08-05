package jdbc;

import java.util.Scanner;

public class Main {

	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);

		CreateEmployee create = new CreateEmployee();
		ReadEmployee read = new ReadEmployee();
		UpdateEmployee update = new UpdateEmployee();
		DeleteEmployee delete = new DeleteEmployee();

		while (true) {

			System.out.println("\n==============================================");
			System.out.println("      EMPLOYEE MANAGEMENT SYSTEM");
			System.out.println("==============================================");
			System.out.println("1. Add Employee");
			System.out.println("2. Add Multiple Employees");
			System.out.println("3. Update Employee");
			System.out.println("4. Delete Employee");
			System.out.println("5. Search Employee By ID");
			System.out.println("6. Display All Employees");
			System.out.println("7. Display Department Wise");
			System.out.println("8. Display Gender Wise");
			System.out.println("9. Display Employees Born After Year");
			System.out.println("10. Maximum Salary");
			System.out.println("11. Maximum Age");
			System.out.println("12. Exit");
			System.out.print("\nEnter Your Choice : ");

			int choice = sc.nextInt();

			switch (choice) {

			case 1:
				create.addEmployee();
				break;

			case 2:
				create.addMultipleEmployees();
				break;

			case 3:
				update.updateEmployee();
				break;

			case 4:
				delete.deleteEmployee();
				break;

			case 5:
				read.displayById();
				break;

			case 6:
				read.displayAllEmployees();
				break;

			case 7:
				read.displayDepartmentWise();
				break;

			case 8:
				read.displayGenderWise();
				break;

			case 9:
				read.displayByYear();
				break;

			case 10:
				read.maximumSalary();
				break;

			case 11:
				read.maximumAge();
				break;

			case 12:
				System.out.println("\nThank You...");
				sc.close();
				System.exit(0);
				break;

			default:
				System.out.println("Invalid Choice...");
			}
		}
	}
}