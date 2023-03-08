package ru.clevertec.Cache;


public interface Cache<T> {
    /**
     * @param id id of desired object
     * @return object from cache if it exists,
     * null otherwise
     */
    T get(long id);

    /**
     * This method should save object in cache
     * or update it if already presented
     * @param id object id
     * @param object value to save
     */
    void put(long id, T object);

    /**
     * This method should delete object from cache
     * or do nothing it if already presented
     * @param id object id
     */
    void delete(long id);
}
