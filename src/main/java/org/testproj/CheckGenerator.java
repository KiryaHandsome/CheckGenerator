package org.testproj;

import org.testproj.Entities.DiscountCard;
import org.testproj.Entities.Product;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class CheckGenerator {
    private StringBuilder checkText;
    private Map<Product, Integer> productsInfo = new HashMap<>(); // product - quantity
    private DiscountCard discountCard;

    public CheckGenerator() {

    }

    public CheckGenerator(DiscountCard discountCard) {
        this.discountCard = discountCard;
    }

    public void addProduct(Product product, int quantity) {
        productsInfo.put(product, quantity);
    }

    // TODO: implement method
    public String generateCheck() {
        return "";
    }

    // TODO: decide : keep or not
    public String getCheck() {
        return checkText.toString();
    }
}
