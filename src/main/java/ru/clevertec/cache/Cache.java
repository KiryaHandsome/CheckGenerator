package ru.clevertec.cache;

public interface Cache<T> {
    T get(int id);
    void put(int id, T value);
    void delete(int id);
    void post(int id, T value);
}
