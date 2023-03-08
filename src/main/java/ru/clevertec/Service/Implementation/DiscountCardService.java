package ru.clevertec.Service.Implementation;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.clevertec.Model.DiscountCard;
import ru.clevertec.Repository.DiscountCardRepository;
import ru.clevertec.Service.AbstractShopService;

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
