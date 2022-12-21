package org.testproj.Check;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.testproj.Check.CheckGenerator;
import org.testproj.Exceptions.DiscountCardAlreadyPresentedException;
import org.testproj.Models.DiscountCard;
import org.testproj.Models.Product;
import org.testproj.Services.Implementations.DiscountCardService;
import org.testproj.Services.Implementations.ProductService;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;


@SpringBootTest
public class CheckGeneratorTest {
    @Autowired
    private DiscountCardService discountCardService;

    @Autowired
    private ProductService productService;

    @Autowired
    private CheckGenerator checkGenerator;

    private DiscountCard discountCard;

    @Test
    public void parseCmdArgumentsTest()
            throws DiscountCardAlreadyPresentedException {
        String[] args = new String[]{"12-34", "1-1", "2-3", "4-5", "card-12"};
        checkGenerator.clearFields();
        Map<Integer, Integer> map =  checkGenerator.parseArguments(args);
        Map<Integer, Integer> expected = new HashMap<>();
        expected.put(12, 34);
        expected.put(1, 1);
        expected.put(2, 3);
        expected.put(4, 5);
        for(Map.Entry<Integer, Integer> pair : map.entrySet()) {
            int key = pair.getKey();
            assertTrue(expected.containsKey(key));
            int value1 = pair.getValue();
            int value2 = expected.get(key);
            assertEquals(value1, value2);
        }
    }

    @Test
    public void parseCmdArgumentsWithRepeatingKeysTest()
            throws DiscountCardAlreadyPresentedException {
        String[] args = new String[]{"12-34", "1-1", "2-3", "4-5", "4-5","card-12"};
        checkGenerator.clearFields();
        Map<Integer, Integer> map =  checkGenerator.parseArguments(args);
        discountCard = discountCardService.find(12);
        Map<Integer, Integer> expected = new HashMap<>();
        expected.put(12, 34);
        expected.put(1, 1);
        expected.put(2, 3);
        expected.put(4, 10);
        for(Map.Entry<Integer, Integer> pair : map.entrySet()) {
            int key = pair.getKey();
            assertTrue(expected.containsKey(key));
            int value1 = expected.get(key);
            int value2 = pair.getValue();
            assertEquals(value1, value2);
        }
    }

    @Test
    public void parseCmdArgumentsWithFewDiscountCardsTest() throws DiscountCardAlreadyPresentedException {
        String[] args = new String[]{"12-34", "1-1", "2-3", "4-5", "4-5","card-12", "card-58"};
        assertThrows(
                DiscountCardAlreadyPresentedException.class, () -> {checkGenerator.parseArguments(args); }
        );
    }

    @Test
    public void getProductsFromDbTest()
            throws DiscountCardAlreadyPresentedException {
        String[] args = new String[]{"12-34", "1-1", "2-3", "4-5", "4-5","card-12"};
        Map<Integer, Integer> map = checkGenerator.parseArguments(args);
        assertNotNull(discountCardService);
        assertNotNull(productService);
        discountCard = discountCardService.find(12);
        Map<Product, Integer> expected = new HashMap<>();
        for(Map.Entry<Integer, Integer> pair : map.entrySet()) {
            expected.put(productService.find(pair.getKey()), pair.getValue());
        }
        try {
            Map<Product, Integer> result = checkGenerator.getProductsFromDb(map);
            for(Map.Entry<Product, Integer> pair : result.entrySet()) {
                Product key = pair.getKey();
                assertTrue(expected.containsKey(key));
                int value1 = pair.getValue();
                int value2 = expected.get(key);
                assertEquals(value1, value2);
            }
            assertNotNull(discountCard);
        } catch (DiscountCardAlreadyPresentedException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Test
    public void getProductsFromDbWithNotOneDiscountCardTest()
            throws DiscountCardAlreadyPresentedException {
        //assertNotNull(discountCardService);
        //assertNotNull(productService);
        checkGenerator.clearFields();
        String[] args = new String[]{"12-34", "1-1", "2-3", "4-5", "4-5","card-199"};
        Map<Integer, Integer> map = checkGenerator.parseArguments(args);
        Map<Product, Integer> expected = new HashMap<>();
        discountCard = discountCardService.find(199);
        for(Map.Entry<Integer, Integer> pair : map.entrySet()) {
            expected.put(productService.find(pair.getKey()), pair.getValue());
        }
        Map<Product, Integer> result = checkGenerator.getProductsFromDb(map);
        for(Map.Entry<Product, Integer> pair : result.entrySet()) {
            Product key = pair.getKey();
            assertTrue(expected.containsKey(key));
            int value1 = pair.getValue();
            int value2 = expected.get(key);
            assertEquals(value1, value2);
        }
        assertNotNull(discountCard);
        assertEquals(discountCard, checkGenerator.getDiscountCard());
    }
}
