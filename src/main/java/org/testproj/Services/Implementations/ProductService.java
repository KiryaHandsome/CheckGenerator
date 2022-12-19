package org.testproj.Services.Implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.testproj.Models.Product;
import org.testproj.Repositories.ProductRepository;
import org.testproj.Services.AbstractShopService;

//TODO : replace logic to common logic with discountCardService
@Service
public class ProductService extends AbstractShopService<Product> {
    //private final Logger LOGGER = LoggerFactory.getLogger(ProductService.class);
    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product update(int id, Product object) {
        object.setId(id);
        return productRepository.save(object);
    }

    public void delete(int id) {
        productRepository.delete(new Product().getBuilder()
                .setId(id)
                .build());
    }
}
