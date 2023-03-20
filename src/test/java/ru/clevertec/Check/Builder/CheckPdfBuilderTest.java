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
                        new Product(45441, "12",
                                123.12, true),
                        new Product(2, "Second", 99, false),
                        new Product(3, "Third", 1881, true),
                        new Product(4, "Fourth", 1234.13, false),
                        new Product(5, "12",
                                123.12, true),
                        new Product(6, "Second", 99, false),
                        new Product(13, "Third", 1881, true),
                        new Product(14, "Fourth", 1234.13, false),
                        new Product(1231, "12",
                                123.12, true),
                        new Product(1112, "Second", 99, false),
                        new Product(113, "Third", 1881, true),
                        new Product(13334, "Fourth", 1234.13, false),
                        new Product(151, "12",
                                123.12, true),
                        new Product(15152, "Second", 99, false),
                        new Product(223, "ThirdThirdThirdThirdThirdThirdThi", 1881, true),
                        new Product(444, "Fourth", 1234.13, false),
                        new Product(1, "12",
                                123.12, true),
                        new Product(432, "Second", 99, false),
                        new Product(4253, "Third", 1881, true),
                        new Product(2364, "Fourth", 1234.13, false),
                        new Product(234623461, "12",
                                123.12, true),
                        new Product(23462, "Second", 99, false),
                        new Product(24363, "Third", 1881, true),
                        new Product(2644, "Fourth", 1234.13, false),
                        new Product(3461, "12",
                                123.12, true),
                        new Product(24623462, "Second", 99, false),
                        new Product(2362343, "Third", 1881, true),
                        new Product(2224, "Fourth", 1234.13, false),

                        new Product(222225, "Fiveth", 671.11, true)
                ).stream()
                .collect(Collectors.toMap(product -> product, a -> 10));

        checkPdfBuilder.buildCheck(products, null);
    }

}