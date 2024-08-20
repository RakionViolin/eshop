package com.fred.eshop.test;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;
import java.util.Properties;

import com.fred.eshop.dao.OrderDAO;
import com.fred.eshop.dao.OrderDAOMySql;
import com.fred.eshop.dao.ProductDAO;
import com.fred.eshop.dao.ProductDAOMySql;
import com.fred.eshop.model.Order;
import com.fred.eshop.model.Product;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;

public class DAOTest {
    public static void main(String args[]) throws IOException {
        try {
            //testRead(); 
            //testCreate();
            //testDelete();
            // testUpdate();
            // testReadAll();
             testReadOrder();
            // testCreateOrder();
             //testReadAllOrder();
            // testUpdateOrder();
            // testReadAllOrder();   
            //testDeleteOrder();    
        } catch (SQLException ex) {
            for (Throwable t : ex) t.printStackTrace();
        } 
    }    
    
    public static void testRead() throws SQLException {
        ProductDAO dao = new ProductDAOMySql();
        Product product = dao.read(1);
        System.out.println(product);
    }

    public static void testCreate() throws SQLException {
        ProductDAO dao = new ProductDAOMySql();
        Product product = new Product("New component", 9.99f, 1, "images/keyboard.jpg");
        dao.create(product);
        System.out.println(product);
    }    

    public static void testReadAll() throws SQLException {
        ProductDAO dao = new ProductDAOMySql();
        List<Product> products = dao.readAll();
        for (Product product : products)
            System.out.println(product);
    }

    public static void testDelete() throws SQLException {
        ProductDAO dao = new ProductDAOMySql();
        int row = dao.delete(20);
        System.out.println(row + "");
    }

    public static void testUpdate() throws SQLException {
        ProductDAO dao = new ProductDAOMySql();
        Product product = new Product("Our latest", 99.9f, 1,"images/keyboard.jpg");
        product.setID(10);
        int row = dao.update(product);
        System.out.println(row + "");
    }
    
    public static void runTest() throws SQLException, IOException {
        try (Connection conn = getConnection(); Statement stat = conn.createStatement()) {
                try (ResultSet rs = stat.executeQuery("SELECT * FROM product")) {
                    while (rs.next())
                        System.out.printf("%s: %s, %.2f\n", rs.getInt(1), rs.getString(2), rs.getDouble(3));
                }
        }
    }    

    public static void testReadOrder() throws SQLException {
        OrderDAO dao = new OrderDAOMySql();
        Order order = dao.read("96");
        System.out.println(order);
    }

   public static void testReadAllOrder() throws SQLException {
        OrderDAO dao = new OrderDAOMySql();
        List<Order> orders = dao.readAll();
        for (Order order : orders)
            System.out.println(order);
    }

    public static void testDeleteOrder() throws SQLException {
        OrderDAO dao = new OrderDAOMySql();
        int row = dao.delete("30");
        System.out.println(row + "");
    }

    public static void testCreateOrder() throws SQLException {
        ProductDAO pdao = new ProductDAOMySql();
        List<Product> products = new ArrayList<>();
        Product product = pdao.read(1);
        int stock = product.getQuantity();
        if (stock > 0 ) {
            product.setQuantity(1);
            products.add(product);
        }

        Product product3 = pdao.read(3);
        int stock3 = product3.getQuantity();
        if (stock3 > 1 ) {
            product3.setQuantity(2);
            products.add(product3);
        }      

        OrderDAO dao = new OrderDAOMySql();
        Date now = Calendar.getInstance().getTime();
        Order order = new Order("16", "New Order", 9.99f, now, products);
        dao.create(order);
        System.out.println(order);

        product.setQuantity(stock-1);
        product3.setQuantity(stock3-2);
        pdao.update(product);
        pdao.update(product3);
    }    

    public static void testUpdateOrder() throws SQLException {
        OrderDAO dao = new OrderDAOMySql();
        Order order = new Order("12", "Updated order", 19.99f, Calendar.getInstance().getTime(), null);
        int row = dao.update(order);
        System.out.println(row + "");
    }

    public static Connection getConnection() throws SQLException, IOException {
        var props = new Properties();
        try (InputStream in = Files.newInputStream(Paths.get("demo/database.properties"))) {
            props.load(in); 
        }
        String drivers = props.getProperty("jdbc.drivers");
        if (drivers != null) 
            System.setProperty("jdbc.drivers", drivers); 
        String url = props.getProperty("jdbc.url");
        String username = props.getProperty("jdbc.username");
        String password = props.getProperty("jdbc.password");
        return DriverManager.getConnection(url, username, password); 
    }
}
