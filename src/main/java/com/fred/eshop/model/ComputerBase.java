package com.fred.eshop.model;

import java.util.Random;
import java.util.List;
import java.util.stream.Collectors;

public class ComputerBase implements Computer {
    private static final String NAME = "Default Computer";
    private static final int PRICE = 700;
    private static final int SIZE = 100;
    private static List<Integer> ids = new Random().ints(1, SIZE+1).distinct().limit(SIZE).boxed().collect(Collectors.toList());

    /*
    private static List<Integer> ids = new ArrayList<>();
    static {
        for (int i=0; i<SIZE; i++)
            ids.add(i+1);
    } */
    
    private String orderID;
    private String description;
    private double price;

    // default constructor
    public ComputerBase() {
        this(getID(),NAME, PRICE);
    }

    public ComputerBase(String orderID, String description, double price) {
        this.orderID = orderID;
        this.description = description;
        this.price = price;
    }

    @Override
    public String getDescription() {
        return this.description;
    }
    @Override
    public double getPrice() {
        return this.price;
    }
    @Override
    public String getOrderID() {
        return this.orderID;
    }

    @Override public String toString() {
        return this.orderID + " " + this.description + " " + this.price;
    }

    private static String getID() {
        // int index = new Random().nextInt(ids.size());
        return Integer.toString(ids.remove(0));
    }
}
