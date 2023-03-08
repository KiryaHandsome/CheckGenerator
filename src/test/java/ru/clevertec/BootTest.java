package ru.clevertec;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.clevertec.Service.Implementation.ProductService;

@SpringBootTest
public class BootTest {
    @Autowired
    private ProductService service;

    @Test
    public void testLoggable() {
        System.out.println(service.find(12));
    }
}
