package org.testproj.Check;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.testproj.Exceptions.DiscountCardAlreadyPresentedException;
import org.testproj.Models.DiscountCard;
import org.testproj.Models.Product;
import org.testproj.Services.Implementations.DiscountCardService;
import org.testproj.Services.Implementations.ProductService;

import java.security.InvalidParameterException;
import java.util.HashMap;
import java.util.Map;

@Component
public class CheckGenerator {
    @Autowired
    private ProductService productService;
    @Autowired
    private DiscountCardService discountCardService;

    private String check;

    //private Map<Integer, Integer> map = new HashMap<>();

    public Map<Integer, Integer> parseArguments(String... args) {
        Map<Integer, Integer> map = new HashMap<>();
        for (String arg : args) {
            String[] values = arg.split("-");
            if (values.length != 2) {
                throw new InvalidParameterException("Invalid argument in command line.");
            }
            if (values[0].equals("card")) {
                int discountCardId = Integer.parseInt(values[1]);
                map.put(0, discountCardId);
            } else {
                int productId = Integer.parseInt(values[0]);
                int quantity = Integer.parseInt(values[1]);
                if (productId == 0 || quantity == 0) {
                    throw new InvalidParameterException("Id and quantity and must be greater than 0.");
                }
                map.put(productId, quantity);
            }
        }
        return map;
    }

    //TODO: implement
    public Map<Product, Integer> getProductsFromDb(Map<Integer, Integer> info) {
        boolean hasDiscountCard = false;
        Map<Product, Integer> map = new HashMap<>();
        try {
            for (Map.Entry<Integer, Integer> pair : info.entrySet()) {

                int id = pair.getKey();
                int qty = pair.getValue();
                if (id == 0) {    //is discount card
                    DiscountCard card = discountCardService.find(pair.getValue());
                    System.out.println(card);
                    if (hasDiscountCard) {
                        throw new DiscountCardAlreadyPresentedException(
                                "There are few discount cards in arguments");
                    }
                    hasDiscountCard = true;
                    //
                    // do something with discount card
                    //
                } else {
                    Product product = productService.find(id);
                    System.out.println(product);
                }
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            //ex.printStackTrace();
        }
        return map;
    }

    //TODO: implement
    public String generateCheck(Map<Product, Integer> info) {
        for (Map.Entry<Product, Integer> pair : info.entrySet()) {
            //shape the check
        }
        return "";
    }
}
