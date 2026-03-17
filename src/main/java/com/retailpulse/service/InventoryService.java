package com.retailpulse.service;

import com.retailpulse.model.Product;
import com.retailpulse.repository.ProductRepository;

import java.util.List;

public class InventoryService {

    private ProductRepository productRepository;

    public InventoryService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    // Add product
    public void addProduct(Product product) {
        productRepository.save(product);
    }

    // View all products
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    // Get product by id
    public Product getProduct(int id) {
        return productRepository.findById(id);
    }

    // Update stock
    public void updateStock(int productId, int quantity) {
        Product product = productRepository.findById(productId);
        product.increaseStock(quantity);
    }
}
