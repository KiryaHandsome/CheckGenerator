package ru.clevertec.Check;

import ru.clevertec.Model.DiscountCard;
import ru.clevertec.Model.Product;

import java.util.Map;

/**
 * CheckSettings - class which contains
 */
public class CheckSettings {

    private final Map<Product, Integer> products;
    private int maxNameLength;
    private int maxPriceLength;
    private int maxQuantityLength;

    private static final int PROMOTIONAL_LABEL_WIDTH = 5;

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
        int minNameLength = 11;
        int descriptionCorrector = 1;
        return Math.max(maxNameLength, minNameLength) + descriptionCorrector;
    }

    public int getPriceWidth() {
        int minPriceLength = 5;
        int priceWidthCorrector = 5;
        return Math.max(maxPriceLength, minPriceLength) + priceWidthCorrector;
    }

    public int getQuantityWidth() {
        return Math.max(maxQuantityLength, 3) + 1;
    }

    public int getTotalWidth() {
        int widthCorrector = 3;
        return getPriceWidth() + widthCorrector;
    }

    public int getPromotionalWidth() {
        int promotionalLabelWidth = 5;
        return promotionalLabelWidth;
    }

    public int getCheckWidth() {
        int checkWidthCorrector = 2;
        return getQuantityWidth() +
                getDescriptionWidth() +
                getPriceWidth() +
                getTotalWidth() +
                getPromotionalWidth() + checkWidthCorrector;
    }

    public int getDateAndTimeWidth() {
        int dateAndTimeLength =  13;
        return getCheckWidth() - dateAndTimeLength;
    }

}
