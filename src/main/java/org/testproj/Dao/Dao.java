package org.testproj.Dao;

import org.testproj.Entities.Product;

import java.util.List;
import java.util.Optional;

public interface Dao<T> {

   T get(int id);

    void save(T obj);

    void update(T obj, T other);

    void delete(T t);
}
