package ru.clevertec.Controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.clevertec.Models.DiscountCard;
import ru.clevertec.Services.Implementations.DiscountCardService;

import java.util.List;

@RestController
@RequestMapping("/")
public class DiscountCardController {
    final private DiscountCardService discountCardService;

    public DiscountCardController(DiscountCardService discountCardService) {
        this.discountCardService = discountCardService;
    }

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
