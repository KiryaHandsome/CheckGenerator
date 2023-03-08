package ru.clevertec.Cache;

import ru.clevertec.Cache.Implementations.LFUCache;
import ru.clevertec.Cache.Implementations.LRUCache;


public class CacheFactory {
    public static <T> Cache<T> getCache(CacheType type, int capacity) {
        return switch (type) {
            case LRU -> new LRUCache<>(capacity);
            case LFU -> new LFUCache<>(capacity);
        };
    }
}
