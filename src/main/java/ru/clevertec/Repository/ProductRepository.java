package ru.clevertec.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.clevertec.Model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

}
