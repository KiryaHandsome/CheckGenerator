package org.testproj.Controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.testproj.Models.Product;
import org.testproj.Services.ShopService;

import java.util.List;

@RestController
@RequestMapping("/")
public class ProductController {

    private final Logger LOGGER = LoggerFactory.getLogger(ProductController.class);
    private final ShopService productService;
    //private final ProductService userConverter;

    @Autowired
    public ProductController(@Qualifier("productService") ShopService productService) {
        this.productService = productService;
        //this.userConverter = userConverter;
    }

    @RequestMapping
    public ResponseEntity<List<Product>> loadAll() {
        LOGGER.info("start loadAll users");
        return null;
    }

    @RequestMapping("/{id}")
    public ResponseEntity<Product> loadOne(@PathVariable int id) {
        LOGGER.info("start loadOne user by id: ", id);
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Product> create(@RequestBody Product req) {
        LOGGER.info("start creating user: ", req);
        return null;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Product> update(@PathVariable int id, @RequestBody Product userDTO) {
        LOGGER.info("start update user: ", userDTO);
        return null;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity delete(@PathVariable int id) {
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }
}