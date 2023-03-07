package ru.clevertec.Cache;

public interface CacheManager<T> {
    T get(long id);
    void put(long id, T object);
    void delete(long id);
}
