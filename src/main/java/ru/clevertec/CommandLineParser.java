package ru.clevertec;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import ru.clevertec.Model.Check;
import ru.clevertec.Check.CheckGenerator;

import java.security.InvalidParameterException;
import java.util.HashMap;
import java.util.Map;

@Component
public class CommandLineParser implements CommandLineRunner {

    private final CheckGenerator checkGenerator;

    @Autowired
    public CommandLineParser(CheckGenerator checkGenerator) {
        this.checkGenerator = checkGenerator;
    }

    @Override
    public void run(String... args) {
        if (args.length == 0) return;
        try {
            Map<Integer, Integer> map = parseArguments(args);
            Check check = checkGenerator.generateCheck(map);
            String checkContent = check.toString();
            System.out.println(checkContent);
            checkGenerator.saveCheckToFile(checkContent);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    /**
     * if value of returned map equals to 0, key - discount card id
     * Arguments should be given in format 'id-qty ... card-id'
     *
     * @return map: key - id, value - quantity
     **/
    public static Map<Integer, Integer> parseArguments(String... args) {
        Map<Integer, Integer> map = new HashMap<>();
        for (String arg : args) {
            String[] values = arg.split("-");
            if (values.length != 2) {
                throw new InvalidParameterException("Invalid argument in command line.");
            }
            if ("card".equalsIgnoreCase(values[0])) {
                Integer cardId = Integer.parseInt(values[1]);
                map.put(cardId, 0);
            } else {
                int productId = Integer.parseInt(values[0]);
                int quantity = Integer.parseInt(values[1]);
                if (productId <= 0 || quantity <= 0) {
                    throw new InvalidParameterException("Id and quantity must be greater than 0.");
                }
                int previousQuantity = map.getOrDefault(productId, 0);
                map.put(productId, previousQuantity + quantity);  //case when map has this product already
            }
        }
        return map;
    }
}
