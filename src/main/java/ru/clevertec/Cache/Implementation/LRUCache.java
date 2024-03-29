package ru.clevertec.Cache.Implementation;

import ru.clevertec.Cache.Cache;

import java.util.LinkedHashMap;
import java.util.Map;

public class LRUCache implements Cache {

    /**
     * Storage for cache.
     * LinkedHashMap implements access order
     * that's why is the best approach to use it in LRU cache
     */
    private LinkedHashMap<Integer, Object> cache;
    private final int capacity;

    /**
     * @param capacity max size of cache
     * @throws IllegalArgumentException when passed capacity less than 1
     *                                  Override LinkedHashMap.removeEldestEntry in way
     *                                  that it will remove the least recently used object
     *                                  when size becomes more than capacity
     */
    public LRUCache(int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("Capacity must be natural");
        }
        this.capacity = capacity;
        cache = new LinkedHashMap<>(LRUCache.this.capacity, 0.75f, true) {
            @Override
            protected boolean removeEldestEntry(Map.Entry eldest) {
                return size() > LRUCache.this.capacity;
            }
        };
    }

    /**
     * Get object from cache
     *
     * @param id of desired object
     * @return instance if object in cache, null otherwise
     */
    @Override
    public Object get(int id) {
        return cache.getOrDefault(id, null);
    }

    /**
     * Put object to cache if it doesn't contain such,
     * otherwise update it in cache.
     * If size == capacity, it removes the least frequently used object
     *
     * @param id     id of stored object
     * @param object object to store
     */
    @Override
    public void put(int id, Object object) {
        cache.put(id, object);
    }

    /**
     * Remove object from cache by id
     *
     * @param id id object that will be deleted
     *           Do nothing if there is no object with such id
     */
    @Override
    public void delete(int id) {
        cache.remove(id);
    }
}
