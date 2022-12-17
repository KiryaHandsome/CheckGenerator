package org.testproj;

import org.testproj.Entities.Product;

public class CheckRunner {
    public static String[] names = new String[]{"Milk", "Beans", "Sausages"};
    public static double[] prices = new double[]{18.77, 15.19, 99.99};

    public static void main(String[] args) {

        Product product = new Product().getBuilder()
                .setId(1)
                .setName("Kirya")
                .setPrice(999.85)
                .build();
        System.out.println(product);
        System.out.println("Hello world!");
    }
}