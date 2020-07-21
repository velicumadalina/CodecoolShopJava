package com.codecool.shop.model;

import java.util.*;
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


    public int getProductFrequency(Product product){
        int frequency = 0;
        for(Product elem: cartContent){
            if(elem.getName().equals(product.getName())){
                frequency++;
            }
        }
        return frequency;
    }

    public List<Integer> getFrequencies() {
        List<Integer> freqs = new ArrayList<>();
        for (Product elem : getDistinctProductsJDBC()) {
            freqs.add(getProductFrequency(elem));
        }
        return freqs;
    }

    public List<Product> getDistinctProducts(){
        return cartContent.stream().distinct().collect(Collectors.toList());
    }

    public List<Product> getDistinctProductsJDBC(){
        Set<String> set = new HashSet<>(cartContent.size());
        return cartContent.stream().filter(p -> set.add(p.getName())).collect(Collectors.toList());    }

    public List<String> getNamesAndQuantities() {
        List<String> namesAndQuantities = new ArrayList<>();
        for (int i = 0; i < getDistinctProductsJDBC().size(); i++) {
            namesAndQuantities.add(getDistinctProductsJDBC().get(i).getName() + " - " + getFrequencies().get(i) + " item(s)");
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