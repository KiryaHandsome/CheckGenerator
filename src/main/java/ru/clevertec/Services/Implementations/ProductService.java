package ru.clevertec.Services.Implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.clevertec.Models.Product;
import ru.clevertec.Repositories.ProductRepository;
import ru.clevertec.Services.AbstractShopService;

@Service
public class ProductService extends AbstractShopService<Product> {

    @Autowired
    public ProductService(ProductRepository productRepository) {
        super(productRepository);
    }

    @Override
    public Product update(int id, Product object) {
        object.setId(id);
        return repository.save(object);
    }
}
