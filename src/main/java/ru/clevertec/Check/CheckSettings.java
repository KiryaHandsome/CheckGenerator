package ru.clevertec.Check;

import ru.clevertec.Model.DiscountCard;
import ru.clevertec.Model.Product;

import java.util.Map;

/**
 * CheckSettings - class which contains
 **/
public class CheckSettings {
    private Map<Product, Integer> products;
    private int maxNameLength;
    private int maxPriceLength;
    private int maxQuantityLength;

    public CheckSettings(Map<Product, Integer> products) {
        this.products = products;
        initializeMaxLengths();
    }

    private void initializeMaxLengths() {
        products.forEach((pr, qty) -> {
            maxQuantityLength = Math.max(maxQuantityLength, CheckUtil.numberOfDigits(qty));
            maxNameLength = Math.max(maxNameLength, pr.getName().length());
            maxPriceLength = Math.max(maxPriceLength, CheckUtil.numberOfDigits((int) pr.getPrice()));
        });
    }

    public int getDescriptionWidth() {
        return Math.max(maxNameLength, 11) + 1;
    }

    public int getPriceWidth() {
        return Math.max(maxPriceLength, 5) + 5;
    }

    public int getQuantityWidth() {
        return Math.max(maxQuantityLength, 3) + 1;
    }

    public int getTotalWidth() {
        return getPriceWidth() + 3;
    }

    public int getPromotionalWidth() {
        return 5;
    }

    public int getCheckWidth() {
        return getQuantityWidth() +
                getDescriptionWidth() +
                getPriceWidth() +
                getTotalWidth() +
                getPromotionalWidth() + 2;
    }

    public int getDateAndTimeWidth() {
        return getCheckWidth() - 13;
    }

}
