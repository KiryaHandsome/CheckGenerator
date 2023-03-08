package ru.clevertec.Cache;

public interface Cache<T> {
    T get(long id);
    void put(long id, T object);
    void delete(long id);
}
