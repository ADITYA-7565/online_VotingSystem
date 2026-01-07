package util;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {

    private static final String URL =
            "jdbc:mysql://localhost:3306/online_voting_db";
    private static final String USER = "root";
    private static final String PASSWORD = "Rohan@7565"; // change if needed

    public static Connection getConnection() {
    	System.out.println("Database connected successfully");

        Connection con = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return con;
    }
}
