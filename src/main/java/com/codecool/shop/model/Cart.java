package com.codecool.shop.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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

    public List<Integer> getFrequencies(List<Product> distinct) {
        List<Integer> freqs = new ArrayList<>();
        for (Product elem : distinct) {
            freqs.add(Collections.frequency(cartContent, elem));
        }
        return freqs;
    }


    public List<String> getNamesAndQuantities(List<Product> products, List<Integer> quantities) {
        List<String> namesAndQuantities = new ArrayList<>();
        for (int i = 0; i < products.size(); i++) {
            namesAndQuantities.add(products.get(i).getName() + " - " + quantities.get(i) + " item(s)");
        }
        return namesAndQuantities;
    }

    public int totalItems(List<Integer> cart) {
        int total = 0;
        for (Integer quantity : cart) {
            total += quantity;
        }
        return total;
    }
}
