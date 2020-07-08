package com.codecool.shop.dao.implementation;

import com.codecool.shop.dao.CartDao;
import com.codecool.shop.model.Cart;
import com.codecool.shop.model.Product;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CartDaoMem implements CartDao {
    List<Cart> allCarts = new ArrayList<>();

    private static CartDaoMem instance = null;

    private CartDaoMem() {

    }

    public static CartDaoMem getInstance() {
        if (instance == null) {
            instance = new CartDaoMem();
        }
        return instance;
    }


    @Override
    public void add(Cart cart) {
        allCarts.add(cart);
    }

    @Override
    public Cart find(int id) {
        return allCarts.stream().filter(t -> t.getId() == id).findFirst().orElse(null);
    }

    @Override
    public void remove(int id) {
        allCarts.remove(id);
    }

    @Override
    public List<Cart> getAll() {
        return allCarts;
    }


}
