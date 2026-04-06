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

        boolean result = purchaseService.purchaseProduct(1, 1, 2);

        assertTrue(result);
        assertEquals(8, productRepo.findById(1).getStockQuantity());
    }

    @Test
    void testInsufficientStock() {

        ProductRepository productRepo = new InMemoryProductRepository();
        OrderRepository orderRepo = new InMemoryOrderRepository();

        PurchaseService purchaseService = new PurchaseService(productRepo, orderRepo);

        Product product = new Product(1, "Test", 100, 1);
        productRepo.save(product);

        boolean result = purchaseService.purchaseProduct(1, 1, 2);

        assertFalse(result);
        assertEquals(1, productRepo.findById(1).getStockQuantity());
    }

    @Test
    void testProductNotFound() {

        ProductRepository productRepo = new InMemoryProductRepository();
        OrderRepository orderRepo = new InMemoryOrderRepository();

        PurchaseService purchaseService = new PurchaseService(productRepo, orderRepo);

        boolean result = purchaseService.purchaseProduct(1, 99, 1);

        assertFalse(result);
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

        assertEquals(1, productRepo.findById(1).getStockQuantity());
    }

    @Test
    void testPurchaseExactStock() {

        ProductRepository productRepo = new InMemoryProductRepository();
        OrderRepository orderRepo = new InMemoryOrderRepository();

        PurchaseService purchaseService = new PurchaseService(productRepo, orderRepo);

        Product product = new Product(1, "Test", 100, 2);
        productRepo.save(product);

        boolean result = purchaseService.purchaseProduct(1, 1, 2);

        assertTrue(result);
        assertEquals(0, productRepo.findById(1).getStockQuantity());
    }
}
