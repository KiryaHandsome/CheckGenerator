package org.testproj;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.testproj.Check.CheckGenerator;
import org.testproj.Models.Product;

import java.util.Map;

@Component
public class CommandLineParser implements CommandLineRunner {
    //private static Logger LOG = LoggerFactory.getLogger(CheckRunner.class);
    @Autowired
    private CheckGenerator checkGenerator;

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Start of cmd args parsing...");
        try {
            Map<Integer, Integer> map = checkGenerator.parseArguments(args);
            Map<Product, Integer> info = checkGenerator.getProductsFromDb(map);
            String check = checkGenerator.generateCheck(info);
            System.out.println(check);
        } catch (Exception ex) {
            System.out.println(ex.getMessage()); // use logger?
        }

    }


}
