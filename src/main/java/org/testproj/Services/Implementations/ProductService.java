package org.testproj.Services.Implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.testproj.Models.Product;
import org.testproj.Repositories.ProductRepository;
import org.testproj.Services.AbstractShopService;

@Service
public class ProductService extends AbstractShopService<Product> {

    @Autowired
    public ProductService(ProductRepository productRepository) {
        super(productRepository);
    }

    public Product update(int id, Product object) {
        object.setId(id);
        return repository.save(object);
    }

    public void delete(int id) {
        repository.delete(new Product().getBuilder()
                .setId(id)
                .build());
    }
}
