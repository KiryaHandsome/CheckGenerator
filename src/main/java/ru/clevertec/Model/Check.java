package ru.clevertec.Model;

import ru.clevertec.Check.CheckSettings;
import ru.clevertec.Check.Builder.CheckStringBuilder;
import ru.clevertec.Exception.DiscountCardAlreadyPresentedException;

import java.util.HashMap;
import java.util.Map;

public class Check {

    private final Map<Product, Integer> products = new HashMap<>();
    private DiscountCard discountCard;

    public void addProduct(Product product, int quantity) {
        products.put(product, quantity);
    }

    public void addDiscountCard(DiscountCard discountCard) throws DiscountCardAlreadyPresentedException {
        if (this.discountCard != null) {
            throw new DiscountCardAlreadyPresentedException(
                    "There are few discount cards in arguments");
        }
        this.discountCard = discountCard;
    }

    @Override
    public String toString() {
        CheckSettings settings = new CheckSettings(products);
        CheckStringBuilder builder = new CheckStringBuilder(settings);
        return builder.buildCheck(products, discountCard);
    }

    public Map<Product, Integer> getProducts() {
        return products;
    }

    public DiscountCard getDiscountCard() {
        return discountCard;
    }
}
