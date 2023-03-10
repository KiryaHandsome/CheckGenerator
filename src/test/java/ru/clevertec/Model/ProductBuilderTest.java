package ru.clevertec.Model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ProductBuilderTest {

    @Test
    public void buildObjectTest() {
        int id = 2;
        String name = "Some food";
        double price = 889.12;
        boolean isPromotional = false;
        Product expectedProduct = new Product(id, name, price, isPromotional);
        Product actualProduct = new Product().getBuilder()
                .setId(id)
                .setName(name)
                .setPrice(price)
                .setPromotional(isPromotional)
                .build();
        assertEquals(expectedProduct, actualProduct);
    }

    @Test
    public void buildObjectWithNullNameTest() {
        int id = 14;
        String name = null;
        double price = 19123.11;
        boolean isPromotional = false;
        Product expectedProduct = new Product(id, name, price, isPromotional);
        Product actualProduct = new Product().getBuilder()
                .setId(id)
                .setPrice(price)
                .setPromotional(isPromotional)
                .build();
        assertEquals(expectedProduct, actualProduct);
    }

    @Test
    public void buildObjectWithNullFieldsTest() {
        Product expectedProduct = new Product();
        Product actualProduct = new Product().getBuilder()
                .build();
        assertEquals(expectedProduct, actualProduct);
    }
}
