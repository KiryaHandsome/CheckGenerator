package ru.clevertec;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.clevertec.Models.Product;
import ru.clevertec.Services.Implementations.ProductService;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class BootTest {
    @Autowired
    private ProductService service;

    @Test
    public void testLoggable() {

        service.find(12);
    }
}
