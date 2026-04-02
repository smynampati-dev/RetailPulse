package com.retailpulse.service;

import com.retailpulse.model.Product;
import com.retailpulse.repository.InMemoryOrderRepository;
import com.retailpulse.repository.InMemoryProductRepository;
import com.retailpulse.repository.OrderRepository;
import com.retailpulse.repository.ProductRepository;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PurchaseServiceTest {

    @Test
    void testSuccessfulPurchase() {

        ProductRepository productRepo = new InMemoryProductRepository();
        OrderRepository orderRepo = new InMemoryOrderRepository();

        PurchaseService purchaseService = new PurchaseService(productRepo, orderRepo);

        Product product = new Product(1, "Test", 100, 10);
        productRepo.save(product);

        purchaseService.purchaseProduct(1, 1, 2);

        Product updated = productRepo.findById(1);

        assertEquals(8, updated.getStockQuantity());
    }

    @Test
    void testInsufficientStock() {

        ProductRepository productRepo = new InMemoryProductRepository();
        OrderRepository orderRepo = new InMemoryOrderRepository();

        PurchaseService purchaseService = new PurchaseService(productRepo, orderRepo);

        Product product = new Product(1, "Test", 100, 1);
        productRepo.save(product);

        Exception exception = assertThrows(RuntimeException.class, () -> {
            purchaseService.purchaseProduct(1, 1, 2);
        });

        assertTrue(exception.getMessage().contains("Out of stock"));
    }

    @Test
    void testProductNotFound() {

        ProductRepository productRepo = new InMemoryProductRepository();
        OrderRepository orderRepo = new InMemoryOrderRepository();

        PurchaseService purchaseService = new PurchaseService(productRepo, orderRepo);

        Exception exception = assertThrows(RuntimeException.class, () -> {
            purchaseService.purchaseProduct(1, 99, 1);
        });

        assertTrue(exception.getMessage().contains("Product not found"));
    }
    @Test
    void testMultiplePurchasesReduceStockCorrectly() {

        ProductRepository productRepo = new InMemoryProductRepository();
        OrderRepository orderRepo = new InMemoryOrderRepository();

        PurchaseService purchaseService = new PurchaseService(productRepo, orderRepo);

        Product product = new Product(1, "Test", 100, 5);
        productRepo.save(product);

        purchaseService.purchaseProduct(1, 1, 2);
        purchaseService.purchaseProduct(2, 1, 2);

        Product updated = productRepo.findById(1);

        assertEquals(1, updated.getStockQuantity());
    }

    @Test
    void testPurchaseExactStock() {

        ProductRepository productRepo = new InMemoryProductRepository();
        OrderRepository orderRepo = new InMemoryOrderRepository();

        PurchaseService purchaseService = new PurchaseService(productRepo, orderRepo);

        Product product = new Product(1, "Test", 100, 2);
        productRepo.save(product);

        purchaseService.purchaseProduct(1, 1, 2);

        Product updated = productRepo.findById(1);

        assertEquals(0, updated.getStockQuantity());
    }
}
