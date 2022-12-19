package org.testproj;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.testproj.Check.CheckParser;
import org.testproj.Models.Product;
import org.testproj.Services.Implementations.DiscountCardService;
import org.testproj.Services.Implementations.ProductService;

import java.util.Map;

@Component
public class CommandLineParser implements CommandLineRunner {
    //private static Logger LOG = LoggerFactory.getLogger(CheckRunner.class);
    @Autowired
    private ProductService productService;
    @Autowired
    private DiscountCardService discountCardService;

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Start of cmd args parsing...");
        Map<Integer, Integer> map = CheckParser.parseCmdArgs(args);
        for(Map.Entry<Integer, Integer> pair : map.entrySet()) {
            if(pair.getKey() == 0) {

            } else {

            }
        }
        Product p = productService.find(1);
        System.out.println(p);
    }


}
