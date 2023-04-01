package ru.clevertec.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.clevertec.Model.DiscountCard;

@Repository
public interface DiscountCardRepository extends JpaRepository<DiscountCard, Integer> {

}