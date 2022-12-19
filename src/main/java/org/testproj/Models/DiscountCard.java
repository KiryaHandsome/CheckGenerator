package org.testproj.Models;

import jakarta.persistence.*;
import org.hibernate.annotations.Proxy;

@Proxy(lazy=false)   //solution for LazyInitializationException
@Entity
@Table(name = "discount_card")
public class DiscountCard {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private float discount;

    public DiscountCard() { }

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
