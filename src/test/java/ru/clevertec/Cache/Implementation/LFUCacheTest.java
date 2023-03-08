package ru.clevertec.Cache.Implementation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import ru.clevertec.Cache.Cache;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class LFUCacheTest {
    private Cache<String> stringCacheManager;
    private int capacity;

    @BeforeEach
    void setUp() {
        capacity = 5;
        stringCacheManager = new LFUCache<>(capacity);
    }

    @ParameterizedTest
    @ValueSource(ints = {Integer.MIN_VALUE, -123, -100, -1, 0})
    void checkConstructorShouldThrowIllegalArgumentException(int capacity) {
        assertThrows(IllegalArgumentException.class,
                () -> new LFUCache<>(capacity));
    }

    @ParameterizedTest
    @ValueSource(ints = {Integer.MAX_VALUE, 123, 100, 1, 1000})
    void checkGetShouldReturnNull(int id) {
        assertNull(stringCacheManager.get(id));
    }

    private static Stream<Arguments> checkGetShouldReturnObject() {
        return Stream.of(
                arguments(1, "Aaaa"),
                arguments(2, "Second"),
                arguments(12, "NULL"),
                arguments(Integer.MAX_VALUE, "MAX_VALUE")
        );
    }

    @ParameterizedTest
    @MethodSource
    void checkGetShouldReturnObject(int id, String value) {
        stringCacheManager.put(id, value);
        assertThat(stringCacheManager.get(id)).isEqualTo(value);
    }

    @ParameterizedTest
    @ValueSource(ints = {10, 20, 100, 1000, Integer.MAX_VALUE})
    void checkGetShouldReturnNullDueToCapacityOverflow(int id) {
        stringCacheManager.put(id, "DesiredString");
        for(int i = 1; i <= capacity; i++) {
            stringCacheManager.put(i, "value" + i);
        }
        assertThat(stringCacheManager.get(id)).isNull();
    }

    @Test
    void checkGetShouldReturnNullDueToLeastFrequentlyUsed() {
        for(int i = 1; i <= capacity - 1; i++) {
            stringCacheManager.put(i, "value" + i);
            stringCacheManager.get(i); //add count of usages
        }
        stringCacheManager.put(capacity, "DesiredValue");    //capacity is max
        stringCacheManager.put(capacity + 1, "SomeValue");
        assertThat(stringCacheManager.get(capacity)).isNull();
    }

    @ParameterizedTest
    @ValueSource(ints = {123, 10000, Integer.MAX_VALUE})
    void checkPutShouldUpdateExistingValue(int id) {
        String value = "Some Value";
        stringCacheManager.put(id, value);
        String expectedValue = "NEW_VALUE";
        stringCacheManager.put(id, expectedValue);
        assertThat(stringCacheManager.get(id)).isEqualTo(expectedValue);
    }

    @Test
    void checkDeleteShouldNotContainsValue() {
        for(int i = 1; i <= capacity; i++) {
            stringCacheManager.put(i, "value" + i);
        }
        stringCacheManager.delete(1);
        assertThat(stringCacheManager.get(1)).isNull();
    }

    @Test
    void checkDeleteShouldDoNothing() {
        for(int i = 1; i <= capacity; i++) {
            stringCacheManager.put(i, "value" + i);
        }
        stringCacheManager.delete(Integer.MAX_VALUE);
        assertThat(stringCacheManager.get(1)).isNotNull();
    }
}