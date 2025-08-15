package Services;

import db.DBConnection;
import model.Transcation;
import model.Expense;
import model.Income;

import java.sql.*;
import java.util.*;

public class TransactionServices {

    private static Connection conn;

    public TransactionServices(){
        conn = DBConnection.getConnection();
    }

    public double getBalance(int userId) {
        double balance = 0;
        try {
            // Query to get the current balance of the user
            String query = "SELECT balance FROM users WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            // If the user exists, get the current balance
            if (rs.next()) {
                balance = rs.getDouble("balance");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error retrieving balance: " + e.getMessage());
        }
        return balance;
    }


    public void updateUserBalance(int userId, double newBalance) {
        try {
            String sql = "UPDATE users SET balance = ? WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setDouble(1, newBalance);
            stmt.setInt(2, userId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error updating user balance: " + e.getMessage());
        }
    }


    public int addTransaction(Transcation t) {
        int generatedId = -1;
        try {
            // Get the current balance of the user from the database
            double currentBalance = getBalance(t.getUserId());

            // Calculate the new balance
            double newBalance;
            if (t.getType().equals("Income")) {
                newBalance = currentBalance + t.getAmount();
            } else {
                newBalance = currentBalance - t.getAmount();
            }

            // Update the user's balance in the database
            updateUserBalance(t.getUserId(), newBalance);

            // Insert the new transaction into the database
            String insertSql = "INSERT INTO transactions (userid, amount, category, date, type) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement insertStmt = conn.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS);
            insertStmt.setInt(1, t.getUserId());
            insertStmt.setDouble(2, t.getAmount());
            insertStmt.setString(3, t.getcategory());  // Ensure this is the correct method for getting category
            insertStmt.setString(4, t.getDate());
            insertStmt.setString(5, t.getType());
            insertStmt.executeUpdate();

            // Retrieve the generated ID for the transaction
            ResultSet rs = insertStmt.getGeneratedKeys();
            if (rs.next()) {
                generatedId = rs.getInt(1);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("addTransactionAndReturnId SQLException: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("addTransactionAndReturnId Exception: " + e.getMessage());
        }
        return generatedId;
    }



    public List<Transcation> getUserTransaction(int user_id){
            List<Transcation> list = new ArrayList<>();
        try{

            String sql = "SELECT *  FROM transactions WHERE userid = ?";
            PreparedStatement ptmt = conn.prepareStatement(sql);

            ptmt.setInt(1 , user_id);
            ptmt.executeQuery();
            ResultSet rs = ptmt.getResultSet();

            while(rs.next()){
                Transcation t;
                String type = rs.getString("type");

                if(type.equals("Income")){
                    t = new Income(rs.getInt("userid") , rs.getDouble("amount") , rs.getString("category") , rs.getString("date"));
                }
                else{
                    t = new Expense(rs.getInt("userid") , rs.getDouble("amount") , rs.getString("category") , rs.getString("date"));
                }
                list.add(t);
            }
        } catch (SQLException e) {
            System.out.println("getUserTransaction SQLException:: " + e.getMessage());
        }catch(Exception e){
            System.out.println("getUserTransaction: " + e.getMessage());
        }
        return list;
    }

    public double getprevTransaction(int Transcid){

        double amount = 0;
        try{

            String sql = "SELECT amount  FROM transactions WHERE id = ?";
            PreparedStatement ptmt = conn.prepareStatement(sql);

            ptmt.setInt(1 , Transcid);

            ptmt.executeQuery();

            ResultSet rs = ptmt.getResultSet();
            if(rs.next()){
                amount = rs.getDouble(1);
            }

        } catch (SQLException e) {
            System.out.println("getTotal SQLException:: " + e.getMessage());
        }catch(Exception e){
            System.out.println("getTotal: " + e.getMessage());
        }
        return amount;
    }

    public int getUserId(int Transcid){

        int user_id = 0;
        try{

            String sql = "SELECT userid  FROM transactions WHERE id = ?";
            PreparedStatement ptmt = conn.prepareStatement(sql);

            ptmt.setInt(1 , Transcid);

            ptmt.executeQuery();

            ResultSet rs = ptmt.getResultSet();
            if(rs.next()){
                user_id = rs.getInt(1);
            }

        } catch (SQLException e) {
            System.out.println("getTotal SQLException:: " + e.getMessage());
        }catch(Exception e){
            System.out.println("getTotal: " + e.getMessage());
        }
        return user_id;
    }

    public void updateTransaction(int id , double amount , String category , String type , String date , double prev_balance) {
        int user_id = getUserId(id); // sending transaction id
        try {

            double balance = prev_balance;
            double prev_transaction = getprevTransaction(id);


            if(type.equals("Income")){
                balance = getBalance(user_id) - prev_transaction + amount;
            }
            else{
                if(type.equals("Expense")){
                    balance = getBalance(getUserId(id)) + prev_transaction - amount;
                }
            }

            updateUserBalance(user_id , balance);

            String sql = "UPDATE transactions SET amount = ?, category = ?, type = ?, date = ? WHERE id = ?";
            PreparedStatement ptmt = conn.prepareStatement(sql);

            ptmt.setDouble(1, amount);
            ptmt.setString(2, category);
            ptmt.setString(3, type);
            ptmt.setString(4, date);
            ptmt.setInt(5, id);

            int rowsUpdated = ptmt.executeUpdate(); //  Use executeUpdate it gives the number of rowws affecteds
            if (rowsUpdated > 0) {
                System.out.println("Transaction updated successfully.");
            } else {
                System.out.println("Transaction ID not found.");
            }

        } catch (SQLException e) {
            System.out.println("SQLException in updateTransaction: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("updateTransaction Exception: " + e.getMessage());
        }
    }


    public boolean deleteTransaction(int id) {

        try {

            String sql = "DELETE FROM transactions WHERE id = ?";
            PreparedStatement ptmt = conn.prepareStatement(sql);


            ptmt.setInt(1, id);

            int rowsUpdated = ptmt.executeUpdate(); //  Use executeUpdate it gives the number of rowws affecteds
            if (rowsUpdated > 0) {
                System.out.println("Transaction deleted successfully.");

            } else {
                System.out.println("Transaction ID not found.");
                return false;
            }

        } catch (SQLException e) {
            System.out.println("SQLException in deleteTransaction: " + e.getMessage());
        } catch (Exception e) {
            System.out.println(" deleteTransaction Exception: " + e.getMessage());
        }
        return true;
    }

    public List<Transcation> searchTransactionbyDateRange(int userid , String StartDate , String EndDate) {

        List<Transcation> list = new ArrayList<>();

        try {

            java.sql.Date sqlStartDate = java.sql.Date.valueOf(StartDate);
            java.sql.Date sqlEndDate = java.sql.Date.valueOf(EndDate);

            String sql = "SELECT * FROM transactions WHERE userid = ? AND STR_TO_DATE(date, '%Y-%m-%d' ) BETWEEN ? AND ?";    // When searching in database converting the String date in Date date
            PreparedStatement ptmt = conn.prepareStatement(sql);

            ptmt.setInt(1, userid);
            ptmt.setDate(2, sqlStartDate);
            ptmt.setDate(3, sqlEndDate);


            ResultSet rs =  ptmt.executeQuery();

            while(rs.next()){
                Transcation t;
                String type = rs.getString("type");
                if(type.equals("Income")){
                    t = new Income(rs.getInt("userid") , rs.getDouble("amount") , rs.getString("category") , rs.getString("date"));
                }
                else{
                    t = new Expense(rs.getInt("userid") , rs.getDouble("amount") , rs.getString("category") , rs.getString("date"));
                }
                list.add(t);
            }

        } catch (SQLException e) {
            System.out.println("SQLException in searchTransactionbyDateRange: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("searchTransactionbyDateRange Exception: " + e.getMessage());
        }
        return list;
    }

    public  List<Transcation> getMonthlyTransaction(int userid , String year , String month){

        List<Transcation> list = new ArrayList<>();


        try {

            String sql = "SELECT * FROM transactions WHERE userid = ? AND SUBSTRING(date , 1,4) = ? AND SUBSTRING(date , 6 , 2) = ?";    // Function format SUBSTRING(date , starting_index , size from thier you want) ex. 2023-04-09..so 1,4 is 2023..in mysql indexing is from 1 and 6 , 2 is "04" 0 as 6th index and 4 has 7 so 6 , 2 is start form 6th and step move is 2
                                                                                                                                                            // We are doing the substring because the date in db is in String format
            PreparedStatement ptmt = conn.prepareStatement(sql);

            ptmt.setInt(1, userid);
            ptmt.setString(2, year);
            ptmt.setString(3, month);

            ptmt.executeQuery();
            ResultSet rs = ptmt.getResultSet();

            while(rs.next()){
                Transcation t;
                String type = rs.getString("type");
                if(type.equals("Income")){
                    t = new Income(rs.getInt("userid") , rs.getDouble("amount") , rs.getString("category") , rs.getString("date"));
                }
                else{
                    t = new Expense(rs.getInt("userid") , rs.getDouble("amount") , rs.getString("category") , rs.getString("date"));
                }
                list.add(t);
            }

        } catch (SQLException e) {
            System.out.println("SQLException in getMonthlyTransaction: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("getMonthlyTransaction Exception: " + e.getMessage());
        }
        return list;
    }

}
