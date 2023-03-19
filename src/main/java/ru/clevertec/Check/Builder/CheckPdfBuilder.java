package ru.clevertec.Check.Builder;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Table;
import ru.clevertec.Check.Builder.Api.CheckBuilder;
import ru.clevertec.Check.CheckUtil;
import ru.clevertec.Model.DiscountCard;
import ru.clevertec.Model.Product;

import java.io.IOException;
import java.util.Map;

public class CheckPdfBuilder implements CheckBuilder {


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
        addHeader(document);
        addProductsToDocument(document, products);
        pdfDocument.close();
        return null;
    }

    private void addProductsToDocument(Document pdfDocument, Map<Product, Integer> products) {
        Table table2 = new Table(5);
        table2.addHeaderCell("QTY");
        table2.addHeaderCell("DESCRIPTION");
        table2.addHeaderCell("PROM.");
        table2.addHeaderCell("PRICE");
        table2.addHeaderCell("TOTAL");
        products.forEach((pr, qty) -> {
            double price = pr.getPrice() * qty * (pr.isPromotional() && (qty > 5) ? 0.9 : 1);
            table2.addCell(String.valueOf(qty))
                    .addCell(pr.getName())
                    .addCell(pr.isPromotional() ? "y" : "n")
                    .addCell(String.valueOf(Math.round(pr.getPrice() * 100.0) / 100.0))
                    .addCell(String.valueOf(Math.round(price * 100.0) / 100.0));
        });
        pdfDocument.add(table2);
    }

    private void addHeader(Document document) {
        Table headerTable = new Table(1);
        headerTable.addCell(CheckUtil.CASH_RECEIPT)
                .addCell(CheckUtil.SHOP_NAME)
                .addCell(CheckUtil.getCurrentDate())
                .addCell(CheckUtil.getCurrentTime())
                .addCell(CheckUtil.PROMOTION_CLAUSE);
        document.add(headerTable);
    }
}
