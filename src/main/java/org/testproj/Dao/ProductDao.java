package org.testproj.Dao;

import org.testproj.Entities.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

//TODO: implement all methods
public class ProductDao implements Dao<Product> {
    private List<Product> products = new ArrayList<>();

    @Override
    public Optional<Product> get(int id) {
        return Optional.of(products.get(id));
    }

    @Override
    public List<Product> getAll() {
        return null;
    }

    @Override
    public void save(Product obj) {

    }

    @Override
    public void update(Product obj, String[] params) {

    }

    @Override
    public void delete(Product product) {

    }
}
