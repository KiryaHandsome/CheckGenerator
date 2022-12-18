package org.testproj;

import org.testproj.Dao.ProductDao;
import org.testproj.Entities.Product;

import java.util.List;
import java.util.Optional;

public class CheckRunner {
    public static String[] names = new String[]{"Milk", "Beans", "Sausages",
            "Serials", "Beef", "Rolton", "Lay's", "M&M's", "Dumplings", "Ham"};
    public static double[] prices = new double[]{18.77, 15.19, 99.99, 66.13, 12.11,
            88.11, 78.12, 456.90, 115.88, 44.65};
    public static double[] weights = new double[]{13.662, 15.112, 100.111, 6123.1, 89.3,
            8716.11, 781.11, 5.6, 888.1123, 1239.123};

    public static void main(String[] args) {
        ProductDao dao = new ProductDao();
        CheckGenerator checkGenerator = new CheckGenerator(dao);

        // TODO: unpack arguments

        try {
            for (String s : args) {
                System.out.println(s);
                String[] productData = s.split("-");
                if (productData[0].equals("card")) { // discount card presentation
                    int discountCardId = Integer.parseInt(productData[1]);
                    checkGenerator.setDiscountCardId(discountCardId);
                } else {
                    int productId = Integer.parseInt(productData[0]);
                    int quantity = Integer.parseInt(productData[1]);
                }
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

    }
}