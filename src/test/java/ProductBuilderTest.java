import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.testproj.Models.Product;


public class ProductBuilderTest {

    @Test
    public void buildObjectTest() {
        int id = 2;
        String name = "Some food";
        double price = 889.12;
        boolean isPromotional = false;
        Product product1 = new Product(id, name, price, isPromotional);
        Product product2 = new Product().getBuilder()
                .setId(id)
                .setName(name)
                .setPrice(price)
                .setPromotional(isPromotional)
                .build();
        Assertions.assertEquals(product1, product2);
    }

    @Test
    public void buildObjectWithNullNameTest() {
        int id = 14;
        String name = null;
        double price = 19123.11;
        boolean isPromotional = false;
        Product product1 = new Product(id, name, price, isPromotional);
        Product product2 = new Product().getBuilder()
                .setId(id)
                .setPrice(price)
                .setPromotional(isPromotional)
                .build();
        Assertions.assertEquals(product1, product2);
    }
}
