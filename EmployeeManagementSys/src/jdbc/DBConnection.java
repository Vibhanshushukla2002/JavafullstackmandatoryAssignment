package jdbc;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {
			
	 static String url = "jdbc:mysql://localhost:3306/employee_management";
	    static String user = "root";
	    static String password = "root123"; // Change if your password is different

	    public static Connection getConnection() {

	        Connection con = null;

	        try {

	            Class.forName("com.mysql.cj.jdbc.Driver");

	            con = DriverManager.getConnection(url, user, password);

	        } catch (Exception e) {
	            e.printStackTrace();
	        }

	        return con;
	    }
    
}