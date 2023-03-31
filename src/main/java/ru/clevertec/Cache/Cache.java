package ru.clevertec.Cache;


public interface Cache {

    /**
     * @param id id of desired object
     * @return object from cache if it exists,
     * null otherwise
     */
    Object get(int id);

    /**
     * This method should save object in cache
     * or update it if already presented
     * @param id object id
     * @param object value to save
     */
    void put(int id, Object object);

    /**
     * This method should delete object from cache
     * or do nothing it if already presented
     * @param id object id
     */
    void delete(int id);
}
