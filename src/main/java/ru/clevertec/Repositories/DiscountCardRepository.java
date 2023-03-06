package ru.clevertec.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.clevertec.Models.DiscountCard;

@Repository
public interface DiscountCardRepository extends JpaRepository<DiscountCard, Integer> {

}