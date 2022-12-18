import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.testproj.Dao.ProductDao;
import org.testproj.Entities.Product;

import java.util.List;


public class ProductDaoTest {
    public String[] names = new String[]{"Milk", "Beans", "Sausages",
            "Serials", "Beef", "Rolton", "Lay's", "M&M's", "Dumplings", "Ham"};
    public double[] prices = new double[]{18.77, 15.19, 99.99, 66.13, 12.11,
            88.11, 78.12, 456.90, 115.88, 44.65};
    public double[] weights = new double[]{13.662, 15.112, 100.111, 6123.1, 89.3,
            8716.11, 781.11, 5.6, 888.1123, 1239.123};
    //List<Product> products = new ArrayList<>();

    @Test
    public void getProduct() {
        ProductDao dao = new ProductDao();
        for (int i = 0; i < names.length; i++) {
            dao.save(new Product().getBuilder()
                    .setId(i + 1)
                    .setName(names[i])
                    .setPrice(prices[i])
                    .setWeight(weights[i])
                    .build());
        }
        Product product = new Product().getBuilder()
                .setId(4)
                .setName("Serials")
                .setPrice(66.13)
                .setWeight(6123.1)
                .build();
        //List<Product> products = dao.getAll();
        //Assertions.assertEquals(products.get(3), product);
    }
}
