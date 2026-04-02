package com.retailpulse.service;

import com.retailpulse.model.Product;
import com.retailpulse.repository.ProductRepository;

import java.util.List;

public class InventoryService {

    private ProductRepository productRepository;

    public InventoryService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    // 🔥 FINAL: return boolean (no printing here)
    public boolean addProduct(Product product) {
        return productRepository.save(product);
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product getProduct(int id) {
        return productRepository.findById(id);
    }
}
