package com.codecool.shop.model;

import java.util.ArrayList;

public class Cart extends BaseModel{
    ArrayList<Product> cartContent;

    public Cart(int id) {
        super(id);
        this.cartContent = new ArrayList<>();
    }

    public void add(Product product) { this.cartContent.add(product); }

    public void remove(Product product) {
        this.cartContent.remove(product);
    }

    public ArrayList<Product> getCartContent() {
        return cartContent;
    }
}
