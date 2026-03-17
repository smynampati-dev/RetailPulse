package com.retailpulse.repository;

import com.retailpulse.model.Product;

import java.io.*;
import java.util.*;

public class FileProductRepository implements ProductRepository {

    private static final String FILE_NAME = "products.txt";

    @Override
    public void save(Product product) {
        try (FileWriter writer = new FileWriter(FILE_NAME, true)) {
            writer.write(product.getId() + "," +
                    product.getName() + "," +
                    product.getPrice() + "," +
                    product.getStockQuantity() + "\n");
        } catch (IOException e) {
            throw new RuntimeException("Error saving product to file");
        }
    }

    @Override
    public Product findById(int id) {
        for (Product p : findAll()) {
            if (p.getId() == id) return p;
        }
        return null;
    }

    @Override
    public List<Product> findAll() {
        List<Product> products = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");

                int id = Integer.parseInt(parts[0]);
                String name = parts[1];
                double price = Double.parseDouble(parts[2]);
                int stock = Integer.parseInt(parts[3]);

                products.add(new Product(id, name, price, stock));
            }

        } catch (FileNotFoundException e) {
            // file not created yet → ignore
        } catch (IOException e) {
            throw new RuntimeException("Error reading file");
        }

        return products;
    }

    @Override
    public void delete(int id) {
        List<Product> products = findAll();

        try (FileWriter writer = new FileWriter(FILE_NAME)) {
            for (Product p : products) {
                if (p.getId() != id) {
                    writer.write(p.getId() + "," +
                            p.getName() + "," +
                            p.getPrice() + "," +
                            p.getStockQuantity() + "\n");
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Error deleting product");
        }
    }
}
