package org.testproj.Services;

import org.testproj.Models.Product;

import java.util.List;

public interface ShopService {
    Product find(int id);

    List<Product> findAll();

    Product create(Product object);

    Product update(int id, Product object);

    boolean delete(int id);

    boolean deleteAll();
}
