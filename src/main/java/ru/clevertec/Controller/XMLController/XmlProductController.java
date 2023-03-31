package ru.clevertec.Controller.XMLController;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.clevertec.Model.Product;
import ru.clevertec.Service.Implementation.ProductService;

import java.util.List;

@RestController
@RequestMapping(value = "/xml", produces = "application/xml")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class XmlProductController {

    private final ProductService productService;

    @GetMapping("/products")
    public ResponseEntity<List<Product>> loadAll() {
        HttpHeaders headers = new HttpHeaders();
        return new ResponseEntity<>(productService.findAll(), headers,
                HttpStatus.CREATED);
    }

    @GetMapping("/product/{id}")
    public ResponseEntity<Product> productById(@PathVariable String id) {
        HttpHeaders headers = new HttpHeaders();
        return new ResponseEntity<>(productService.find(Integer.parseInt(id)), headers,
                HttpStatus.CREATED);
    }

    @DeleteMapping("/product/delete/{id}")
    public ResponseEntity<String> removeProductById(@PathVariable String id) {
        productService.delete(Integer.parseInt(id));
        return ResponseEntity.status(HttpStatus.OK).body("Product with id " + id + "" +
                " either was deleted or wasn't in db");
    }

    @PostMapping("/product/create")
    public ResponseEntity<Product> create(@RequestBody Product product) {
        HttpHeaders headers = new HttpHeaders();
        return new ResponseEntity<>(productService.create(product), headers,
                HttpStatus.CREATED);
    }

    @PutMapping("/product/update/{id}")
    public ResponseEntity<Product> updateDiscountCard(
            @PathVariable String id,
            @RequestBody Product product) {
        HttpHeaders headers = new HttpHeaders();
        return new ResponseEntity<>(productService.update(Integer.parseInt(id), product), headers,
                HttpStatus.CREATED);
    }
}
