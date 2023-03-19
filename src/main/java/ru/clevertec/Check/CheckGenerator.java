package ru.clevertec.Check;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.clevertec.Exception.DiscountCardAlreadyPresentedException;
import ru.clevertec.Model.Check;
import ru.clevertec.Model.DiscountCard;
import ru.clevertec.Model.Product;
import ru.clevertec.Service.Implementation.DiscountCardService;
import ru.clevertec.Service.Implementation.ProductService;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

@Component
public class CheckGenerator {
    final private ProductService productService;
    final private DiscountCardService discountCardService;

    @Autowired
    public CheckGenerator(ProductService productService,
                          DiscountCardService discountCardService) {
        this.productService = productService;
        this.discountCardService = discountCardService;
    }

    private void addDataFromDb(Map<Integer, Integer> info, Check check)
            throws DiscountCardAlreadyPresentedException {
        for (Map.Entry<Integer, Integer> pair : info.entrySet()) {
            int id = pair.getKey();
            int quantity = pair.getValue();
            if (quantity == 0) { //is discount card
                DiscountCard discountCard = discountCardService.find(id);
                check.addDiscountCard(discountCard);
            } else {
                Product product = productService.find(id);
                check.addProduct(product, quantity);
            }
        }
    }

    public Check generateCheck(Map<Integer, Integer> info) throws DiscountCardAlreadyPresentedException {
        Check check = new Check();
        addDataFromDb(info, check);
        return check;
    }

    public void saveCheckToFile(String fileName, String checkContent) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
        writer.write(checkContent);
        writer.close();
    }

}