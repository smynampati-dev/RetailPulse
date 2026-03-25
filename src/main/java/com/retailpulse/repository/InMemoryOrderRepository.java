package com.retailpulse.repository;

import com.retailpulse.model.Order;
import java.util.*;

public class InMemoryOrderRepository implements OrderRepository {

    private List<Order> orderStore =
            Collections.synchronizedList(new ArrayList<>());

    @Override
    public void save(Order order) {
        orderStore.add(order);
    }

    @Override
    public List<Order> findAll() {
        return new ArrayList<>(orderStore);
    }
}
