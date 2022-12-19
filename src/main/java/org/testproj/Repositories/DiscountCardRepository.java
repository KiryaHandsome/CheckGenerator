package org.testproj.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.testproj.Models.DiscountCard;

@Repository
public interface DiscountCardRepository extends JpaRepository<DiscountCard, Integer> {

}