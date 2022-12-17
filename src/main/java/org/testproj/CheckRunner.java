package org.testproj;

import org.testproj.Dao.ProductDao;
import org.testproj.Entities.Product;

import java.util.List;

public class CheckRunner {
    public static String[] names = new String[]{"Milk", "Beans", "Sausages",
            "Serials", "Beef", "Rolton", "Lay's", "M&M's", "Dumplings", "Ham"};
    public static double[] prices = new double[]{18.77, 15.19, 99.99, 66.13, 12.11,
            88.11, 78.12, 456.90, 115.88, 44.65};
    public static double[] weights = new double[]{13.662, 15.112, 100.111, 6123.1, 89.3,
            8716.11, 781.11, 5.6, 888.1123, 1239.123};

    public static void main(String[] args) {
        ProductDao dao = new ProductDao();

        for (int i = 0; i < names.length; i++) {
            dao.save(new Product().getBuilder()
                    .setId(i + 1)
                    .setName(names[i])
                    .setPrice(prices[i])
                    .setWeight(weights[i])
                    .build());
        }
        List<Product> products = dao.getAll();
        for (Product p : products) {
            System.out.println(p);
        }
        System.out.println("------------------------------------");
        Product pr = new Product().getBuilder()
                .setId(199)
                .setName("Perfume")
                .setPrice(123)
                .setPromotional(true)
                .setWeight(9999)
                .build();
        dao.update(dao.get(1), pr);
        for (Product p : products) {
            System.out.println(p);
        }
        System.out.println("------------------------------------");
        dao.delete(dao.get(2));
        for (Product p : products) {
            System.out.println(p);
        }
        //System.out.println("Hello world!");
    }
}