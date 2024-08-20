package com.fred.eshop.test;

import java.sql.*;
 
public class DBTest {
    public static void main(String[] args) {
        Connection conn;
        try {
            // Load db driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            String username = "root";
            String password = "mypass";
            String url = "jdbc:mysql://localhost:3307/test";          
 
            // Conect to db
            conn = DriverManager.getConnection(url, username, password);
 
            // CRUD
            Statement stat = conn.createStatement();
 
            // Create DB
            String createQuery = "INSERT INTO product (name, price) VALUES ('Keyboard Cover', 200.00)";
            stat.executeUpdate(createQuery);

            // Update DB
            String updateQuery = "UPDATE product SET price = 9.99 WHERE pid = 1";
            stat.executeUpdate(updateQuery);
 
            // Delete DB
            String deleteQuery = "DELETE FROM product WHERE pid = 2";
            stat.executeUpdate(deleteQuery);
 
            // Read DB
            String readQuery = "SELECT * from product";
            ResultSet rs = stat.executeQuery(readQuery);


            while (rs.next())
                System.out.printf("%s: %s, %.2f\n", rs.getInt(1), rs.getString(2), rs.getDouble(3));

            conn.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        } 
 
    }
}
