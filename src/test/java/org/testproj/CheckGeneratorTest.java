package org.testproj;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.testproj.Check.CheckGenerator;
import org.testproj.Services.Implementations.DiscountCardService;
import org.testproj.Services.Implementations.ProductService;

import java.util.HashMap;
import java.util.Map;


@SpringBootTest
public class CheckGeneratorTest {
    @Autowired
    private DiscountCardService discountCardService;

    @Autowired
    private ProductService productService;

    @Autowired
    private CheckGenerator checkGenerator;

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
    public void getProductsFromDbTest() {
        
    }
}
