package org.testproj.Models;

import jakarta.persistence.*;
import org.hibernate.annotations.Proxy;

import java.util.Objects;

@Proxy(lazy = false)   //solution for LazyInitializationException
@Entity
@Table(name = "discount_card")
public class DiscountCard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;
    @Column(name = "discount")
    private float discount;

    public DiscountCard() {
    }

    public DiscountCard(long id, float discount) {
        this.id = id;
        this.discount = discount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DiscountCard that)) return false;
        return id == that.id && Float.compare(that.discount, discount) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, discount);
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
