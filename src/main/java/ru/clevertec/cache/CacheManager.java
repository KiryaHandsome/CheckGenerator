package ru.clevertec.cache;

public interface CacheManager<T> {
    T get(int id);
    boolean contains(int id);
    void put(int id, T object);
}
