package com.fred.eshop.model;

public class Component extends ComputerDecorator {
    private String description;
    private double price;

    public Component(Computer computer) {
        super(computer);
    }

    public Component(Computer computer, String description, double price) {
        super(computer);
        this.description = description;
        this.price = price;
    }

    @Override
    public String getDescription() {
        return super.getDescription() + " + " + this.description;
    }

    @Override
    public double getPrice() {
        return super.getPrice() + this.price;
    }
}
