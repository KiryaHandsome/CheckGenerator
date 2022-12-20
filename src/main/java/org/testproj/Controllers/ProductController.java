package org.testproj.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.testproj.Models.Product;
import org.testproj.Services.Implementations.ProductService;

import java.util.List;

@RestController
@RequestMapping("/")
public class ProductController {
    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/products")
    public List<Product> loadAll() {
        return productService.findAll();
    }

    @GetMapping("/product/{id}")
    public Product productById(@PathVariable String id) {
        return productService.find(Integer.parseInt(id));
    }

    @DeleteMapping("/product/{id}")
    public String removeProductById(@PathVariable String id) {
        try {
            productService.delete(Integer.parseInt(id));
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "There is no product with id " + id + "\n");
        }
        return "removing was completed successfully";
    }

    @PostMapping("/product/create")
    public Product create(@RequestBody Product product) {
        return productService.create(product);
    }
}