package com.codecool.shop.model;

import java.util.ArrayList;

public class Cart {
    ArrayList<Product> cartContent;

    public Cart() {
        this.cartContent = new ArrayList<>();
    }

    public void add(Product product) {
        this.cartContent.add(product);
    }

    public void remove(Product product) {
        this.cartContent.remove(product);
    }

    public ArrayList<Product> getCartContent() {
        return cartContent;
    }
}
