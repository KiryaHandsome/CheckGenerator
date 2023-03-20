package ru.clevertec.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.clevertec.Check.Builder.CheckPdfBuilder;
import ru.clevertec.Check.CheckGenerator;
import ru.clevertec.Check.CheckUtil;
import ru.clevertec.CommandLineParser;
import ru.clevertec.Exception.DiscountCardAlreadyPresentedException;
import ru.clevertec.Model.Check;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

@RestController
@RequestMapping("/check")
public class CheckController {
    final private CheckGenerator checkGenerator;

    @Autowired
    public CheckController(CheckGenerator checkGenerator) {
        this.checkGenerator = checkGenerator;
    }

    @GetMapping
    public String getCheck(@RequestParam Map<String, String> allParams) {
        String[] args = CheckUtil.parseParams(allParams);
        Map<Integer, Integer> info = CommandLineParser.parseArguments(args);
        String checkContent;
        try {
            checkContent = checkGenerator.generateCheck(info).toString();
            checkGenerator.saveCheckToFile(checkContent);
        } catch (DiscountCardAlreadyPresentedException | IOException e) {
            return e.getMessage();
        }
        return checkContent;
    }

    @GetMapping(value = "/pdf", produces = "application/pdf")
    public ResponseEntity<byte[]> getCheckPdf(@RequestParam Map<String, String> allParams) throws IOException {
        String[] args = CheckUtil.parseParams(allParams);
        Map<Integer, Integer> info = CommandLineParser.parseArguments(args);
        Check check;
        try {
            check = checkGenerator.generateCheck(info);
        } catch (DiscountCardAlreadyPresentedException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        CheckPdfBuilder pdfBuilder = new CheckPdfBuilder();
        String filePath = pdfBuilder.buildCheck(check.getProducts(), check.getDiscountCard());
        byte[] content = Files.readAllBytes(Path.of(filePath));
        return new ResponseEntity<>(content, HttpStatus.OK);
    }
}
