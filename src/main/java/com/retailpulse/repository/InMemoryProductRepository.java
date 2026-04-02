package com.retailpulse.repository;

import com.retailpulse.model.Product;

import java.util.*;

public class InMemoryProductRepository implements ProductRepository {

    private Map<Integer, Product> productMap = new HashMap<>();

    @Override
    public boolean save(Product product) {

        // ❌ Prevent duplicate name
        for (Product p : productMap.values()) {
            if (p.getName().equalsIgnoreCase(product.getName())) {
                System.out.println("❌ Product already exists!");
                return false;
            }
        }

        productMap.put(product.getId(), product);
        System.out.println("✅ Product added successfully!");
        return true;
    }

    @Override
    public Product findById(int id) {
        return productMap.get(id);
    }

    @Override
    public List<Product> findAll() {
        return new ArrayList<>(productMap.values());
    }

    @Override
    public void delete(int id) {
        productMap.remove(id);
    }
}
