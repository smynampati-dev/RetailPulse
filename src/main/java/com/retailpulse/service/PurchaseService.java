package com.retailpulse.service;

import com.retailpulse.model.Order;
import com.retailpulse.model.Product;
import com.retailpulse.repository.OrderRepository;
import com.retailpulse.repository.ProductRepository;

public class PurchaseService {

    private ProductRepository productRepository;
    private OrderRepository orderRepository;

    public PurchaseService(ProductRepository productRepository,
                           OrderRepository orderRepository) {
        this.productRepository = productRepository;
        this.orderRepository = orderRepository;
    }

    public void purchaseProduct(int orderId, int productId, int quantity) {

        Product product = productRepository.findById(productId);

        if (product == null) {
            throw new RuntimeException("Product not found!");
        }

        // 🔥 Fine-grained locking
        synchronized (product) {

            boolean success = product.decreaseStock(quantity);

            if (!success) {
                throw new RuntimeException("Not enough stock!");
            }

            Order order = new Order(orderId, productId, quantity);
            orderRepository.save(order);

            System.out.println("Purchase successful: " + order);
        }
    }
}
