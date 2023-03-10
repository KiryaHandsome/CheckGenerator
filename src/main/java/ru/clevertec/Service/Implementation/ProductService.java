package ru.clevertec.Service.Implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import ru.clevertec.Cache.Annotations.CacheEvict;
import ru.clevertec.Cache.Annotations.CachePut;
import ru.clevertec.Cache.Annotations.Cacheable;
import ru.clevertec.Model.Product;
import ru.clevertec.Repository.ProductRepository;
import ru.clevertec.Service.ShopService;

import java.util.List;

@Service
public class ProductService implements ShopService<Product> {
    private JpaRepository<Product, Integer> repository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.repository = productRepository;
    }

    @Override
    public Product create(Product product) {
        return repository.save(product);
    }

    @Override
    @Cacheable
    public Product find(int id) {
        return repository.getReferenceById(id);
    }

    @Override
    public List<Product> findAll() {
        return repository.findAll();
    }

    @Override
    public void deleteAll() {
        repository.deleteAll();
    }

    @Override
    @CachePut
    public Product update(int id, Product object) {
        object.setId(id);
        return repository.save(object);
    }

    @Override
    @CacheEvict
    public void delete(int id) {
        repository.deleteById(id);
    }
}
