package com.retailpulse.repository;

import com.retailpulse.model.Order;

import java.util.ArrayList;
import java.util.List;

public class InMemoryOrderRepository implements OrderRepository {

    private List<Order> orderStore = new ArrayList<>();

    @Override
    public void save(Order order) {
        orderStore.add(order);
    }

    @Override
    public List<Order> findAll() {
        return orderStore;
    }
}
