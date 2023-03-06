package ru.clevertec.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.clevertec.Models.Product;
import ru.clevertec.Services.Implementations.ProductService;

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

    @DeleteMapping("/product/delete/{id}")
    public ResponseEntity<String> removeProductById(@PathVariable String id) {
        productService.delete(Integer.parseInt(id));
        return ResponseEntity.status(HttpStatus.OK).body("Product with id " + id + "" +
                " either was deleted or wasn't in db");
    }

    @PostMapping("/product/create")
    public Product create(@RequestBody Product product) {
        return productService.create(product);
    }

    @PutMapping("/product/update/{id}")
    public Product updateDiscountCard(@PathVariable String id,
                                           @RequestBody Product product) {
        return productService.update(Integer.parseInt(id), product);
    }
}