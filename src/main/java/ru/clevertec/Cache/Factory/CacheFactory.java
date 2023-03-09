package ru.clevertec.Cache.Factory;

import ru.clevertec.Cache.Cache;
import ru.clevertec.Cache.Implementation.LFUCache;
import ru.clevertec.Cache.Implementation.LRUCache;

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
