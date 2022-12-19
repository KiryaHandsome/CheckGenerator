package org.testproj.Check;


import java.security.InvalidParameterException;
import java.util.HashMap;
import java.util.Map;

public class CheckParser {
    public static Map<Integer, Integer> parseCmdArgs(String ...args) {
        Map<Integer, Integer> map = new HashMap<>();
        for(String arg : args) {
            String[] values = arg.split("-");
            if(values.length != 2) {
                throw new InvalidParameterException("Invalid argument in command line.");
            }
            if(values[0].equals("card")) {
                int discountCardId = Integer.parseInt(values[1]);
                map.put(0, discountCardId);
            } else {
                int productId = Integer.parseInt(values[0]);
                int quantity = Integer.parseInt(values[1]);
                if(productId == 0 || quantity == 0) {
                    throw new InvalidParameterException("Id and quantity and must be greater than 0.");
                }
                map.put(productId, quantity);
            }
        }
        return map;
    }
}
