package ru.clevertec.Services.Implementations;


import io.micrometer.observation.annotation.Observed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.clevertec.Models.DiscountCard;
import ru.clevertec.Repositories.DiscountCardRepository;
import ru.clevertec.Services.AbstractShopService;

@Service
public class DiscountCardService extends AbstractShopService<DiscountCard> {
    @Autowired
    public DiscountCardService(DiscountCardRepository discountCardRepository) {
        super(discountCardRepository);
    }

    @Override
    public DiscountCard update(int id, DiscountCard object) {
        object.setId(id);
        return repository.save(object);
    }
}
