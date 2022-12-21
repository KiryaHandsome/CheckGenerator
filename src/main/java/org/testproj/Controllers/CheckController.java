package org.testproj.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.testproj.Check.CheckGenerator;
import org.testproj.Exceptions.DiscountCardAlreadyPresentedException;
import org.testproj.Models.Product;
import org.testproj.Services.Implementations.ProductService;

import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/check")
public class CheckController {
    final private CheckGenerator checkGenerator;
    final private ProductService productService;

    @Autowired
    public CheckController(ProductService productService, CheckGenerator checkGenerator) {
        this.productService = productService;
        this.checkGenerator = checkGenerator;
    }

    @GetMapping
    public String getCheck(@RequestParam Map<String, String> allParams)
            throws DiscountCardAlreadyPresentedException {
        String[] args = allParams.entrySet()
                .stream()
                .map(e -> e.getKey() + "-" + e.getValue())
                .toArray(String[]::new);
        Map<Integer, Integer> map = checkGenerator.parseArguments(args);
        try {
            Map<Product, Integer> info = checkGenerator.getProductsFromDb(map);
            checkGenerator.generateCheck(info);
            checkGenerator.saveCheckToFile("check.txt");
        } catch (DiscountCardAlreadyPresentedException | IOException e) {
            return e.getMessage();
        }
        return checkGenerator.getCheck();
    }
}
