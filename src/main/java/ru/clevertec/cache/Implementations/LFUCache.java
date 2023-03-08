package ru.clevertec.Cache.Implementations;

import org.springframework.beans.factory.annotation.Value;
import ru.clevertec.Cache.Cache;

import java.util.*;


public class LFUCache<T> implements Cache<T> {
    /**
     * Map that contains id as key
     * and object as value
     */
    private Map<Long, T> values = new HashMap<>();
    /**
     * Map that contains id as key
     * and count of usages as value
     */
    private Map<Long, Integer> countMap = new HashMap<>();
    /**
     * Sorted map that contains frequency of using as key
     * and list of objects id as value
     */
    private TreeMap<Integer, List<Long>> frequencyMap = new TreeMap<>();

    private final int CAPACITY;

    /**
     * @param capacity max size of cache
     * @throws IllegalArgumentException when passed capacity less than 1
     */
    public LFUCache(int capacity) {
        System.out.println(capacity);
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
    public T get(long id) {
        if (!values.containsKey(id)) {
            return null;
        }
        int frequency = countMap.get(id);
        countMap.put(id, frequency + 1);
        frequencyMap.get(frequency).remove(id);
        if (frequencyMap.get(frequency).size() == 0) {
            frequencyMap.remove(frequency);
        }
        frequencyMap.computeIfAbsent(frequency + 1, v -> new LinkedList<>()).add(id);
        return values.get(id);
    }

    /**
     * Put object to cache if it doesn't contain such,
     * otherwise update it in cache.
     * If size == capacity, it removes the least frequently used object
     * @param id id of stored object
     * @param object object to store
     * */
    @Override
    public void put(long id, T object) {
        if(!values.containsKey(id)) {
            if(values.size() == CAPACITY) {
                int leastFrequency =  frequencyMap.firstKey();
                long idToRemove = frequencyMap.get(leastFrequency).remove(0);

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

            frequencyMap.get(frequency).remove(Long.valueOf(id));
            if(frequencyMap.get(frequency).size() == 0) {
                frequencyMap.remove(frequency);
            }

            frequencyMap.computeIfAbsent(frequency + 1, v -> new LinkedList<>()).add(id);
        }
    }

    /**
     * Remove object from cache by id
     * @param id id object that will be deleted
     * Do nothing if there is no object with such id
     * */
    @Override
    public void delete(long id) {
        if(values.containsKey(id)) {
            int frequency = countMap.get(id);
            countMap.remove(id);
            values.remove(id);
            frequencyMap.get(frequency).remove(id);
        }
    }
}
