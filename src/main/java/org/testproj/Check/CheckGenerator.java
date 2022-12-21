package org.testproj.Check;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.testproj.Exceptions.DiscountCardAlreadyPresentedException;
import org.testproj.Models.DiscountCard;
import org.testproj.Models.Product;
import org.testproj.Services.Implementations.DiscountCardService;
import org.testproj.Services.Implementations.ProductService;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.security.InvalidParameterException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

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

    private Map<Product, Integer> productAndQuantity;
    private DiscountCard discountCard;
    private int maxNameLength;
    private int maxPriceLength;
    private int maxQuantityLength;
    private String check;

    public Map<Integer, Integer> parseArguments(String... args)
            throws DiscountCardAlreadyPresentedException {
        Map<Integer, Integer> map = new HashMap<>();
        for (String arg : args) {
            String[] values = arg.split("-");
            if (values.length != 2) {
                throw new InvalidParameterException("Invalid argument in command line.");
            }
            if (values[0].equals("card")) {
                if(discountCard != null) {
                    throw new DiscountCardAlreadyPresentedException(
                            "There are few discount cards in arguments");
                }
                discountCard = discountCardService.find(Integer.parseInt(values[1]));
            } else {
                int productId = Integer.parseInt(values[0]);
                int quantity = Integer.parseInt(values[1]);
                if (productId == 0 || quantity == 0) {
                    throw new InvalidParameterException("Id and quantity must be greater than 0.");
                }
                int previousQuantity = Optional.ofNullable(map.get(productId)).orElse(0);
                map.put(productId, previousQuantity + quantity);  //case when map already got this product
            }
        }
        return map;
    }

    public Map<Product, Integer> getProductsFromDb(Map<Integer, Integer> info)
            throws DiscountCardAlreadyPresentedException {
        Map<Product, Integer> map = new HashMap<>();
        for (Map.Entry<Integer, Integer> pair : info.entrySet()) {
            int id = pair.getKey();
            int quantity = pair.getValue();
            Product product = productService.find(id);
            //get max lengths to shape check
            maxQuantityLength = Math.max(maxQuantityLength, numberOfDigits(quantity));
            maxNameLength = Math.max(maxNameLength, product.getName().length());
            maxPriceLength = Math.max(maxPriceLength, numberOfDigits((int) product.getPrice()));
            map.put(product, quantity);
        }
        productAndQuantity = map;
        return map;
    }

    public String generateCheck(Map<Product, Integer> info) {
        int qtyWidth = Math.max(maxQuantityLength, 3) + 1;
        int descriptionWidth = Math.max(maxNameLength, 11) + 1;
        int priceWidth = Math.max(maxPriceLength, 5) + 5;
        int totalWidth = priceWidth + 3;
        int isPromotionalWidth = 5;
        int checkWidth = qtyWidth + descriptionWidth + priceWidth + totalWidth + isPromotionalWidth + 2;
        StringBuilder builder = new StringBuilder();
        builder.append("%-" + qtyWidth + "s")
                .append("%" + descriptionWidth + "s  ")
                .append("%" + isPromotionalWidth + "s")
                .append("%" + priceWidth + "s ")
                .append("%" + totalWidth + "s");
        String template = builder.toString();
        StringBuilder checkBase = new StringBuilder();
        checkBase.append(headerText(checkWidth));
        checkBase.append(String.format(template, "QTY", "DESCRIPTION", "PROM.", "PRICE", "TOTAL\n"));
        checkBase.append("*".repeat(checkWidth) + "\n"); //delimiter
        builder.setLength(0);
        double totalCost = 0;
        for (Map.Entry<Product, Integer> pair : productAndQuantity.entrySet()) {
            Product p = pair.getKey();
            int qty = pair.getValue();
            boolean isProm = p.isPromotional();
            double resultPrice = p.getPrice() * qty * (isProm && (qty > 5) ? 0.9 : 1);
            totalCost += resultPrice;
            checkBase.append(String.format(template, qty, p.getName(),
                    isProm ? "y" : "n",
                    Math.round(p.getPrice() * 100.0) / 100.0,
                    Math.round(resultPrice * 100.0) / 100.0 + "\n"));
        }
        checkBase.append("*".repeat(checkWidth) + "\n"); //delimiter
        checkBase.append(String.format("COST:%" +
                (checkWidth - 5) + ".2f\n", totalCost));  //5-len of 'cost:' string
        int argument;
        if (discountCard != null) {
            argument = (int) (discountCard.getDiscount() * 100);
            totalCost = totalCost * (1 - discountCard.getDiscount());
        } else {
            argument = 0;
        }
        checkBase.append(String.format("DISCOUNT:%" +
                (checkWidth - 10) + "d%%\n", argument)); //10-len of 'discount:%'string
        checkBase.append(String.format("TOTAL COST:%" +
                (checkWidth - 11) + ".2f\n", totalCost));  //11-len of 'total cost:' string
        check = checkBase.toString();
        return check;
    }

    private static String headerText(int checkWidth) {
        StringBuilder builder = new StringBuilder();
        String checkSpaces = getSpacesToPlaceInCenter(checkWidth, "CASH RECEIPT");
        builder.append(checkSpaces)
                .append("CASH RECEIPT")
                .append(checkSpaces + '\n');
        String companySpaces = getSpacesToPlaceInCenter(checkWidth, "Clevertec SHOP");
        builder.append(companySpaces)
                .append("Clevertec SHOP")
                .append(companySpaces + '\n');
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String formattedDate = LocalDate.now().format(formatter);
        formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        String formattedTime = LocalTime.now().format(formatter);
        builder.append(String.format("%" + (checkWidth - 13) + "s %s\n", "Date:", formattedDate))
                .append(String.format("%" + (checkWidth - 13) + "s %s\n\n", "Time:", formattedTime));
        builder.append("PROM. - promotional product;\n")
                .append("If quantity of promotional product > 5 \n" +
                        "then u get discount 10% on this position!\n\n");
        return builder.toString();
    }

    private static int numberOfDigits(int num) {
        return (int) Math.log10(num) + 1;
    }

    private static String getSpacesToPlaceInCenter(int checkWidth, String s) {
        return " ".repeat((checkWidth - s.length()) / 2);
    }

    public void saveCheckToFile(String fileName) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
        writer.write(check);
        writer.close();
    }

    public String getCheck() {
        return check;
    }

    public DiscountCard getDiscountCard() {
        return discountCard;
    }

    public void clearFields() {
        discountCard = null;
        productAndQuantity = null;
    }
}