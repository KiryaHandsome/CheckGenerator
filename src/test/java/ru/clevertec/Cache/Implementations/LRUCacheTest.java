package ru.clevertec.Cache.Implementations;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import ru.clevertec.Models.Product;
import ru.clevertec.Cache.CacheManager;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class LRUCacheTest {

    private CacheManager<Product> productCacheManager;
    private int capacity;

    @BeforeEach
    void setUp() {
        capacity = 5;
        productCacheManager = new LRUCache<>(capacity);
    }

    @ParameterizedTest
    @ValueSource(ints = {Integer.MIN_VALUE, -123, -100, -1, 0})
    void checkConstructorShouldThrowIllegalArgumentException(int capacity) {
        assertThrows(IllegalArgumentException.class,
                () -> new LRUCache<>(capacity));
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
        Product actual = productCacheManager.get(id);
        assertThat(actual).isEqualTo(expectedProduct);
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 11, 88, 99, Integer.MAX_VALUE})
    void checkContainsShouldReturnFalse(int id) {
        boolean actual = productCacheManager.contains(id);
        assertThat(actual).isFalse();
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 11, 88, 99, Integer.MAX_VALUE})
    void checkContainsShouldReturnTrue(int id) {
        productCacheManager.put(id, new Product());
        boolean actual = productCacheManager.contains(id);
        assertThat(actual).isTrue();
    }

    @Test
    void checkContainsShouldReturnFalseDueToCapacityOverflow() {
        productCacheManager.put(capacity, new Product());
        for (int i = 0; i < capacity; i++) {
            productCacheManager.put(i, new Product());
        }
        boolean actual = productCacheManager.contains(capacity);
        assertThat(actual).isFalse();
    }

    @Test
    void checkDeleteShouldNotContainsValue() {
        for(int i = 1; i <= capacity; i++) {
            productCacheManager.put(i, new Product(i, "value" + i, 0.87, true));
        }
        productCacheManager.delete(1);
        assertThat(productCacheManager.contains(1)).isFalse();
    }

    @Test
    void checkDeleteShouldDoNothing() {
        for(int i = 1; i <= capacity; i++) {
            productCacheManager.put(i, new Product(i, "value" + i, 0.87, true));
        }
        productCacheManager.delete(Integer.MAX_VALUE);
        assertThat(productCacheManager.contains(1)).isTrue();
    }
}