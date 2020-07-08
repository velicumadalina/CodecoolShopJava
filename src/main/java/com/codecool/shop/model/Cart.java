package com.codecool.shop.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Cart extends BaseModel{
    List <Product> cartContent;
    int total;

    public Cart(int id) {
        super(id);
        this.cartContent = new ArrayList<>();
    }

    public void add(Product product) { this.cartContent.add(product); }

    public void remove(Product product) {
        this.cartContent.remove(product);
    }

    public List<Product> getCartContent() {
        return cartContent;
    }




    public List<Integer> getFrequencies() {
        List<Integer> freqs = new ArrayList<>();
        for (Product elem : cartContent.stream().distinct().collect(Collectors.toList())) {
            freqs.add(Collections.frequency(cartContent, elem));
        }
        return freqs;
    }

    public List<Product> getDistinctProducts(){
        return cartContent.stream().distinct().collect(Collectors.toList());
    }


    public List<String> getNamesAndQuantities() {
        List<String> namesAndQuantities = new ArrayList<>();
        for (int i = 0; i < cartContent.size(); i++) {
            namesAndQuantities.add(cartContent.get(i).getName() + " - " + getFrequencies().get(i) + " item(s)");
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

    public void clearCart(){
        cartContent.clear();
        total = 0;
    }

    public int totalPrice(){
        for(Product prod: cartContent){
            total += prod.getPrice();
        }
        return total;
    }
}
