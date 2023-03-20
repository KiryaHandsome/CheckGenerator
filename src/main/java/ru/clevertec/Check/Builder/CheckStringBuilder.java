package ru.clevertec.Check.Builder;

import ru.clevertec.Check.Builder.Api.CheckBuilder;
import ru.clevertec.Check.CheckSettings;
import ru.clevertec.Check.CheckUtil;
import ru.clevertec.Model.DiscountCard;
import ru.clevertec.Model.Product;

import java.util.Map;

public class CheckStringBuilder implements CheckBuilder {
    private final CheckSettings settings;
    private double totalCost;
    private StringBuilder checkBuilder;
    private String productTemplate;

    public CheckStringBuilder(CheckSettings checkSettings) {
        this.settings = checkSettings;
    }

    @Override
    public String buildCheck(Map<Product, Integer> products, DiscountCard discountCard) {
        int checkWidth = settings.getCheckWidth();
        productTemplate = createProductTemplate();
        checkBuilder = new StringBuilder();
        checkBuilder.append(shapeHeaderText(checkWidth))
                .append(String.format(productTemplate,
                        CheckUtil.QUANTITY,
                        CheckUtil.DESCRIPTION,
                        CheckUtil.PROMOTIONAL,
                        CheckUtil.PRICE,
                        CheckUtil.TOTAL + "\n"))
                .append(CheckUtil.getDelimiter(checkWidth)); //delimiter
        addProductsToCheck(products);
        checkBuilder.append(CheckUtil.getDelimiter(checkWidth)) //delimiter
                .append(String.format("COST:%" +
                        (checkWidth - 6) + ".2f" + CheckUtil.CURRENCY_SYMBOL + "\n", totalCost));  //5-len of 'cost:' string
        if (discountCard != null) {
            int discount = (int) (discountCard.getDiscount() * 100);
            totalCost = totalCost * (1 - discountCard.getDiscount());
            checkBuilder.append(String.format("DISCOUNT:%" +
                    (checkWidth - 10) + "d%%\n", discount)); //10-len of 'discount:%' string
        }
        checkBuilder.append(String.format("TOTAL COST:%" +
                (checkWidth - 12) + ".2f" + CheckUtil.CURRENCY_SYMBOL + "\n", totalCost));  //11-len of 'total cost:' string
        return checkBuilder.toString();
    }

    private void addProductsToCheck(Map<Product, Integer> products) {
        totalCost = 0;
        for (Map.Entry<Product, Integer> pair : products.entrySet()) {
            Product product = pair.getKey();
            int qty = pair.getValue();
            double priceWithPromotion = CheckUtil.getPromotionalPrice(product, qty);
            totalCost += priceWithPromotion;
            checkBuilder.append(String.format(productTemplate,
                    qty,
                    product.getName(),
                    product.isPromotional() ? "y" : "n",
                    CheckUtil.priceToString(product.getPrice()),
                    CheckUtil.priceToString(priceWithPromotion) + "\n"));
        }
    }

    private String createProductTemplate() {
        return "%-" + settings.getQuantityWidth() + "s" +
                "%" + settings.getDescriptionWidth() + "s  " +
                "%" + settings.getPromotionalWidth() + "s" +
                "%" + settings.getPriceWidth() + "s " +
                "%" + settings.getTotalWidth() + "s";
    }

    private String shapeHeaderText(int checkWidth) {
        StringBuilder headerBuilder = new StringBuilder();
        String checkSpaces = CheckUtil.getSpacesToPlaceInCenter(checkWidth, CheckUtil.CASH_RECEIPT);
        headerBuilder.append(checkSpaces)
                .append(CheckUtil.CASH_RECEIPT)
                .append(checkSpaces + '\n');
        String companySpaces = CheckUtil.getSpacesToPlaceInCenter(checkWidth, CheckUtil.SHOP_NAME);
        headerBuilder.append(companySpaces)
                .append(CheckUtil.SHOP_NAME)
                .append(companySpaces + '\n');
        headerBuilder.append(String.format("%" + settings.getDateAndTimeWidth() + "s %s\n", "Date:", CheckUtil.getCurrentDate()))
                .append(String.format("%" + settings.getDateAndTimeWidth() + "s %s\n\n", "Time:", CheckUtil.getCurrentTime()));
        headerBuilder.append(CheckUtil.PROMOTION_CLAUSE);
        return headerBuilder.toString();
    }
}
