package com.fred.eshop.dao;

import java.sql.SQLException;
import java.util.List;

import com.fred.eshop.model.Product;

public interface ProductDAO {
    // CRUD
    void create(Product product) throws SQLException;
    Product read(int id) throws SQLException;
    List<Product> readAll() throws SQLException;
    List<Product> read(String id) throws SQLException;
    int update(Product product) throws SQLException;
    int delete(int id) throws SQLException;
}
