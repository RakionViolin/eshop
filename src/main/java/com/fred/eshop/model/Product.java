package com.fred.eshop.model;

public class Product {
    private int id;
    private String description;
    private float price;
    private int quantity;
    private String image;


    public Product(String description, float price, int quantity, String image) {
        this.description = description;
        this.price = price;
        this.quantity = quantity;
        this.image = image;
    }

    public int getID() {
        return this.id;
    }
    public void setID(int id) {
        this.id = id;
    }

    public String getDescription() {
        return this.description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public float getPrice() {
        return this.price;
    }
    public void setPrice(float price) {
        this.price = price;
    }

    public int getQuantity() {
        return this.quantity;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

        public void setImage(String image) {
        this.image = image;
    }

    public String getImage() {
        return image;
    }

    @Override
    public String toString() {
        return this.id + " " + this.description + " " + this.price + " " + this.quantity + this.image;
    }
}
