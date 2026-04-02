package com.retailpulse.repository;

import com.retailpulse.model.Product;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileProductRepository implements ProductRepository {

    private static final String FILE_NAME = "products.txt";

    @Override
    public boolean save(Product product) {

        List<Product> products = findAll();

        // ❌ Prevent duplicate name
        for (Product p : products) {
            if (p.getName().equalsIgnoreCase(product.getName())) {
                System.out.println("❌ Product already exists!");
                return false;
            }
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME, true))) {

            writer.write(product.getId() + "," +
                    product.getName() + "," +
                    product.getPrice() + "," +
                    product.getStockQuantity());

            writer.newLine();

            System.out.println("✅ Product added successfully!");
            return true;

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Product findById(int id) {
        return findAll().stream()
                .filter(p -> p.getId() == id)
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<Product> findAll() {

        List<Product> products = new ArrayList<>();

        File file = new File(FILE_NAME);
        if (!file.exists()) return products;

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {

            String line;
            while ((line = reader.readLine()) != null) {

                String[] parts = line.split(",");

                products.add(new Product(
                        Integer.parseInt(parts[0]),
                        parts[1],
                        Double.parseDouble(parts[2]),
                        Integer.parseInt(parts[3])
                ));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return products;
    }

    @Override
    public void delete(int id) {
        // (optional for now)
    }
}
