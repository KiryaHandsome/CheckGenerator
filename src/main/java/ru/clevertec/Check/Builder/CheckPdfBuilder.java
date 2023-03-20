package ru.clevertec.Check.Builder;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.HorizontalAlignment;
import com.itextpdf.layout.properties.TextAlignment;
import ru.clevertec.Check.Builder.Api.CheckBuilder;
import ru.clevertec.Check.CheckUtil;
import ru.clevertec.Model.DiscountCard;
import ru.clevertec.Model.Product;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class CheckPdfBuilder implements CheckBuilder {
    private static final int PADDING = 5;

    public CheckPdfBuilder() {

    }

    @Override
    public Object buildCheck(Map<Product, Integer> products, DiscountCard discountCard) {
        String templatePath = "src/main/resources/pdf/Clevertec_template.pdf";
        String outputPath = "src/main/resources/pdf/check.pdf";
        PdfDocument pdfDocument;
        Document document;
        try {
            pdfDocument = new PdfDocument(new PdfReader(templatePath), new PdfWriter(outputPath));
            document = new Document(pdfDocument);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        document.add(new Paragraph("\n".repeat(PADDING)));


        List<Cell> headerCells = getHeaderCells();
        Table productsTable = getProductsTable(products);

        Table resultTable = new Table(1);
        resultTable.setHorizontalAlignment(HorizontalAlignment.CENTER);
        headerCells.forEach(resultTable::addCell);

        resultTable.addCell(productsTable);

        document.add(resultTable);
        document.close();
        return null;
    }

    private Table getProductsTable(Map<Product, Integer> products) {
        Table table2 = new Table(5);
        table2.addHeaderCell("QTY")
                .addHeaderCell("DESCRIPTION")
                .addHeaderCell("PROM.")
                .addHeaderCell("PRICE")
                .addHeaderCell("TOTAL");
        for (Map.Entry<Product, Integer> entry : products.entrySet()) {
            Product pr = entry.getKey();
            int qty = entry.getValue();
            Cell qtyCell = new Cell();
            qtyCell.add(new Paragraph(String.valueOf(qty)));

            Cell nameCell = new Cell();
            nameCell.add(new Paragraph(pr.getName()));

            Cell promCell = new Cell().setTextAlignment(TextAlignment.CENTER);
            promCell.add(new Paragraph(pr.isPromotional() ? "y" : "n"));

            Cell priceCell = new Cell().setTextAlignment(TextAlignment.RIGHT);
            priceCell.add(new Paragraph(CheckUtil.getRoundedPrice(pr.getPrice())));

            Cell promPriceCell = new Cell().setTextAlignment(TextAlignment.RIGHT);
            promPriceCell.add(new Paragraph(CheckUtil.getPromotionalPrice(pr, qty)));

            List.of(qtyCell, nameCell, promCell, priceCell, promPriceCell)
                    .forEach(table2::addCell);
        }
        return table2;
    }

    private List<Cell> getHeaderCells() {
        Cell cashReceiptCell = new Cell().setTextAlignment(TextAlignment.CENTER);
        cashReceiptCell.add(new Paragraph(CheckUtil.CASH_RECEIPT));

        Cell shopNameCell = new Cell().setTextAlignment(TextAlignment.CENTER);
        shopNameCell.add(new Paragraph(CheckUtil.SHOP_NAME));

        Cell dateCell = new Cell().setTextAlignment(TextAlignment.RIGHT);
        dateCell.add(new Paragraph(CheckUtil.getCurrentDate()));

        Cell timeCell = new Cell().setTextAlignment(TextAlignment.RIGHT);
        timeCell.add(new Paragraph(CheckUtil.getCurrentTime()));

        Cell promotionCell = new Cell();
        promotionCell.add(new Paragraph(CheckUtil.PROMOTION_CLAUSE));

        return List.of(cashReceiptCell, shopNameCell, dateCell, timeCell, promotionCell);
    }
}
