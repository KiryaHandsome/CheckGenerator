package org.testproj.Dao;

import java.util.List;
import java.util.Optional;

public interface Dao<T> {

    Optional<T> get(int id);

    List<T> getAll();

    void save(T obj);

    void update(T obj, String[] params);

    void delete(T t);
}
