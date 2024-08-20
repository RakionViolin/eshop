package com.fred.eshop.controller;

import java.util.List;
import org.springframework.web.bind.annotation.RestController;

import com.fred.eshop.model.Order;
import com.fred.eshop.model.Product;
import com.fred.eshop.model.Component;
import com.fred.eshop.model.Computer;
import com.fred.eshop.model.ComputerBase;
import com.fred.eshop.service.OrderService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;


import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
@CrossOrigin
@RestController
@RequestMapping("/eshop/order")
public class OrderAPI {

    @Autowired
    OrderService oService;

    @GetMapping(value="/get/{id}", produces="application/json")
    public Order getOrder(@PathVariable String id) {
        return oService.read(id);
    }

    @GetMapping(value="/getall", produces="application/json")
    public List<Order> getOrders() {
        return oService.readAll();
    }

    @PostMapping(value="/create", consumes="application/json")
    public void createOrder(@RequestBody Order order ) {
        Computer computer = new ComputerBase();

        for (Product product : order.getProducts())
            computer = new Component(computer, product.getDescription(),product.getQuantity()*product.getPrice());

        order.setOrderID(computer.getOrderID());
        order.setDescription(computer.getDescription());
        order.setPrice((float)computer.getPrice());
        oService.create(order);        
    } 

    @PutMapping(value="/update", consumes="application/json")
    public void updateOrder(@RequestBody Order order ) {
        oService.update(order);        
    } 

    @DeleteMapping(value="/cancel/{id}")
    public void cancelOrder(@PathVariable String id ) {
        oService.cancel(id);        
    } 
}


