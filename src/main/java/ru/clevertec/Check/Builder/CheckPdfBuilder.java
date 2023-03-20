package ru.clevertec.Check.Builder;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.borders.DashedBorder;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.HorizontalAlignment;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;
import ru.clevertec.Check.Builder.Api.CheckBuilder;
import ru.clevertec.Check.CheckUtil;
import ru.clevertec.Model.DiscountCard;
import ru.clevertec.Model.Product;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

public class CheckPdfBuilder implements CheckBuilder {
    private static final int PADDING = 5;
    private static final String templatePath = "src/main/resources/pdf/Clevertec_template.pdf";
    private static final String outputPath = "data/check.pdf";

    private double totalCost;

    @Override
    public String buildCheck(Map<Product, Integer> products, DiscountCard discountCard) {
        PdfDocument pdfDocument;
        Document document;
        try {
            Files.createDirectories(Paths.get("data"));
            pdfDocument = new PdfDocument(new PdfReader(templatePath), new PdfWriter(outputPath));
            document = new Document(pdfDocument);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        document.add(new Paragraph("\n".repeat(PADDING)));

        List<Cell> headerCells = createHeaderCells();
        Table productsTable = createProductsTable(products);
        Table totalTable = createTotalTable(discountCard).setWidth(UnitValue.createPercentValue(100));
        Table resultTable = new Table(1).setBorder(new DashedBorder(1));
        resultTable.setHorizontalAlignment(HorizontalAlignment.CENTER);

        headerCells.forEach(resultTable::addCell);
        resultTable.addCell(productsTable);
        resultTable.addCell(totalTable);

        document.add(resultTable);
        document.close();
        return outputPath;
    }

    /**
     * Shape table with information from map.
     *
     * @return table with products info for check
     */
    private Table createProductsTable(Map<Product, Integer> products) {
        Table productsTable = new Table(5);
        List<Cell> namingCells = createNamingCells();
        namingCells.forEach(productsTable::addHeaderCell);
        totalCost = 0;
        for (Map.Entry<Product, Integer> entry : products.entrySet()) {
            Product product = entry.getKey();
            int qty = entry.getValue();
            double priceWithPromotion = CheckUtil.getPromotionalPrice(product, qty);
            totalCost += priceWithPromotion;
            List<Cell> rowCells = createProductRow(entry.getKey(), entry.getValue());
            rowCells.forEach(productsTable::addCell);
        }
        return productsTable;
    }

    /**
     * Creates cells for product in check receipt.
     *
     * @return list with cells
     */
    private List<Cell> createProductRow(Product product, int quantity) {
        Cell quantityCell = new Cell()
                .setBorder(Border.NO_BORDER)
                .setBorderRight(new DashedBorder(1))
                .add(new Paragraph(String.valueOf(quantity)));
        Cell nameCell = new Cell()
                .setBorder(Border.NO_BORDER)
                .setBorderRight(new DashedBorder(1))
                .add(new Paragraph(product.getName()));
        Cell promCell = new Cell()
                .setTextAlignment(TextAlignment.CENTER)
                .setBorder(Border.NO_BORDER)
                .setBorderRight(new DashedBorder(1))
                .add(new Paragraph(product.isPromotional() ? "y" : "n"));
        Cell priceCell = new Cell()
                .setTextAlignment(TextAlignment.RIGHT)
                .setBorder(Border.NO_BORDER)
                .setBorderRight(new DashedBorder(1))
                .add(new Paragraph(CheckUtil.priceToString(product.getPrice())));
        double priceWithPromotion = CheckUtil.getPromotionalPrice(product, quantity);
        String stringPriceWithPromotion = CheckUtil.priceToString(priceWithPromotion);
        Cell totalPriceCell = new Cell()
                .setTextAlignment(TextAlignment.RIGHT)
                .setBorder(Border.NO_BORDER)
                .add(new Paragraph(stringPriceWithPromotion));
        return List.of(quantityCell, nameCell, promCell, priceCell, totalPriceCell);
    }

    /**
     * @return list with cells filled with names from {@link ru.clevertec.Check.CheckUtil}
     * like qty, description, total and others
     */
    private List<Cell> createNamingCells() {
        Cell quantityCell = setUpNamingCell()
                .setBorderLeft(Border.NO_BORDER)
                .add(new Paragraph(CheckUtil.QUANTITY));
        Cell nameCell = setUpNamingCell()
                .add(new Paragraph(CheckUtil.DESCRIPTION));
        Cell promCell = setUpNamingCell()
                .add(new Paragraph(CheckUtil.PROMOTIONAL));
        Cell priceCell = setUpNamingCell()
                .add(new Paragraph(CheckUtil.PRICE));
        Cell totalPriceCell = setUpNamingCell()
                .setBorderRight(Border.NO_BORDER)
                .add(new Paragraph(CheckUtil.TOTAL));
        return List.of(quantityCell, nameCell, promCell, priceCell, totalPriceCell);
    }

    private Cell setUpNamingCell() {
        return new Cell().setTextAlignment(TextAlignment.CENTER)
                .setBorder(Border.NO_BORDER)
                .setBorderRight(new DashedBorder(1))
                .setBorderBottom(new DashedBorder(1));
    }

    /**
     * Creates cells for header with corresponding properties.
     *
     * @return list of cells for header
     */
    private List<Cell> createHeaderCells() {
        Cell cashReceiptCell = new Cell()
                .setTextAlignment(TextAlignment.CENTER)
                .setBorder(Border.NO_BORDER)
                .add(new Paragraph(CheckUtil.CASH_RECEIPT));
        Cell shopNameCell = new Cell()
                .setTextAlignment(TextAlignment.CENTER)
                .setBorder(Border.NO_BORDER)
                .add(new Paragraph(CheckUtil.SHOP_NAME));
        Cell dateCell = new Cell()
                .setTextAlignment(TextAlignment.RIGHT)
                .setBorder(Border.NO_BORDER)
                .add(new Paragraph(CheckUtil.getCurrentDate()));
        Cell timeCell = new Cell()
                .setTextAlignment(TextAlignment.RIGHT)
                .setBorder(Border.NO_BORDER)
                .add(new Paragraph(CheckUtil.getCurrentTime()));
        Cell promotionCell = new Cell()
                .setBorder(new DashedBorder(1))
                .add(new Paragraph(CheckUtil.PROMOTION_CLAUSE));

        return List.of(cashReceiptCell, shopNameCell, dateCell, timeCell, promotionCell);
    }

    /**
     * Generates table with total cost output
     */
    private Table createTotalTable(DiscountCard discountCard) {
        Table totalTable = new Table(2);
        Cell costCell = new Cell()
                .setTextAlignment(TextAlignment.LEFT)
                .setBorder(Border.NO_BORDER)
                .add(new Paragraph(CheckUtil.COST));
        Cell costDataCell = new Cell()
                .setTextAlignment(TextAlignment.RIGHT)
                .setBorder(Border.NO_BORDER)
                .add(new Paragraph(CheckUtil.priceToString(totalCost)));
        Cell discountCell = new Cell()
                .setTextAlignment(TextAlignment.LEFT)
                .setBorder(Border.NO_BORDER)
                .add(new Paragraph(CheckUtil.DISCOUNT));
        int discount = 0;
        if (discountCard != null) {
            discount = (int) (discountCard.getDiscount() * 100);
            totalCost = totalCost * (1 - discountCard.getDiscount());
        }
        Cell discountDataCell = new Cell()
                .setTextAlignment(TextAlignment.RIGHT)
                .setBorder(Border.NO_BORDER)
                .add(new Paragraph(discount + "%"));
        Cell totalCostCell = new Cell()
                .setTextAlignment(TextAlignment.LEFT)
                .setBorder(Border.NO_BORDER)
                .add(new Paragraph(CheckUtil.TOTAL_COST));
        Cell totalCostDataCell = new Cell()
                .setTextAlignment(TextAlignment.RIGHT)
                .setBorder(Border.NO_BORDER)
                .add(new Paragraph(CheckUtil.priceToString(totalCost)));

        List<Cell> cells = List.of(costCell, costDataCell, discountCell,
                discountDataCell, totalCostCell, totalCostDataCell);
        cells.forEach(totalTable::addCell);
        return totalTable;
    }
}
