package ru.clevertec.Check.Builder.Api;

import ru.clevertec.Model.DiscountCard;
import ru.clevertec.Model.Product;

import java.util.Map;

public interface CheckBuilder {
    Object buildCheck(Map<Product, Integer> products, DiscountCard discountCard);
}
