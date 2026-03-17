package com.retailpulse.repository;

import com.retailpulse.model.Product;
import java.util.List;

public interface ProductRepository {

    void save(Product product);

    Product findById(int id);

    List<Product> findAll();

    void delete(int id);
}
