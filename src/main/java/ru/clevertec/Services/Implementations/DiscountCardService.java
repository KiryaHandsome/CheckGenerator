package ru.clevertec.Services.Implementations;


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

    public DiscountCard update(int id, DiscountCard object) {
        object.setId(id);
        return repository.save(object);
    }

    public void delete(int id) {
        DiscountCard card = new DiscountCard();
        card.setId(id);
        repository.delete(card);
    }
}
