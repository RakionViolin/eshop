package com.fred.eshop.model;

import java.util.List;
import java.util.Collections;
import java.util.Comparator;

public class SortByOrderID implements SortStrategy {
    @Override public void sort(List<Computer> cart) {
        Collections.sort(cart, new Comparator<Computer>() {
            @Override public int compare(Computer c1, Computer c2) {
                return c2.getOrderID().compareTo(c1.getOrderID());
            }
        });
    }  
}
