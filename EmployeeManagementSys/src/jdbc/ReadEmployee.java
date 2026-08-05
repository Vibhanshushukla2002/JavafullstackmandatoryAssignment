package jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class ReadEmployee {

	Scanner sc = new Scanner(System.in);

	// Search by Employee ID
	public void displayById() {

		try {

			Connection con = DBConnection.getConnection();

			System.out.print("Enter Employee ID : ");
			int id = sc.nextInt();

			PreparedStatement ps = con.prepareStatement("SELECT * FROM employee WHERE eno=?");
			ps.setInt(1, id);

			ResultSet rs = ps.executeQuery();

			if (rs.next()) {

				System.out.println("---------------------------------------------");
				System.out.println("Employee No : " + rs.getInt(1));
				System.out.println("Employee Name : " + rs.getString(2));
				System.out.println("Salary : " + rs.getDouble(3));
				System.out.println("Department : " + rs.getString(4));
				System.out.println("Gender : " + rs.getString(5));
				System.out.println("DOB : " + rs.getDate(6));

			} else {
				System.out.println("Employee Not Found...");
			}

			rs.close();
			ps.close();
			con.close();

		} catch (Exception e) {
			System.out.println(e);
		}
	}

	// Display All Employees
	public void displayAllEmployees() {

		try {

			Connection con = DBConnection.getConnection();

			PreparedStatement ps = con.prepareStatement("SELECT * FROM employee");

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {

				System.out.println("---------------------------------------------");
				System.out.println("Employee No : " + rs.getInt(1));
				System.out.println("Employee Name : " + rs.getString(2));
				System.out.println("Salary : " + rs.getDouble(3));
				System.out.println("Department : " + rs.getString(4));
				System.out.println("Gender : " + rs.getString(5));
				System.out.println("DOB : " + rs.getDate(6));

			}

			rs.close();
			ps.close();
			con.close();

		} catch (Exception e) {
			System.out.println(e);
		}
	}

	// Department Wise
	public void displayDepartmentWise() {

		try {

			Connection con = DBConnection.getConnection();

			sc.nextLine();
			System.out.print("Enter Department : ");
			String dept = sc.nextLine();

			PreparedStatement ps = con.prepareStatement("SELECT * FROM employee WHERE dept=?");
			ps.setString(1, dept);

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {

				System.out.println(rs.getInt(1) + " "
						+ rs.getString(2) + " "
						+ rs.getDouble(3) + " "
						+ rs.getString(4) + " "
						+ rs.getString(5) + " "
						+ rs.getDate(6));
			}

			rs.close();
			ps.close();
			con.close();

		} catch (Exception e) {
			System.out.println(e);
		}
	}

	// Gender Wise
	public void displayGenderWise() {

		try {

			Connection con = DBConnection.getConnection();

			sc.nextLine();
			System.out.print("Enter Gender (Male/Female): ");
			String gender = sc.nextLine();

			PreparedStatement ps = con.prepareStatement("SELECT * FROM employee WHERE gender=?");
			ps.setString(1, gender);

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {

				System.out.println(rs.getInt(1) + " "
						+ rs.getString(2) + " "
						+ rs.getDouble(3) + " "
						+ rs.getString(4) + " "
						+ rs.getString(5) + " "
						+ rs.getDate(6));
			}

			rs.close();
			ps.close();
			con.close();

		} catch (Exception e) {
			System.out.println(e);
		}
	}

	// Employees Born After Year
	public void displayByYear() {

		try {

			Connection con = DBConnection.getConnection();

			System.out.print("Enter Year : ");
			int year = sc.nextInt();

			PreparedStatement ps = con.prepareStatement(
					"SELECT * FROM employee WHERE YEAR(dob) > ?");

			ps.setInt(1, year);

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {

				System.out.println(rs.getInt(1) + " "
						+ rs.getString(2) + " "
						+ rs.getDouble(3) + " "
						+ rs.getString(4) + " "
						+ rs.getString(5) + " "
						+ rs.getDate(6));
			}

			rs.close();
			ps.close();
			con.close();

		} catch (Exception e) {
			System.out.println(e);
		}
	}

	// Maximum Salary
	public void maximumSalary() {

		try {

			Connection con = DBConnection.getConnection();

			PreparedStatement ps = con.prepareStatement(
					"SELECT * FROM employee WHERE salary=(SELECT MAX(salary) FROM employee)");

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {

				System.out.println(rs.getInt(1) + " "
						+ rs.getString(2) + " "
						+ rs.getDouble(3) + " "
						+ rs.getString(4) + " "
						+ rs.getString(5) + " "
						+ rs.getDate(6));
			}

			rs.close();
			ps.close();
			con.close();

		} catch (Exception e) {
			System.out.println(e);
		}
	}

	// Maximum Age
	public void maximumAge() {

		try {

			Connection con = DBConnection.getConnection();

			PreparedStatement ps = con.prepareStatement(
					"SELECT * FROM employee ORDER BY dob ASC LIMIT 1");

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {

				System.out.println(rs.getInt(1) + " "
						+ rs.getString(2) + " "
						+ rs.getDouble(3) + " "
						+ rs.getString(4) + " "
						+ rs.getString(5) + " "
						+ rs.getDate(6));
			}

			rs.close();
			ps.close();
			con.close();

		} catch (Exception e) {
			System.out.println(e);
		}
	}
}