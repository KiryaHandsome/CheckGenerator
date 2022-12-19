package org.testproj.Check;

import org.testproj.Exceptions.DiscountCardAlreadyPresentedException;

import java.util.HashMap;
import java.util.Map;

// TODO: change all injections to Autowired annotations
// TODO: maybe add some 'broker' between dao and checkGenerator
public class CheckGenerator {
    private StringBuilder checkText;
    private Map<Integer, Integer> productsInfo = new HashMap<>(); // product id - quantity
    private Integer discountCardId;

    public CheckGenerator() { }

    public void setDiscountCardId(Integer discountCardId)
            throws DiscountCardAlreadyPresentedException {
        if (this.discountCardId == null) {
            this.discountCardId = discountCardId;
        } else {
            throw new DiscountCardAlreadyPresentedException(discountCardId.toString());
        }
    }

    public void addProduct(int productId, int quantity) {
        productsInfo.put(productId, quantity);
    }

    // TODO: implement method
    public String generateCheck() {
        return "";
    }
}
