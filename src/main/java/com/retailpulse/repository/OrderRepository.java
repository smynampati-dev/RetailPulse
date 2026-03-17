package com.retailpulse.repository;

import com.retailpulse.model.Order;
import java.util.List;

public interface OrderRepository {

    void save(Order order);

    List<Order> findAll();
}
