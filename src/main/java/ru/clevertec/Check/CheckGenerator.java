package ru.clevertec.Check;


import lombok.RequiredArgsConstructor;
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
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CheckGenerator {

    private final ProductService productService;
    private final DiscountCardService discountCardService;

    private static final String DIRECTORY_NAME = "data";
    private static final String FILE_NAME = "check.txt";
    private static final String OUTPUT_PATH = DIRECTORY_NAME + "/" + FILE_NAME;

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

    public void saveCheckToFile(String checkContent) throws IOException {
        Files.createDirectories(Paths.get(DIRECTORY_NAME));
        BufferedWriter writer = new BufferedWriter(new FileWriter(OUTPUT_PATH));
        writer.write(checkContent);
        writer.close();
    }

}