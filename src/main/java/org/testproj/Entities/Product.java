package org.testproj.Entities;

import java.util.Date;

public class Product {
    private int id;
    private String name;
    private double price;
    private boolean isPromotional;

    //TODO: expiration date
    //TODO:
    private double weight;

    public Product() {}

    public Product(int id, String name, double price, boolean isPromotional, double weight) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.isPromotional = isPromotional;
        this.weight = weight;
    }

    public double getWeight() {
        return weight;
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

    public void setFields(Product other) {
        this.id = other.id;
        this.name = other.name;
        this.price = other.price;
        this.isPromotional = other.isPromotional;
        this.weight = other.weight;
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
                ", weight=" + weight +
                '}';
    }

    public static class ProductBuilder {
        private int id;
        private String name;
        private double price;
        private boolean isPromotional;
        private double weight;

        public ProductBuilder setId(int id) {
            this.id = id;
            return this;
        }

        public ProductBuilder setName(String name) {
            this.name = name;
            return this;
        }

        public ProductBuilder setWeight(double weight) {
            this.weight = weight;
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
            return new Product(id, name, price, isPromotional,
                    weight);
        }
    }
}
