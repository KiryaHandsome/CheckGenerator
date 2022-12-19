package org.testproj.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.testproj.Models.DiscountCard;
import org.testproj.Models.Product;
import org.testproj.Repositories.ProductRepository;

import java.util.List;

public abstract class AbstractShopService<T> {
    protected JpaRepository<T, Integer> repository;

    public AbstractShopService(JpaRepository<T, Integer> repository) {
        this.repository = repository;
    }

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
