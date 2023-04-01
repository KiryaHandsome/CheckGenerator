package ru.clevertec.Cache.Proxy;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.clevertec.Model.Product;
import ru.clevertec.Service.Implementation.ProductService;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class ServiceProxyTest {
    @Autowired
    private ProductService productService;

    @Test
    void checkCreateShouldReturnCreatedValue() {
        Product product = Product.builder()
                .id(123)
                .price(123.123)
                .name("name")
                .isPromotional(false)
                .build();
        Product actualProduct = productService.create(product);
        Product productFromCache = productService.find(123);
        assertThat(actualProduct).isEqualTo(productFromCache);
    }

    @Test
    void checkFindShouldReturnSameValue() {
        Product product = productService.find(123);
        Product productFromCache = productService.find(123);
        assertThat(product).isEqualTo(productFromCache);
    }

    @ParameterizedTest
    @ValueSource(ints = {555, 666, 777})
    void checkUpdate(int id) {
        Product product = productService.find(id);
        product.setName("NEW_UNUSUAL_NAME");
        Product productFromUpdate = productService.update(id, product);
        assertThat(productFromUpdate).isEqualTo(product);
        assertThat(productFromUpdate).isEqualTo(productService.find(id));
    }

    @ParameterizedTest
    @ValueSource(ints = {123, 999, 2, 4})
    void checkDelete(int id) {
        productService.delete(id);
    }
}