package ru.clevertec.cache.impl;

import ru.clevertec.cache.CacheManager;

import java.util.*;

public class LFUCache<T> implements CacheManager<T> {
    /**
     * Map that contains id as key
     * and object as value
     */
    private Map<Integer, T> values = new HashMap<>();
    /**
     * Map that contains id as key
     * and count of usages as value
     */
    private Map<Integer, Integer> countMap = new HashMap<>();
    /**
     * Sorted map that contains frequency of using as key
     * and list of objects id as value
     */
    private TreeMap<Integer, List<Integer>> frequencyMap = new TreeMap<>();

    private final int CAPACITY;

    /**
     * @param capacity max size of cache
     * @throws IllegalArgumentException when passed capacity less than 1
     */
    public LFUCache(int capacity) {
        if(capacity <= 0) {
            throw new IllegalArgumentException("Capacity must be natural");
        }
        this.CAPACITY = capacity;
    }

    /**
     * Get object from cache
     * @param id of desired object
     * @return instance if object in cache, null otherwise
     */
    @Override
    public T get(int id) {
        if (!values.containsKey(id)) {
            return null;
        }
        int frequency = countMap.get(id);
        countMap.put(id, frequency + 1);
        frequencyMap.get(frequency).remove(Integer.valueOf(id));
        if (frequencyMap.get(frequency).size() == 0) {
            frequencyMap.remove(frequency);
        }
        frequencyMap.computeIfAbsent(frequency + 1, v -> new LinkedList<>()).add(id);
        return values.get(id);
    }

    /**
     * @param id id of desired object
     * @return true if object in cache, false otherwise
     */
    @Override
    public boolean contains(int id) {
        return values.containsKey(id);
    }

    /**
     * Put object to cache if it doesn't contain such,
     * otherwise update it in cache.
     * If size == capacity, it removes the least frequently used object
     * @param id id of stored object
     * @param object object to store
     * */
    @Override
    public void put(int id, T object) {
        if(!values.containsKey(id)) {
            if(values.size() == CAPACITY) {
                int leastFrequency =  frequencyMap.firstKey();
                int idToRemove = frequencyMap.get(leastFrequency).remove(0);

                if(frequencyMap.get(leastFrequency).size() == 0) {
                    frequencyMap.remove(leastFrequency);
                }

                countMap.remove(idToRemove);
                values.remove(idToRemove);
            }
            values.put(id, object);
            countMap.put(id, 1);
            frequencyMap.computeIfAbsent(1, v -> new LinkedList<>()).add(id);
        } else {
            values.put(id, object);

            int frequency = countMap.get(id);
            countMap.put(id, frequency + 1);

            frequencyMap.get(frequency).remove(Integer.valueOf(id));
            if(frequencyMap.get(frequency).size() == 0) {
                frequencyMap.remove(frequency);
            }

            frequencyMap.computeIfAbsent(frequency, v -> new LinkedList<>()).add(id);
        }
    }
}
