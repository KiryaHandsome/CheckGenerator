package ru.clevertec.Cache.Implementation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import ru.clevertec.Model.Product;
import ru.clevertec.Cache.Cache;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class LRUCacheTest {

    private Cache productCacheManager;
    private int capacity;

    @BeforeEach
    void setUp() {
        capacity = 5;
        productCacheManager = new LRUCache(capacity);
    }

    @ParameterizedTest
    @ValueSource(ints = {Integer.MIN_VALUE, -123, -100, -1, 0})
    void checkConstructorShouldThrowIllegalArgumentException(int capacity) {
        assertThrows(IllegalArgumentException.class,
                () -> new LRUCache(capacity));
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2, -123, 0, 88, 99, Integer.MAX_VALUE, Integer.MIN_VALUE})
    void checkGetShouldReturnNull(int id) {
        Object actual = productCacheManager.get(id);
        assertThat(actual).isNull();
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 11, 88, 99, Integer.MAX_VALUE})
    void checkGetShouldReturnProduct(int id) {
        Product expectedProduct = new Product().getBuilder()
                .setPrice(88)
                .setPromotional(true)
                .setName("Name")
                .build();
        productCacheManager.put(id, expectedProduct);
        Product actual = (Product) productCacheManager.get(id);
        assertThat(actual).isEqualTo(expectedProduct);
    }

    @Test
    void checkDeleteShouldNotContainsValue() {
        for(int i = 1; i <= capacity; i++) {
            productCacheManager.put(i, new Product(i, "value" + i, 0.87, true));
        }
        productCacheManager.delete(1);
        assertThat(productCacheManager.get(1)).isNull();
    }

    @Test
    void checkDeleteShouldDoNothing() {
        for(int i = 1; i <= capacity; i++) {
            productCacheManager.put(i, new Product(i, "value" + i, 0.87, true));
        }
        productCacheManager.delete(Integer.MAX_VALUE);
        assertThat(productCacheManager.get(1)).isNotNull();
    }
}