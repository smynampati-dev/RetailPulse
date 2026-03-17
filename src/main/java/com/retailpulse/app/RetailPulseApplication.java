package com.retailpulse.app;

import com.retailpulse.model.Product;
import com.retailpulse.repository.InMemoryProductRepository;
import com.retailpulse.repository.InMemoryOrderRepository;
import com.retailpulse.service.InventoryService;
import com.retailpulse.service.PurchaseService;

import java.util.Scanner;

public class RetailPulseApplication {

    public static void main(String[] args) {

        System.out.println("RetailPulse CLI Started...\n");

        Scanner scanner = new Scanner(System.in);

        // Repositories
        InMemoryProductRepository productRepo = new InMemoryProductRepository();
        InMemoryOrderRepository orderRepo = new InMemoryOrderRepository();

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
                    System.out.println(inventoryService.getAllProducts());
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
                    System.out.println(orderRepo.findAll());
                    break;

                case 5:
                    System.out.println("\nStarting Flash Sale...");

                    Product flashProduct = new Product(999, "FlashItem", 1000, 5);
                    inventoryService.addProduct(flashProduct);

                    for (int i = 1; i <= 10; i++) {
                        int userId = i;

                        new Thread(() -> {
                            try {
                                purchaseService.purchaseProduct(1000 + userId, 999, 1);
                                System.out.println("User " + userId + " purchased successfully");
                            } catch (Exception e) {
                                System.out.println("User " + userId + " failed: " + e.getMessage());
                            }
                        }).start();
                    }

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
}
