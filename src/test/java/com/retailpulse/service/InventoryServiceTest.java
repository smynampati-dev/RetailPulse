package com.retailpulse.service;

import com.retailpulse.model.Product;
import com.retailpulse.repository.InMemoryProductRepository;
import com.retailpulse.repository.InMemoryOrderRepository;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class InventoryServiceTest {

    @Test
    void testAddProduct() {

        InMemoryProductRepository repo = new InMemoryProductRepository();
        InventoryService service = new InventoryService(repo);

        Product product = new Product(1, "Phone", 50000, 10);

        service.addProduct(product);

        assertEquals(1, service.getAllProducts().size());
        assertEquals("Phone", service.getProduct(1).getName());
    }

    @Test
    void testPurchaseSuccess() {

        InMemoryProductRepository productRepo = new InMemoryProductRepository();
        InMemoryOrderRepository orderRepo = new InMemoryOrderRepository();

        InventoryService inventoryService = new InventoryService(productRepo);
        PurchaseService purchaseService = new PurchaseService(productRepo, orderRepo);

        Product product = new Product(1, "Phone", 50000, 10);
        inventoryService.addProduct(product);

        boolean result = purchaseService.purchaseProduct(100, 1, 2);

        assertTrue(result);
        assertEquals(8, inventoryService.getProduct(1).getStockQuantity());
        assertEquals(1, orderRepo.findAll().size());
    }

    @Test
    void testPurchaseFailure_NotEnoughStock() {

        InMemoryProductRepository productRepo = new InMemoryProductRepository();
        InMemoryOrderRepository orderRepo = new InMemoryOrderRepository();

        InventoryService inventoryService = new InventoryService(productRepo);
        PurchaseService purchaseService = new PurchaseService(productRepo, orderRepo);

        Product product = new Product(1, "Phone", 50000, 2);
        inventoryService.addProduct(product);

        boolean result = purchaseService.purchaseProduct(100, 1, 5);

        assertFalse(result);
        assertEquals(2, inventoryService.getProduct(1).getStockQuantity());
        assertEquals(0, orderRepo.findAll().size());
    }

    @Test
    void testPurchaseFailure_ProductNotFound() {

        InMemoryProductRepository productRepo = new InMemoryProductRepository();
        InMemoryOrderRepository orderRepo = new InMemoryOrderRepository();

        PurchaseService purchaseService = new PurchaseService(productRepo, orderRepo);

        boolean result = purchaseService.purchaseProduct(100, 999, 1);

        assertFalse(result);
        assertEquals(0, orderRepo.findAll().size());
    }
}
