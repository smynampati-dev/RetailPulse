package com.retailpulse.app;

import com.retailpulse.model.Product;
import com.retailpulse.repository.*;
import com.retailpulse.service.InventoryService;
import com.retailpulse.service.PurchaseService;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class RetailPulseApplication {

    public static void main(String[] args) {

        System.out.println("RetailPulse CLI Started...\n");

        Scanner scanner = new Scanner(System.in);

        ProductRepository productRepo = new DbProductRepository();
        OrderRepository orderRepo = new DbOrderRepository();

        InventoryService inventoryService = new InventoryService(productRepo);
        PurchaseService purchaseService = new PurchaseService(productRepo, orderRepo);

        int productIdCounter = getMaxProductId(productRepo) + 1;
        int orderIdCounter = (int) (System.currentTimeMillis() % 100000);

        while (true) {

            System.out.println("\n===== RetailPulse Menu =====");
            System.out.println("1. Add Product");
            System.out.println("2. View Products");
            System.out.println("3. Purchase Product");
            System.out.println("4. View Orders");
            System.out.println("5. Simulate Flash Sale");
            System.out.println("0. Exit");

            try {
                System.out.print("Enter choice: ");
                int choice = Integer.parseInt(scanner.nextLine());

                switch (choice) {

                    case 1:
                        System.out.print("Enter product name: ");
                        String name = scanner.nextLine();

                        System.out.print("Enter price: ");
                        double price = Double.parseDouble(scanner.nextLine());

                        System.out.print("Enter stock quantity: ");
                        int stock = Integer.parseInt(scanner.nextLine());

                        Product product = new Product(productIdCounter++, name, price, stock);

                        boolean added = inventoryService.addProduct(product);

                        if (added) {
                            System.out.println("✅ Product added successfully!");
                        } else {
                            System.out.println("❌ Failed to add product.");
                        }
                        break;

                    case 2:
                        System.out.println("\nAll Products:");
                        inventoryService.getAllProducts().forEach(System.out::println);
                        break;

                    case 3:
                        System.out.print("Enter product ID: ");
                        int pid = Integer.parseInt(scanner.nextLine());

                        System.out.print("Enter quantity: ");
                        int qty = Integer.parseInt(scanner.nextLine());

                        boolean result = purchaseService.purchaseProduct(orderIdCounter++, pid, qty);

                        if (!result) {
                            System.out.println("❌ Purchase failed!");
                        }
                        break;

                    case 4:
                        System.out.println("\nAll Orders:");
                        orderRepo.findAll().forEach(System.out::println);
                        break;

                    case 5:
                        runFlashSale(scanner, inventoryService, purchaseService, orderIdCounter);
                        break;

                    case 0:
                        System.out.println("Exiting...");
                        scanner.close();
                        return;

                    default:
                        System.out.println("Invalid choice");
                }

            } catch (Exception e) {
                System.out.println("❌ Invalid input! Please enter correct values.");
            }
        }
    }

    private static int getMaxProductId(ProductRepository repo) {
        return repo.findAll().stream()
                .mapToInt(Product::getId)
                .max()
                .orElse(0);
    }

    // 🔥 FLASH SALE (FIXED INPUT + SAFE)
    private static void runFlashSale(Scanner scanner,
                                     InventoryService inventoryService,
                                     PurchaseService purchaseService,
                                     int baseOrderId) {

        try {
            System.out.print("\nEnter product ID for flash sale: ");
            int productId = Integer.parseInt(scanner.nextLine());

            Product product = inventoryService.getProduct(productId);

            if (product == null) {
                System.out.println("❌ Product not found!");
                return;
            }

            if (product.getStockQuantity() <= 0) {
                System.out.println("❌ No stock available!");
                return;
            }

            System.out.println("🔥 Starting FLASH SALE for: " + product.getName());

            int numberOfUsers = 50;

            List<Thread> threads = new ArrayList<>();

            AtomicInteger successCount = new AtomicInteger(0);
            AtomicInteger failureCount = new AtomicInteger(0);

            for (int i = 1; i <= numberOfUsers; i++) {

                int orderId = baseOrderId + i;
                int userId = i;

                Thread t = new Thread(() -> {

                    boolean result = purchaseService.purchaseProduct(orderId, productId, 1);

                    if (result) {
                        successCount.incrementAndGet();
                        System.out.println("✅ User " + userId + " SUCCESS");
                    } else {
                        failureCount.incrementAndGet();
                        System.out.println("❌ User " + userId + " FAILED");
                    }
                });

                threads.add(t);
                t.start();
            }

            for (Thread t : threads) {
                t.join();
            }

            Product finalProduct = inventoryService.getProduct(productId);

            System.out.println("\n===== FLASH SALE RESULT =====");
            System.out.println("Total Success: " + successCount.get());
            System.out.println("Total Failed: " + failureCount.get());
            System.out.println("Final Stock: " + finalProduct.getStockQuantity());

        } catch (Exception e) {
            System.out.println("❌ Invalid input during flash sale.");
        }
    }
}
