package com.retailpulse.service;

import com.retailpulse.model.Order;
import com.retailpulse.model.Product;
import com.retailpulse.repository.OrderRepository;
import com.retailpulse.repository.ProductRepository;
import com.retailpulse.repository.DbProductRepository;

public class PurchaseService {

    private ProductRepository productRepository;
    private OrderRepository orderRepository;

    public PurchaseService(ProductRepository productRepository,
                           OrderRepository orderRepository) {
        this.productRepository = productRepository;
        this.orderRepository = orderRepository;
    }

    // 🔥 FINAL VERSION (DB + InMemory SUPPORT)
    public boolean purchaseProduct(int orderId, int productId, int quantity) {

        try {

            // 🔴 DB FLOW (PRODUCTION)
            if (productRepository instanceof DbProductRepository dbRepo) {

                boolean success = dbRepo.decreaseStockAtomic(productId, quantity);

                if (!success) {
                    return false;
                }

                orderRepository.save(new Order(orderId, productId, quantity));
                return true;
            }

            // 🟢 IN-MEMORY FLOW (FOR TESTING)
            Product product = productRepository.findById(productId);

            if (product == null) {
                return false;
            }

            synchronized (product) {
                if (product.getStockQuantity() < quantity) {
                    return false;
                }

                product.setStockQuantity(
                        product.getStockQuantity() - quantity
                );
            }

            orderRepository.save(new Order(orderId, productId, quantity));
            return true;

        } catch (Exception e) {
            return false;
        }
    }
}
