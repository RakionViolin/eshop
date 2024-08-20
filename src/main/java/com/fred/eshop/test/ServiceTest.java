package com.fred.eshop.test;

import com.fred.eshop.model.Order;
import com.fred.eshop.model.Product;
import com.fred.eshop.service.OrderService;

public class ServiceTest {
    public static void main(String[] args) {
        //testReadOrder();
        testCancelOrder();
    }

    public static void testReadOrder() {
        OrderService oService = new OrderService();
        Order order = oService.read("73");
        System.out.println(order);

        for (Product product : order.getProducts())
            System.out.println(product);
    }

    public static void testCancelOrder() {
        OrderService oService = new OrderService();
        Order order = oService.read("73");
        oService.cancel("73");
    }
}
