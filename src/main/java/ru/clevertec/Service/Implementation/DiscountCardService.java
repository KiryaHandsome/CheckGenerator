package ru.clevertec.Service.Implementation;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import ru.clevertec.Model.DiscountCard;
import ru.clevertec.Repository.DiscountCardRepository;
import ru.clevertec.Service.ShopService;

import java.util.List;

@Service
public class DiscountCardService implements ShopService<DiscountCard> {

    private final JpaRepository<DiscountCard, Integer> repository;

    @Autowired
    public DiscountCardService(DiscountCardRepository discountCardRepository) {
        this.repository = discountCardRepository;
    }

    @Override
    public DiscountCard create(DiscountCard product) {
        return repository.save(product);
    }

    @Override
    public DiscountCard find(int id) {
        return repository.getReferenceById(id);
    }

    @Override
    public List<DiscountCard> findAll() {
        return repository.findAll();
    }

    @Override
    public void deleteAll() {
        repository.deleteAll();
    }

    @Override
    public DiscountCard update(int id, DiscountCard object) {
        object.setId(id);
        return repository.save(object);
    }


    public void delete(int id) {
        repository.deleteById(id);
    }
}
