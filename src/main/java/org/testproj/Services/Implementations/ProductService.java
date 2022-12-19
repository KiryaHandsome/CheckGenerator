package org.testproj.Services.Implementations;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.testproj.Models.Product;
import org.testproj.Repositories.ProductRepository;

import org.testproj.Services.ShopService;

import java.util.List;

@Service
public class ProductService implements ShopService {
    private final Logger LOGGER = LoggerFactory.getLogger(ProductService.class);
    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Product create(Product product) {
        return productRepository.save(product);
    }

    @Override
    public Product find(int id) {
        return productRepository.getReferenceById(id);
    }

    @Override
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    @Override
    public Product update(int id, Product product) {
        product.setId(id);
        return productRepository.save(product);
    }

    @Override
    public boolean delete(int id) {
        try {
            productRepository.delete(new Product().getBuilder()
                    .setId(id)
                    .build());
            return true;
        } catch (DataAccessException e) {
            LOGGER.info(e.getMessage());
            return false;
        }
    }

    @Override
    public boolean deleteAll() {
        try {
            productRepository.deleteAll();
            return true;
        } catch (DataAccessException e) {
            LOGGER.info(e.getMessage());
            return false;
        }
    }
}
