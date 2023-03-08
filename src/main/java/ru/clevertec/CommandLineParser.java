package ru.clevertec;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import ru.clevertec.Check.CheckGenerator;
import ru.clevertec.Model.Product;

import java.util.Map;

@Component
public class CommandLineParser implements CommandLineRunner {
    //private static Logger LOG = LoggerFactory.getLogger(CheckRunner.class);
    @Autowired
    private CheckGenerator checkGenerator;

    @Override
    public void run(String... args) {
        if (args.length == 0) return;
        try {
            Map<Integer, Integer> map = checkGenerator.parseArguments(args);
            Map<Product, Integer> info = checkGenerator.getProductsFromDb(map);
            String check = checkGenerator.generateCheck(info);
            System.out.println(check);
            checkGenerator.saveCheckToFile("check.txt");
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

    }


}
