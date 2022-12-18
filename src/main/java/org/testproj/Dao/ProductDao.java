package org.testproj.Dao;

import org.testproj.Entities.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

//TODO: implement all methods for database
public class ProductDao implements Dao<Product> {
    private List<Product> products = new ArrayList<>();

    @Override
    public Product get(int id) {
        return products.stream()
                .filter(p -> p.getId() == id)
                .findFirst()
                .orElse(null);
    }

    @Override
    public void save(Product obj) {
        products.add(obj);
    }

    @Override
    public void update(Product obj, Product other) {
        int index = products.indexOf(obj);
        products.get(index).setFields(other);
    }

    @Override
    public void delete(Product product) {
        products.remove(product);
    }
}
