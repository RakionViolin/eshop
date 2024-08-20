package com.fred.eshop.model;

import java.util.List;
import java.util.Collections;
import java.util.Comparator;

public class SortByPrice implements SortStrategy {
    @Override public void sort(List<Computer> cart) {
        Collections.sort(cart, new Comparator<Computer>() {
            @Override public int compare(Computer c1, Computer c2) {
                return (int) (c2.getPrice() - c1.getPrice());
            }
        });
    }
}
