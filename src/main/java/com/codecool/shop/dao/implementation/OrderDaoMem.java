package com.codecool.shop.dao.implementation;

import com.codecool.shop.dao.OrderDao;
import com.codecool.shop.model.Order;

import java.util.ArrayList;
import java.util.List;


public class OrderDaoMem implements OrderDao {
    List<Order> allOrders = new ArrayList<>();

    private static OrderDaoMem instance = null;

    private OrderDaoMem() {

    }

    public static OrderDaoMem getInstance() {
        if (instance == null) {
            instance = new OrderDaoMem();
        }
        return instance;
    }

    @Override
    public void add(Order order) {
        allOrders.add(order);
    }

    @Override
    public Order find(int id) {

        return allOrders.stream().filter(t -> t.getId() == id).findFirst().orElse(null);


    }

    @Override
    public void remove(int id) {
        allOrders.remove(id);
    }

    @Override
    public List<Order> getAll() {
        return allOrders;
    }
}
