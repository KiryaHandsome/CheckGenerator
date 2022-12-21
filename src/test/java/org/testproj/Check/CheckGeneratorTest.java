package org.testproj;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
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
    public void parseCmdArgumentsTest() {
        String[] args = new String[]{"12-34", "1-1", "2-3", "4-5", "card-12"};
        Map<Integer, Integer> map =  checkGenerator.parseArguments(args);
        Map<Integer, Integer> expected = new HashMap<>();
        expected.put(12, 34);
        expected.put(1, 1);
        expected.put(2, 3);
        expected.put(4, 5);
        expected.put(0, 12);
        for(Map.Entry<Integer, Integer> pair : map.entrySet()) {
            int key = pair.getKey();
            assertTrue(expected.containsKey(key));
            int value1 = pair.getValue();
            int value2 = expected.get(key);
            assertEquals(value1, value2);
        }
    }

    @Test
    public void parseCmdArgumentsWithRepeatingKeysTest() {
        String[] args = new String[]{"12-34", "1-1", "2-3", "4-5", "4-5","card-12"};
        Map<Integer, Integer> map =  checkGenerator.parseArguments(args);
        Map<Integer, Integer> expected = new HashMap<>();
        expected.put(12, 34);
        expected.put(1, 1);
        expected.put(2, 3);
        expected.put(4, 10);
        expected.put(0, 12);
        for(Map.Entry<Integer, Integer> pair : map.entrySet()) {
            int key = pair.getKey();
            assertTrue(expected.containsKey(key));
            int value1 = pair.getValue();
            int value2 = expected.get(key);
            assertEquals(value1, value2);
        }
    }

    @Test
    public void getProductsFromDbTest() {
        assertNotNull(discountCardService);
        assertNotNull(productService);
        Map<Integer, Integer> map = new HashMap<>();
        map.put(12, 34);
        map.put(1, 1);
        map.put(2, 3);
        map.put(4, 5);
        map.put(0, 12);
        Map<Product, Integer> expected = new HashMap<>();
        for(Map.Entry<Integer, Integer> pair : map.entrySet()) {
            if(pair.getKey() != 0) {
                expected.put(productService.find(pair.getKey()), pair.getValue());
            } else {
                discountCard = discountCardService.find(pair.getValue());
            }
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
    public void getProductsFromDbWithNotOneDiscountCardTest() {
        assertNotNull(discountCardService);
        assertNotNull(productService);
        Map<Integer, Integer> map = new HashMap<>();
        map.put(12, 34);
        map.put(1, 1);
        map.put(2, 3);
        map.put(4, 5);
        map.put(0, 12);
        map.put(0, 16);
        Map<Product, Integer> expected = new HashMap<>();
        for(Map.Entry<Integer, Integer> pair : map.entrySet()) {
            if(pair.getKey() != 0) {
                expected.put(productService.find(pair.getKey()), pair.getValue());
            } else {
                discountCard = discountCardService.find(pair.getValue());
            }
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
}
