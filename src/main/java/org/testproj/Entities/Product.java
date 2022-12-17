package org.testproj.Entities;

import java.util.Date;

public class Product {
    private long id;
    private String name;
    private double price;

    //TODO: add weight and expiration date to database and builder
    private double weight;
    private Date expirationDate;

    public Product() {}

    public Product(long id, String name, double price, double weight, Date expirationDate) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.weight = weight;
        this.expirationDate = expirationDate;
    }

    public double getWeight() {
        return weight;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public long getId() {
        return id;
    }
    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
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
                '}';
    }

    public static class ProductBuilder {
        private long id;
        private String name;
        private double price;
        private double weight;
        private Date expirationDate;

        public ProductBuilder setId(long id) {
            this.id = id;
            return this;
        }

        public ProductBuilder setName(String name) {
            this.name = name;
            return this;
        }

        public void setWeight(double weight) {
            this.weight = weight;
        }

        public void setExpirationDate(Date expirationDate) {
            this.expirationDate = expirationDate;
        }

        public ProductBuilder setPrice(double price) {
            this.price = price;
            return this;
        }

        public Product build() {
            return new Product(id, name, price,
                    weight, expirationDate);
        }
    }
}
