package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private static Connection conn = null;

    public static Connection getConnection() {
        try {
            if (conn == null || conn.isClosed()) {
                String url = System.getenv("DB_URL");         // Add you database url
                String username = System.getenv("DB_USERNAME");   //Database username
                String password = System.getenv("DB_PASSWORD");   // Database password

                Class.forName("com.mysql.cj.jdbc.Driver");
                conn = DriverManager.getConnection(url, username, password);
            }
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return conn;
    }
}
