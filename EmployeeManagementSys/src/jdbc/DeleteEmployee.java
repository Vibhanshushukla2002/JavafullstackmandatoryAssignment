package jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Scanner;

public class DeleteEmployee {

    Scanner sc = new Scanner(System.in);

    public void deleteEmployee() {

        try {

            Connection con = DBConnection.getConnection();

            System.out.print("Enter Employee ID to Delete : ");
            int eno = sc.nextInt();

            String sql = "DELETE FROM employee WHERE eno=?";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, eno);

            int row = ps.executeUpdate();

            if (row > 0) {
                System.out.println("Employee Deleted Successfully...");
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