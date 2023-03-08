package ru.clevertec.Service.Implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.clevertec.Model.Product;
import ru.clevertec.Repository.ProductRepository;
import ru.clevertec.Service.AbstractShopService;

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
