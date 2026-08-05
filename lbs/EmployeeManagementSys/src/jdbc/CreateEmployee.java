package jdbc;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.util.Scanner;

public class CreateEmployee {

	Scanner sc = new Scanner(System.in);

	// Add Single Employee
	public void addEmployee() {

		try {

			Connection con = DBConnection.getConnection();

			String sql = "insert into employee values(?,?,?,?,?,?)";

			PreparedStatement ps = con.prepareStatement(sql);

			System.out.print("Enter Employee No : ");
			ps.setInt(1, sc.nextInt());

			sc.nextLine();

			System.out.print("Enter Employee Name : ");
			ps.setString(2, sc.nextLine());

			System.out.print("Enter Salary : ");
			ps.setDouble(3, sc.nextDouble());

			sc.nextLine();

			System.out.print("Enter Department : ");
			ps.setString(4, sc.nextLine());

			System.out.print("Enter Gender : ");
			ps.setString(5, sc.nextLine());

			System.out.print("Enter DOB (yyyy-mm-dd) : ");
			String dob = sc.nextLine();

			ps.setDate(6, Date.valueOf(dob));

			int row = ps.executeUpdate();

			if (row > 0) {
				System.out.println("Employee Added Successfully...");
			} else {
				System.out.println("Employee Not Added...");
			}

			ps.close();
			con.close();

		} catch (Exception e) {
			System.out.println(e);
		}
	}

	// Add Multiple Employees using Batch
	public void addMultipleEmployees() {

		try {

			Connection con = DBConnection.getConnection();

			String sql = "insert into employee values(?,?,?,?,?,?)";

			PreparedStatement ps = con.prepareStatement(sql);

			System.out.print("Enter Number of Employees : ");
			int n = sc.nextInt();

			for (int i = 1; i <= n; i++) {

				System.out.println("\nEnter Details of Employee " + i);

				System.out.print("Employee No : ");
				ps.setInt(1, sc.nextInt());

				sc.nextLine();

				System.out.print("Employee Name : ");
				ps.setString(2, sc.nextLine());

				System.out.print("Salary : ");
				ps.setDouble(3, sc.nextDouble());

				sc.nextLine();

				System.out.print("Department : ");
				ps.setString(4, sc.nextLine());

				System.out.print("Gender : ");
				ps.setString(5, sc.nextLine());

				System.out.print("DOB (yyyy-mm-dd) : ");
				String dob = sc.nextLine();

				ps.setDate(6, Date.valueOf(dob));

				ps.addBatch();
			}

			ps.executeBatch();

			System.out.println("All Employees Added Successfully...");

			ps.close();
			con.close();

		} catch (Exception e) {
			System.out.println(e);
		}
	}

}