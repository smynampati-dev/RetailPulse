package com.retailpulse.repository;

import com.retailpulse.model.Product;
import java.util.List;

public interface ProductRepository {

    boolean save(Product product); // ✅ UPDATED

    Product findById(int id);

    List<Product> findAll();

    void delete(int id);
}
