package com.fred.eshop.dao;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Properties;

import org.springframework.util.ResourceUtils;

import com.fred.eshop.model.Product;

public class ProductDAOMySql implements ProductDAO {
    @Override
    public void create(Product product) throws SQLException {
        String createQuery = "INSERT INTO product (name, price, quantity, image) VALUES (?, ?, ?, ?)";
        try (Connection conn = getConnection()) {
            PreparedStatement stat = conn.prepareStatement(createQuery, Statement.RETURN_GENERATED_KEYS);
            stat.setString(1, product.getDescription());
            stat.setFloat(2, product.getPrice());
            stat.setInt(3, product.getQuantity());
            stat.setString(4, product.getImage());            
            stat.executeUpdate();

            ResultSet rs = stat.getGeneratedKeys();
            int id = -1;
            if (rs.next()) {
                id = rs.getInt(1);
                product.setID(id);
            }
        } catch (IOException ex) {
            throw new SQLException("Can not connect to DB");
        }
    }

    @Override
    public Product read(int id) throws SQLException {
        Product product = null;
        try (Connection conn = getConnection(); Statement stat = conn.createStatement()) {
            try (ResultSet rs = stat.executeQuery("SELECT * FROM product WHERE pid=" + id)) {
                if (rs.next())
                    product = new Product(rs.getString(2), rs.getFloat(3), rs.getInt(4), rs.getString(5));
                    product.setID(rs.getInt(1));
            }       
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new SQLException("Can not connect to DB");
        }

        return product;
    }

    @Override
    public List<Product> read(String id) throws SQLException {
        List<Product> products = new ArrayList<Product>();
        try (Connection conn = getConnection(); Statement stat = conn.createStatement()) {
            try (ResultSet rs = stat.executeQuery("SELECT * FROM product WHERE oid=" + id)) {
                while (rs.next()) {
                    Product product = new Product(rs.getString(2), rs.getFloat(3), rs.getInt(4), rs.getString(5));
                    product.setID(rs.getInt(1));
                    products.add(product);
                }
            }
        } catch (IOException ex) {
            throw new SQLException("Can not connect to DB");
        }

        return products;
    }    

    @Override
    public List<Product> readAll() throws SQLException {
        List<Product> products = new ArrayList<Product>();
        try (Connection conn = getConnection(); Statement stat = conn.createStatement()) {
            try (ResultSet rs = stat.executeQuery("SELECT * FROM product")) {
                while (rs.next()) {
                    Product product = new Product(rs.getString(2), rs.getFloat(3), rs.getInt(4), rs.getString(5));
                    product.setID(rs.getInt(1));
                    products.add(product);
                }
            }
        } catch (IOException ex) {
            throw new SQLException("Can not connect to DB");
        }

        return products;
    }

    @Override
    public int update(Product product) throws SQLException {
        String updateQuery = "UPDATE product SET name=?, price=?, quantity=?, image=? WHERE pid=?";
        int row = 0;
        
        try (Connection conn = getConnection()) {
            PreparedStatement stat = conn.prepareStatement(updateQuery);
            stat.setString(1, product.getDescription());
            stat.setFloat(2, product.getPrice());            
            stat.setInt(3, product.getQuantity());
            stat.setString(4, product.getImage());             
            stat.setInt(5, product.getID());           
            row = stat.executeUpdate();
        } catch (IOException ex) {
            throw new SQLException("Can not connect to DB");
        }

        return row;    
    }

    @Override
    public int delete(int id) throws SQLException{
        String deleteQuery = "DELETE FROM product WHERE pid = ?";

        int row = 0;
        try (Connection conn = getConnection()) {
            PreparedStatement stat = conn.prepareStatement(deleteQuery);
            stat.setInt(1, id);
            row = stat.executeUpdate();
        } catch (IOException ex) {
            throw new SQLException("Can not connect to DB");
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
