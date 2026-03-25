package com.retailpulse.repository;

import com.retailpulse.model.Product;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryProductRepository implements ProductRepository {

    private Map<Integer, Product> productStore = new ConcurrentHashMap<>();

    @Override
    public void save(Product product) {
        productStore.put(product.getId(), product);
    }

    @Override
    public Product findById(int id) {
        return productStore.get(id);
    }

    @Override
    public List<Product> findAll() {
        return new ArrayList<>(productStore.values());
    }

    @Override
    public void delete(int id) {
        productStore.remove(id);
    }
}
