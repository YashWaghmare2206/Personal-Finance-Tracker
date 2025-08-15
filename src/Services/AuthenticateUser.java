package Services;

import db.DBConnection;
import model.Transcation;

import java.sql.*;

public class AuthenticateUser {

    public Connection conn;

    public AuthenticateUser(){
        try {
            conn = DBConnection.getConnection();
        } catch(Exception e) {
            e.printStackTrace(); // Or use a logger
        }
    }


    public boolean validateuser(String username , String password){

        try{

            String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
            PreparedStatement ptmt = conn.prepareStatement(sql);
            ptmt.setString(1,username);
            ptmt.setString(2,password);

            ResultSet rs = ptmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            System.out.println("Authentication SQLException:: " + e.getMessage());
        }catch(Exception e){
            System.out.println("Authentication: " + e.getMessage());
        }
        return false;

    }

    public void registerUser(String username, String password, double startingBalance) {
        try {
            String sql = "INSERT INTO users (username, password, balance) VALUES (?, ?, ?)";
            PreparedStatement ptmt = conn.prepareStatement(sql);
            ptmt.setString(1, username);
            ptmt.setString(2, password);
            ptmt.setDouble(3, startingBalance);
            ptmt.executeUpdate();
            System.out.println("User registered with starting balance: " + startingBalance);
        } catch (SQLException e) {
            System.out.println("registerUser SQLException: " + e.getMessage());
        }
    }

    public int getUserId(String username) {
        int userId = -1; // Default to -1 if no user is found

        try (Connection conn = DBConnection.getConnection()) {
            String query = "SELECT id FROM users WHERE username = ?";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setString(1, username);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        userId = rs.getInt("id");  // Extract the userId from the result
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return userId;
    }
}




