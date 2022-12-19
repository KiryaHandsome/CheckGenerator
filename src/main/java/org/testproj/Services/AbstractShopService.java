package org.testproj.Services;

import org.springframework.data.jpa.repository.JpaRepository;
import org.testproj.Models.DiscountCard;
import org.testproj.Models.Product;

import java.util.List;

public abstract class AbstractShopService<T> {
    private JpaRepository<T, Integer> repository;

    public T create(T product) {
        return repository.save(product);
    }

    public T find(int id) {
        return repository.getReferenceById(id);
    }

    public List<T> findAll() {
        return repository.findAll();
    }

    public void deleteAll() {
        repository.deleteAll();
    }
}
