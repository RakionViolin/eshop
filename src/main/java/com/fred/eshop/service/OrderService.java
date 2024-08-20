package com.fred.eshop.service;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.fred.eshop.dao.OrderDAO;
import com.fred.eshop.dao.OrderDAOMySql;
import com.fred.eshop.dao.ProductDAO;
import com.fred.eshop.dao.ProductDAOMySql;
import com.fred.eshop.model.Order;
import com.fred.eshop.model.Product;


import java.util.ArrayList;

@Component
public class OrderService {
    @Autowired
    private OrderDAO odao;

    public void create(Order order) {
        // OrderDAO dao = new OrderDAOMySql();
        try {
            odao.create(order);

            // update stock in products
            List<Product> products = order.getProducts();
            ProductService pService = new ProductService();
            // decrease product stock by refunded product quantity
            for (Product product : products)
                product.setQuantity(pService.read(product.getID()).getQuantity() - product.getQuantity());
            pService.updateAll(products);
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("Error creating order in db");
        }
    }

    public Order read(String id) {
        // OrderDAO dao = new OrderDAOMySql();
   
        Order order = null;
        try {
            order = odao.read(id);
        } catch (SQLException ex) {
            System.out.println("Error reading order in db");
        }

        return order;
    }    

    public List<Order> readAll() {
        OrderDAO dao = new OrderDAOMySql();
        List<Order> orders = new ArrayList<>();
        try {
            orders = dao.readAll();
        } catch (SQLException ex) {
            System.out.println("Error reading orders in db");
        }

        return orders;
    }    
    @Transactional
    public void update(Order order){        
            cancel(order.getOrderID());
            create(order);
    }

    @Transactional
    public void cancel(String id) {
        OrderDAO dao = new OrderDAOMySql();  

       try {
            // update stock in products
            Order order = dao.read(id);
            List<Product> products = order.getProducts();
            ProductService pService = new ProductService();
            // increase product stock by refunded product quantity
            for (Product product : products){
                System.out.println(product.getImage());
                product.setQuantity(pService.read(product.getID()).getQuantity() + product.getQuantity() );
            }
            pService.updateAll(products);

            dao.delete(id);
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("Error cancel order in db");
        }              
    }
}
