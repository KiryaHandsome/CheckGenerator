package ru.clevertec.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.clevertec.Model.Check;
import ru.clevertec.Check.CheckGenerator;
import ru.clevertec.CommandLineParser;
import ru.clevertec.Exception.DiscountCardAlreadyPresentedException;

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
        Check check;
        try {
            check = checkGenerator.generateCheck(info);
            //checkGenerator.saveCheckToFile("check.txt");
        } catch (DiscountCardAlreadyPresentedException e) {
            return e.getMessage();
        }
        return check.toString();
    }
}
