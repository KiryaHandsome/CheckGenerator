package org.testproj.Services.Implementations;


import org.springframework.stereotype.Service;
import org.testproj.Models.Product;
import org.testproj.Services.ShopService;

import java.util.List;

@Service
public class DiscountCardService implements ShopService {
    @Override
    public Product find(int id) {
        return null;
    }

    @Override
    public List<Product> findAll() {
        return null;
    }

    @Override
    public Product create(Product object) {
        return null;
    }

    @Override
    public Product update(int id, Product object) {
        return null;
    }

    @Override
    public boolean delete(int id) {
        return false;
    }

    @Override
    public boolean deleteAll() {
        return false;
    }
}
