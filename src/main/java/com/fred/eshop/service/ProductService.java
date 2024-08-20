package com.fred.eshop.service;

import java.sql.SQLException;
import java.util.List;

import com.fred.eshop.dao.ProductDAO;
import com.fred.eshop.dao.ProductDAOMySql;
import com.fred.eshop.model.Product;

import java.util.ArrayList;

public class ProductService {
    public void create(Product product) {
        ProductDAO dao = new ProductDAOMySql();
        try {
            dao.create(product);
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("Error creating product in db");
        }
    }

    public List<Product> readAll() {
        ProductDAO dao = new ProductDAOMySql();
        List<Product> products = new ArrayList<>();
        try {
            products = dao.readAll();
        } catch (SQLException ex) {
            System.out.println("Error reading products in db");
        }

        return products;
    }  

    public Product read(int id) {
        ProductDAO dao = new ProductDAOMySql();
        Product product = null;
        try {
            product = dao.read(id);
        } catch (SQLException ex) {
            System.out.println("Error reading product in db");
        }

        return product;
    }     
    
    public void updateAll(List<Product> products) {
        ProductDAO dao = new ProductDAOMySql();
        
        for (Product product : products)
            try {
                dao.update(product);     
            } catch (SQLException ex) {
                System.out.println("Error updating products in db");
            }        
    }

    public void update(Product product) {
        ProductDAO dao = new ProductDAOMySql();        
        
            try {
                dao.update(product);     
            } catch (SQLException ex) {
                System.out.println("Error updating products in db");
            }        
    }
    public int delete(int id) {
        ProductDAO dao = new ProductDAOMySql();
        int n = 0;        
        try {
            n = dao.delete(id);
        } catch (SQLException ex) {
            System.out.println("Error deleting product in db");
        }

        return n;
    }
         
}


