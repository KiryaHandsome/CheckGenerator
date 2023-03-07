package ru.clevertec.Models;

import jakarta.persistence.*;
import org.hibernate.annotations.Proxy;

import java.util.Objects;


@Proxy(lazy = false)   //solution for LazyInitializationException
@Entity
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;
    @Column(name = "name")
    private String name;
    @Column(name = "price")
    private double price;
    @Column(name = "is_promotional")
    private boolean isPromotional;

    public Product() {
    }

    public Product(long id, String name, double price, boolean isPromotional) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.isPromotional = isPromotional;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public boolean isPromotional() {
        return isPromotional;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setPromotional(boolean promotional) {
        isPromotional = promotional;
    }

    public ProductBuilder getBuilder() {
        return new ProductBuilder();
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", isPromotional=" + isPromotional +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Product product)) return false;
        return id == product.id
                && Double.compare(product.price, price) == 0
                && isPromotional == product.isPromotional
                && Objects.equals(name, product.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, price, isPromotional);
    }

    public static class ProductBuilder {
        private long id;
        private String name;
        private double price;
        private boolean isPromotional;

        public ProductBuilder() {
        }

        public ProductBuilder(Product product) {
            this.id = product.id;
            this.name = String.valueOf(product.name);
            this.price = product.price;
            this.isPromotional = product.isPromotional;
        }

        public ProductBuilder setId(long id) {
            this.id = id;
            return this;
        }

        public ProductBuilder setName(String name) {
            this.name = name;
            return this;
        }

        public ProductBuilder setPromotional(boolean promotional) {
            isPromotional = promotional;
            return this;
        }

        public ProductBuilder setPrice(double price) {
            this.price = price;
            return this;
        }

        public Product build() {
            return new Product(id, name, price, isPromotional);
        }
    }
}
