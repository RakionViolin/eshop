package com.fred.eshop.model;

public class ComputerDecorator implements Computer {
    private Computer computer;

    public ComputerDecorator(Computer computer) {
        this.computer = computer;
    }

    public String getDescription() {
        return this.computer.getDescription();
    }
    public double getPrice() {
        return this.computer.getPrice();
    }
    public String getOrderID() {
        return this.computer.getOrderID();
    }

    @Override
    public String toString() {
        return this.getDescription() + " " + this.getPrice();
    }
}
