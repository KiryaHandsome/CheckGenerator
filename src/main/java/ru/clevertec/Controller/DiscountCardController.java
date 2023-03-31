package ru.clevertec.Controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.clevertec.Model.DiscountCard;
import ru.clevertec.Service.Implementation.DiscountCardService;

import java.util.List;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class DiscountCardController {

    private final DiscountCardService discountCardService;

    @GetMapping("/discount-cards")
    public List<DiscountCard> getAllDiscountCards() {
        return discountCardService.findAll();
    }

    @GetMapping("/discount-card/{id}")
    public DiscountCard getDiscountCardById(@PathVariable String id) {
        return discountCardService.find(Integer.parseInt(id));
    }

    @PostMapping("/discount-card/create")
    public DiscountCard createDiscountCard(@RequestBody DiscountCard discountCard) {
        return discountCardService.create(discountCard);
    }

    @DeleteMapping("/discount-card/delete/{id}")
    public ResponseEntity<String> deleteDiscountCard(@PathVariable String id) {
        discountCardService.delete(Integer.parseInt(id));
        return ResponseEntity.status(HttpStatus.OK).body("Discount card with id " + id + "" +
                " either was deleted or wasn't in db");
    }

    @PutMapping("/discount-card/update/{id}")
    public DiscountCard updateDiscountCard(@PathVariable String id,
                                           @RequestBody DiscountCard discountCard) {
        return discountCardService.update(Integer.parseInt(id), discountCard);
    }
}
