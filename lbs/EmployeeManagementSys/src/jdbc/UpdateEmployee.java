package jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Scanner;

public class UpdateEmployee {

    Scanner sc = new Scanner(System.in);

    public void updateEmployee() {

        try {

            Connection con = DBConnection.getConnection();

            System.out.print("Enter Employee ID : ");
            int eno = sc.nextInt();

            sc.nextLine();

            System.out.print("Enter New Employee Name : ");
            String ename = sc.nextLine();

            System.out.print("Enter New Salary : ");
            double salary = sc.nextDouble();

            sc.nextLine();

            System.out.print("Enter New Department : ");
            String dept = sc.nextLine();

            String sql = "UPDATE employee SET ename=?, salary=?, dept=? WHERE eno=?";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, ename);
            ps.setDouble(2, salary);
            ps.setString(3, dept);
            ps.setInt(4, eno);

            int row = ps.executeUpdate();

            if (row > 0) {
                System.out.println("Employee Updated Successfully...");
            } else {
                System.out.println("Employee Not Found...");
            }

            ps.close();
            con.close();

        } catch (Exception e) {
            System.out.println(e);
        }

    }

}