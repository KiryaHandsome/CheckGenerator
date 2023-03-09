package ru.clevertec.Service;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ShopService<T> {
    T create(T product);

    T find(int id);

    List<T> findAll();

    void deleteAll();

    T update(int id, T object);

    public void delete(int id);
}
