package ru.clevertec.Check;

import ru.clevertec.Model.Product;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

public class CheckUtil {
    public static final String CASH_RECEIPT = "CASH RECEIPT";
    public static final String SHOP_NAME = "Clevertec SHOP";
    public static final String PROMOTION_CLAUSE = "PROM. - promotional product;\n" +
            "If quantity of promotional product > 5 \n" +
            "then u get discount 10% on this position!\n\n";
    public static final char CURRENCY_SYMBOL = '$';
    public static final String QUANTITY = "QTY";
    public static final String PROMOTIONAL = "PROM.";
    public static final String PRICE = "PRICE";
    public static final String TOTAL = "TOTAL";
    public static final String DESCRIPTION = "DESCRIPTION";
    public static final String COST = "COST";
    public static final String TOTAL_COST = "TOTAL COST";
    public static final String DISCOUNT = "DISCOUNT";
    private static final DecimalFormat decimalFormat = new DecimalFormat("0.00");

    public static String getCurrentDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        return LocalDate.now().format(formatter);
    }

    public static String getCurrentTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        return LocalTime.now().format(formatter);
    }

    public static int numberOfDigits(int num) {
        return (int) Math.log10(num) + 1;
    }

    public static String getSpacesToPlaceInCenter(int checkWidth, String s) {
        return " ".repeat((checkWidth - s.length()) / 2);
    }

    public static String getDelimiter(int checkWidth) {
        return "*".repeat(checkWidth) + '\n';
    }

    public static double getPromotionalPrice(Product product, int quantity) {
        return product.getPrice() * quantity * (product.isPromotional() && (quantity > 5) ? 0.9 : 1);
    }

    public static String priceToString(double price) {
        return decimalFormat.format(price) + CURRENCY_SYMBOL;
    }

    public static String[] parseParams(Map<String, String> params) {
        return params.entrySet()
                .stream()
                .map(e -> e.getKey() + "-" + e.getValue())
                .toArray(String[]::new);
    }

}
