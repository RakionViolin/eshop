package com.fred.eshop.model;

import java.util.Date;
import java.util.List;

// Order domain object
public class Order {
    private String orderID;
    private String description;
    private float price;
    private Date date;
    private List<Product> products;

    public Order(String orderID, String description, float price, Date date, List<Product> products) {
        this.orderID = orderID;
        this.description = description;
        this.price = price;
        this.date = date;
        this.products = products;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String desc){
        this.description = desc;
    }

    public float getPrice() {
        return this.price;
    }

    public void setPrice(float price){
        this.price = price;
    }

    public String getOrderID() {
        return this.orderID;
    }

    public void setOrderID (String id){
        this.orderID = id;
    }

    public Date getDate() {
        //this.date =  new Timestamp(Calendar.getInstance().getTime().getTime());
        return this.date;
    }

    public List<Product> getProducts() {
        return this.products;
    }
    
    public String toString() {
        return orderID + " " + description + " $" + price + " " + date;
    }
}
