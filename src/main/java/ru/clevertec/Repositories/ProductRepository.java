package ru.clevertec.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.clevertec.Models.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

}
