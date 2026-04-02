package com.retailpulse.service;

import com.retailpulse.model.Order;
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

    // 🔥 FINAL CLEAN VERSION
    public boolean purchaseProduct(int orderId, int productId, int quantity) {

        try {
            if (productRepository instanceof DbProductRepository dbRepo) {

                boolean success = dbRepo.decreaseStockAtomic(productId, quantity);

                if (!success) {
                    return false;
                }

                Order order = new Order(orderId, productId, quantity);
                orderRepository.save(order);

                return true;
            }

            return false;

        } catch (Exception e) {
            return false;
        }
    }
}
