package com.retailpulse.app;

import com.retailpulse.model.Product;
import com.retailpulse.repository.ProductRepository;
import com.retailpulse.repository.OrderRepository;
import com.retailpulse.repository.InMemoryProductRepository;
import com.retailpulse.repository.InMemoryOrderRepository;
import com.retailpulse.service.InventoryService;
import com.retailpulse.service.PurchaseService;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

public class RetailPulseApplication {

    public static void main(String[] args) {

        System.out.println("RetailPulse CLI Started...\n");

        Scanner scanner = new Scanner(System.in);

        // ✅ Phase 2 — Use interfaces
        ProductRepository productRepo = new InMemoryProductRepository();
        OrderRepository orderRepo = new InMemoryOrderRepository();

        // Services
        InventoryService inventoryService = new InventoryService(productRepo);
        PurchaseService purchaseService = new PurchaseService(productRepo, orderRepo);

        int productIdCounter = 1;
        int orderIdCounter = 100;

        while (true) {

            System.out.println("\n===== RetailPulse Menu =====");
            System.out.println("1. Add Product");
            System.out.println("2. View Products");
            System.out.println("3. Purchase Product");
            System.out.println("4. View Orders");
            System.out.println("5. Simulate Flash Sale");
            System.out.println("0. Exit");

            System.out.print("Enter choice: ");
            int choice = scanner.nextInt();

            switch (choice) {

                case 1:
                    System.out.print("Enter product name: ");
                    String name = scanner.next();

                    System.out.print("Enter price: ");
                    double price = scanner.nextDouble();

                    System.out.print("Enter stock quantity: ");
                    int stock = scanner.nextInt();

                    Product product = new Product(productIdCounter++, name, price, stock);
                    inventoryService.addProduct(product);

                    System.out.println("Product added successfully!");
                    break;

                case 2:
                    System.out.println("\nAll Products:");
                    inventoryService.getAllProducts().forEach(System.out::println);
                    break;

                case 3:
                    System.out.print("Enter product ID: ");
                    int pid = scanner.nextInt();

                    System.out.print("Enter quantity: ");
                    int qty = scanner.nextInt();

                    try {
                        purchaseService.purchaseProduct(orderIdCounter++, pid, qty);
                    } catch (Exception e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                    break;

                case 4:
                    System.out.println("\nAll Orders:");
                    orderRepo.findAll().forEach(System.out::println);
                    break;

                case 5:
                    simulateFlashSale(inventoryService, purchaseService);
                    break;

                case 0:
                    System.out.println("Exiting...");
                    scanner.close();
                    return;

                default:
                    System.out.println("Invalid choice");
            }
        }
    }

    // 🔥 FINAL FLASH SALE METHOD (PRO LEVEL)
    private static void simulateFlashSale(InventoryService inventoryService,
                                          PurchaseService purchaseService) {

        System.out.println("\n🔥 Starting FLASH SALE simulation...");

        int productId = 999;

        // Fresh product
        Product flashProduct = new Product(productId, "FlashItem", 1000, 10);
        inventoryService.addProduct(flashProduct);

        int numberOfUsers = 50;

        List<Thread> threads = new ArrayList<>();

        // ✅ Thread-safe counters
        AtomicInteger successCount = new AtomicInteger(0);
        AtomicInteger failureCount = new AtomicInteger(0);

        for (int i = 1; i <= numberOfUsers; i++) {

            int orderId = 1000 + i;
            int userId = i;

            Thread t = new Thread(() -> {
                try {
                    purchaseService.purchaseProduct(orderId, productId, 1);
                    successCount.incrementAndGet();
                    System.out.println("✅ User " + userId + " SUCCESS");
                } catch (Exception e) {
                    failureCount.incrementAndGet();
                    System.out.println("❌ User " + userId + " FAILED: " + e.getMessage());
                }
            });

            threads.add(t);
            t.start();
        }

        // ✅ Wait for all threads
        for (Thread t : threads) {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        Product finalProduct = inventoryService.getProduct(productId);

        // 🔥 FINAL RESULT
        System.out.println("\n===== FLASH SALE RESULT =====");
        System.out.println("Total Success: " + successCount.get());
        System.out.println("Total Failed: " + failureCount.get());
        System.out.println("Final Stock: " + finalProduct.getStockQuantity());
    }
}
