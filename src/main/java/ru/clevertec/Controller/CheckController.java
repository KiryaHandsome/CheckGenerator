package ru.clevertec.Controller;

import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CheckController {

    private final CheckGenerator checkGenerator;

    @GetMapping
    public ResponseEntity<String> getCheck(@RequestParam Map<String, String> allParams) {
        String checkContent;
        try {
            String[] args = CheckUtil.parseParams(allParams);
            Map<Integer, Integer> info = CommandLineParser.parseArguments(args);
            checkContent = checkGenerator.generateCheck(info).toString();
            checkGenerator.saveCheckToFile(checkContent);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(checkContent, HttpStatus.OK);
    }

    /**
     * @return pdf document of check built from parameters
     */
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
