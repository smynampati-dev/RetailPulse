package com.retailpulse.model;

import java.time.LocalDateTime;

public class Order {

    private int orderId;
    private int productId;
    private int quantity;
    private LocalDateTime timestamp;

    public Order(int orderId, int productId, int quantity) {
        this.orderId = orderId;
        this.productId = productId;
        this.quantity = quantity;
        this.timestamp = LocalDateTime.now();
    }

    // ✅ ADD THIS
    public Order(int orderId, int productId, int quantity, LocalDateTime timestamp) {
        this.orderId = orderId;
        this.productId = productId;
        this.quantity = quantity;
        this.timestamp = timestamp;
    }

    public int getOrderId() { return orderId; }
    public int getProductId() { return productId; }
    public int getQuantity() { return quantity; }
    public LocalDateTime getTimestamp() { return timestamp; }

    @Override
    public String toString() {
        return "Order{id=" + orderId + ", productId=" + productId +
                ", quantity=" + quantity + ", time=" + timestamp + "}";
    }
}
