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
    public ResponseEntity<List<DiscountCard>> getAllDiscountCards() {
        List<DiscountCard> data = discountCardService.findAll();
        HttpStatus status = HttpStatus.OK;
        if (data == null) {
            status = HttpStatus.NOT_FOUND;
        }
        return new ResponseEntity<>(data, status);
    }

    @GetMapping("/discount-card/{id}")
    public ResponseEntity<DiscountCard> getDiscountCardById(@PathVariable String id) {
        try {
            DiscountCard data = discountCardService.find(Integer.parseInt(id));
            return new ResponseEntity<>(data, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/discount-card/create")
    public ResponseEntity<DiscountCard> createDiscountCard(@RequestBody DiscountCard discountCard) {
        DiscountCard data = discountCardService.create(discountCard);
        return new ResponseEntity<>(data, HttpStatus.OK);
    }

    @DeleteMapping("/discount-card/delete/{id}")
    public ResponseEntity<String> deleteDiscountCard(@PathVariable String id) {
        discountCardService.delete(Integer.parseInt(id));
        return new ResponseEntity<>("Discount card with id " + id +
                " either was deleted or wasn't in db", HttpStatus.OK);
    }

    @PutMapping("/discount-card/update/{id}")
    public ResponseEntity<DiscountCard> updateDiscountCard(
            @PathVariable String id,
            @RequestBody DiscountCard discountCard) {
        DiscountCard data = discountCardService.update(Integer.parseInt(id), discountCard);
        return new ResponseEntity<>(data, HttpStatus.OK);
    }
}
