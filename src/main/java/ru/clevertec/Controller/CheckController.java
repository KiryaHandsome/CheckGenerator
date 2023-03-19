package ru.clevertec.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.clevertec.Model.Check;
import ru.clevertec.Check.CheckGenerator;
import ru.clevertec.CommandLineParser;
import ru.clevertec.Exception.DiscountCardAlreadyPresentedException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
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
        String[] args = allParams.entrySet()
                .stream()
                .map(e -> e.getKey() + "-" + e.getValue())
                .toArray(String[]::new);
        Map<Integer, Integer> info = CommandLineParser.parseArguments(args);
        String checkContent;
        try {
            checkContent = checkGenerator.generateCheck(info).toString();
            checkGenerator.saveCheckToFile("check.txt", checkContent);
        } catch (DiscountCardAlreadyPresentedException | IOException e) {
            return e.getMessage();
        }
        return checkContent;
    }

    @GetMapping("/pdf")
    public ResponseEntity<?> getCheckPdf() throws FileNotFoundException {
//        File file = new File("E:\\tmp\\test.pdf");
//        FileInputStream fileInputStream = new FileInputStream(file);
//        javax.ws.rs.core.Response.ResponseBuilder responseBuilder = javax.ws.rs.core.Response.ok((Object) fileInputStream);
//        responseBuilder.type("application/pdf");
//        responseBuilder.header("Content-Disposition", "filename=test.pdf");
//        return responseBuilder.build();
        return null;
    }
}
