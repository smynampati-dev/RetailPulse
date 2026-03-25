package com.retailpulse.service;

import com.retailpulse.model.Product;
import com.retailpulse.repository.ProductRepository;

import java.util.List;

public class InventoryService {

    private ProductRepository productRepository;

    public InventoryService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public void addProduct(Product product) {
        productRepository.save(product);
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product getProduct(int id) {
        return productRepository.findById(id);
    }

    public void updateStock(int productId, int quantity) {
        Product product = productRepository.findById(productId);

        if (product == null) {
            throw new RuntimeException("Product not found!");
        }

        product.increaseStock(quantity);
    }
}
