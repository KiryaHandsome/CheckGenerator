package org.testproj.Dao;

import org.testproj.Entities.DiscountCard;

import java.util.ArrayList;
import java.util.List;

//TODO: implements all methods for array and after for db
public class DiscountCardDao implements Dao<DiscountCard> {
    private List<DiscountCard> discountCards = new ArrayList<>();

    @Override
    public DiscountCard get(int id) {
        return discountCards.stream()
                .filter(p -> p.getId() == id)
                .findFirst()
                .orElse(null);
    }

    @Override
    public void save(DiscountCard obj) {
        discountCards.add(obj);
    }

    @Override
    public void update(DiscountCard obj, DiscountCard other) {

    }

    @Override
    public void delete(DiscountCard discountCard) {

    }
}
