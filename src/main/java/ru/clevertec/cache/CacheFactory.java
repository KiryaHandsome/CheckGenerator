package ru.clevertec.Cache;

import ru.clevertec.Cache.Implementations.LFUCache;
import ru.clevertec.Cache.Implementations.LRUCache;

/**
 * Factory pattern implementation for Cache
 */
public class CacheFactory {
    /**
     * @param type cache implementation
     * @param capacity max size of cache
     */
    public static <T> Cache<T> getCache(CacheType type, int capacity) {
        return switch (type) {
            case LRU -> new LRUCache<>(capacity);
            case LFU -> new LFUCache<>(capacity);
        };
    }
}
