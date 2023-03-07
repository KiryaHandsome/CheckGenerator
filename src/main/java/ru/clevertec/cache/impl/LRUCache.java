package ru.clevertec.cache.impl;

import ru.clevertec.cache.CacheManager;

import java.util.LinkedHashMap;
import java.util.Map;

public class LRUCache<T> implements CacheManager<T> {
    private LinkedHashMap<Integer, T> cache;
    private final int CAPACITY;

    public LRUCache(int capacity) {
        this.CAPACITY = capacity;
        cache = new LinkedHashMap<>(CAPACITY, 0.75f, true) {
            @Override
            protected boolean removeEldestEntry(Map.Entry eldest) {
                return size() > CAPACITY;
            }
        };
    }

    @Override
    public T get(int id) {
        return cache.getOrDefault(id, null);
    }

    @Override
    public boolean contains(int id) {
        return cache.containsKey(id);
    }

    @Override
    public void put(int id, T object) {
        cache.put(id, object);
    }

    @Override
    public void delete(int id) {
        cache.remove(id);
    }
}
