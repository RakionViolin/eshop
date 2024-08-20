package com.fred.eshop.dao;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.ResourceUtils;

import com.fred.eshop.model.Order;
import com.fred.eshop.model.Product;

@Repository
public class OrderDAOMySql implements OrderDAO {
    @Autowired
    private DataSource dataSource;

    @Override
    public void create(Order order) throws SQLException {
        String createQuery = "INSERT INTO productOrder (oid, description, total, date_time) VALUES (?, ?, ?, ?)";
        String query2 = "INSERT INTO orderDetails (oid, pid,quantity) VALUES(?, ?, ?)";

        Connection conn = null;
        try {
            conn = getConnection();
            conn.setAutoCommit(false);

            PreparedStatement stat = conn.prepareStatement(createQuery);
            stat.setString(1, order.getOrderID());            
            stat.setString(2, order.getDescription());
            stat.setFloat(3, order.getPrice());
            stat.setTimestamp(4, new Timestamp(order.getDate().getTime()));
            stat.executeUpdate();

            stat = conn.prepareStatement(query2);
            for (Product product : order.getProducts()) {
                stat.setString(1, order.getOrderID());
                stat.setInt(2, product.getID());
                stat.setInt(3, product.getQuantity());
                stat.executeUpdate();
            }
            
            conn.commit();
        } catch (IOException ex) {           
            throw new SQLException("Can not load DB driver configuration");
        }
    }

    @Override
    public Order read(String id) throws SQLException {
        Order order = null;
        List<Product> products = new ArrayList<>();
        String orderQuery = "SELECT * FROM productOrder WHERE oid=" + id;
        String productQuery = "SELECT product.pid, product.name, product.price, product.quantity, product.image, orderDetails.quantity FROM product INNER JOIN orderDetails on product.pid = orderDetails.pid WHERE orderDetails.oid = " + id;
        
        try (Connection conn = getConnection(); Statement stat = conn.createStatement()) {
            ResultSet rs = stat.executeQuery(productQuery);
            while (rs.next()) {
                Product product = new Product(rs.getString(2), rs.getFloat(3), rs.getInt(6),rs.getString(5));
                product.setID(rs.getInt(1));
                products.add(product);
            }

            rs = stat.executeQuery(orderQuery);
            if (rs.next())
                order = new Order(id, rs.getString(2), rs.getFloat(3), rs.getTimestamp(4), products);
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new SQLException("Can not get order from DB");
        }

        return order;
    }

    @Override
    public List<Order> readAll() throws SQLException {
        List<Order> orders = new ArrayList<Order>();
        List<Product> products = new ArrayList<>(); // lazy initialization of products

        try (Connection conn = getConnection(); Statement stat = conn.createStatement()) {
            try (ResultSet rs = stat.executeQuery("SELECT * FROM productOrder")) {
                while (rs.next()) {
                    Order order = new Order(rs.getString(1), rs.getString(2), rs.getFloat(3), rs.getTimestamp(4), products);
                    orders.add(order);
                }
            }
        } catch (IOException ex) {
            throw new SQLException("Can not load DB driver configuration");
        }
        
        return orders;
    }

    @Override
    public int update(Order order) throws SQLException {
        String updateQuery = "UPDATE productOrder SET description=?, total=?, date_time=? WHERE oid=?";
        String deleteQuery = "DELETE FROM orderDetails WHERE oid =?";
        String insertQuery = "INSERT INTO orderDetails (oid, pid, quantity) VALUES (?,?,?)";
        // update product list
        int row = 0;
        try (Connection conn = getConnection()) {
            conn.setAutoCommit(false);

            // update order 
            PreparedStatement stat = conn.prepareStatement(updateQuery);
            stat.setString(1, order.getDescription());
            stat.setFloat(2, order.getPrice());            
            stat.setTimestamp(3, new Timestamp(order.getDate().getTime()));
            stat.setString(4, order.getOrderID());
            row = stat.executeUpdate();
            
            // delete products belong to the order
            stat = conn.prepareStatement(deleteQuery);
            stat.setString(1, order.getOrderID());
            row = stat.executeUpdate();

            // insert updated products into the order
            stat = conn.prepareStatement(insertQuery);
            for (Product product : order.getProducts()) {
                stat.setString(1, order.getOrderID());
                stat.setInt(2, product.getID());
                stat.setInt(3, product.getQuantity());
                stat.executeUpdate();
            }

            conn.commit();
        } catch (IOException ex) {
            throw new SQLException("Cannot load DB driver configuration");
        } 

        return row;    
    }

    @Override
    public int delete(String id) throws SQLException{
        String deleteOrderDetailsQuery = "DELETE FROM orderDetails WHERE oid = ?";
        String deleteOrderQuery = "DELETE FROM productOrder WHERE oid = ?";

        int row = 0;
        try (Connection conn = getConnection()) {
            PreparedStatement stat = conn.prepareStatement(deleteOrderDetailsQuery);
            stat.setString(1, id);
            row = stat.executeUpdate();
            stat = conn.prepareStatement(deleteOrderQuery);
            stat.setString(1, id);
            row += stat.executeUpdate();
        } catch (IOException ex) {
            throw new SQLException("Cannot delete order from DB");
        }
        
        return row;
    }

    public static Connection getConnection() throws SQLException, IOException {
        var props = new Properties();

        File file = ResourceUtils.getFile("classpath:database.properties");
        InputStream in = new FileInputStream(file);
        props.load(in);
        
                    
        String drivers = props.getProperty("jdbc.drivers");
        if (drivers != null) 
            System.setProperty("jdbc.drivers", drivers); 
        String url = props.getProperty("jdbc.url");
        String username = props.getProperty("jdbc.username");
        String password = props.getProperty("jdbc.password");
        return DriverManager.getConnection(url, username, password); 
    }      
}
