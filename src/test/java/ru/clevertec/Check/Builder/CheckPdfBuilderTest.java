package ru.clevertec.Check.Builder;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.clevertec.Model.Product;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class CheckPdfBuilderTest {

    private CheckPdfBuilder checkPdfBuilder;

    @BeforeEach
    void setUp() {
        checkPdfBuilder = new CheckPdfBuilder();
    }

    @Test
    void test() {
        Map<Product, Integer> products = List.of(
                new Product(1, "First", 123.12, true),
                new Product(2, "Second", 99, false),
                new Product(3, "Third", 1881, true),
                new Product(4, "Fourth", 1234.13, false),
                new Product(5, "Fiveth", 671.11, true)
        ).stream()
                .collect(Collectors.toMap(product -> product, a -> 1));

        checkPdfBuilder.buildCheck(products, null);
    }

}