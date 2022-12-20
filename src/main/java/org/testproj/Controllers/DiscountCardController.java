package org.testproj.Controllers;

import org.springframework.web.bind.annotation.*;
import org.testproj.Models.DiscountCard;
import org.testproj.Services.Implementations.DiscountCardService;

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
    public void deleteDiscountCard(@PathVariable String id) {
        discountCardService.delete(Integer.parseInt(id));
    }

    @PutMapping("/discount-card/update/{id}")
    public DiscountCard updateDiscountCard(@PathVariable String id,
                                           @RequestBody DiscountCard discountCard) {
        return discountCardService.update(Integer.parseInt(id), discountCard);
    }
}
