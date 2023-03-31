package ru.clevertec.Cache.Implementation;

import ru.clevertec.Cache.Cache;

import java.util.*;


public class LFUCache implements Cache {

    /**
     * Map that contains id as key
     * and object as value
     */
    private final Map<Integer, Object> values = new HashMap<>();

    /**
     * Map that contains id as key
     * and count of usages as value
     */
    private final Map<Integer, Integer> countMap = new HashMap<>();

    /**
     * Sorted map that contains frequency of using as key
     * and list of objects id as value
     */
    private final TreeMap<Integer, List<Integer>> frequencyMap = new TreeMap<>();
    private final int capacity;

    /**
     * @param capacity max size of cache
     * @throws IllegalArgumentException if passed capacity less than 1
     */
    public LFUCache(int capacity) {
        System.out.println(capacity);
        if (capacity <= 0) {
            throw new IllegalArgumentException("Capacity must be natural");
        }
        this.capacity = capacity;
    }

    /**
     * Get object from cache
     *
     * @param id of desired object
     * @return instance if object in cache, null otherwise
     */
    @Override
    public Object get(int id) {
        if (!values.containsKey(id)) {
            return null;
        }
        updateStorages(id);
        return values.get(id);
    }

    /**
     * Update count of usages in {@link LFUCache#countMap}
     * and add id to {@link LFUCache#frequencyMap} with updated frequency
     *
     * @param id id of upgradeable object
     */
    private void updateStorages(int id) {
        int frequency = countMap.get(id);
        countMap.put(id, frequency + 1);
        frequencyMap.get(frequency).remove(Integer.valueOf(id));
        if (frequencyMap.get(frequency).size() == 0) {
            frequencyMap.remove(frequency);
        }
        frequencyMap.computeIfAbsent(frequency + 1, v -> new LinkedList<>()).add(id);
    }

    /**
     * Put object to cache if it doesn't contain such,
     * otherwise update it in cache.
     * If {@link LFUCache#values}.size() == {@link LFUCache#capacity}, it removes the least frequently used object
     *
     * @param id     id of stored object
     * @param object object to store
     */
    @Override
    public void put(int id, Object object) {
        if (!values.containsKey(id)) {
            if (values.size() == capacity) {
                int leastFrequency = frequencyMap.firstKey();
                int idToRemove = frequencyMap.get(leastFrequency).remove(0);

                if (frequencyMap.get(leastFrequency).size() == 0) {
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
            updateStorages(id);
        }
    }

    /**
     * Remove object from cache by id
     *
     * @param id id object that will be deleted
     *           Do nothing if there is no object with such id
     */
    @Override
    public void delete(int id) {
        if (values.containsKey(id)) {
            int frequency = countMap.get(id);
            countMap.remove(id);
            values.remove(id);
            frequencyMap.get(frequency).remove(id);
        }
    }
}
