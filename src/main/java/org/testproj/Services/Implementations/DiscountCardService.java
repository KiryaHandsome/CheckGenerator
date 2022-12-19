package org.testproj.Services.Implementations;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.testproj.Models.DiscountCard;
import org.testproj.Repositories.DiscountCardRepository;
import org.testproj.Services.AbstractShopService;

@Service
public class DiscountCardService extends AbstractShopService<DiscountCard> {
    //private DiscountCardRepository discountCardRepository;

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
