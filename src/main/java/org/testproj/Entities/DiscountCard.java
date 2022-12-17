package org.testproj.Entities;

public class DiscountCard {
    private long id;
    private float discount;

    public DiscountCard(long id, float discount) {
        this.id = id;
        this.discount = discount;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public float getDiscount() {
        return discount;
    }

    public void setDiscount(float discount) {
        this.discount = discount;
    }
}
