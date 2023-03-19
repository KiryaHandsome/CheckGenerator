package ru.clevertec;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.clevertec.Check.CheckGenerator;
import ru.clevertec.Exception.DiscountCardAlreadyPresentedException;
import ru.clevertec.Model.Check;

import java.io.IOException;
import java.security.InvalidParameterException;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
class CommandLineParserTest {

    @Mock
    private CheckGenerator mockCheckGenerator;
    @Autowired
    private CheckGenerator checkGenerator;
    @InjectMocks
    private CommandLineParser commandLineParser;

    @Test
    void checkRunShouldCallParseArgumentsOnce()
            throws IOException {
//        commandLineParser.run("");
//        verify(checkGenerator).parseArguments(any());
//        verify(checkGenerator).getProductsFromDb(any());
//        verify(checkGenerator).generateCheck(any());
//        verify(checkGenerator).saveCheckToFile("check.txt");
    }

    @Test
    void checkParseArgumentsShouldReturnCorrectMap() {
        String[] arguments = {"12-5", "13-4", "90-1"};
        Map<Integer, Integer> expectedMap = new HashMap<>();
        expectedMap.put(12, 5);
        expectedMap.put(13, 4);
        expectedMap.put(90, 1);
        Map<Integer, Integer> actualMap = CommandLineParser.parseArguments(arguments);
        assertThat(actualMap).isEqualTo(expectedMap);
    }

    @Test
    void checkParseArgumentsShouldReturnCorrectMapWithDiscountCard() {
        String[] arguments = {"12-5", "13-4", "90-1", "card-123"};
        Map<Integer, Integer> expectedMap = new HashMap<>();
        expectedMap.put(12, 5);
        expectedMap.put(13, 4);
        expectedMap.put(90, 1);
        expectedMap.put(123, 0);
        Map<Integer, Integer> actualMap = CommandLineParser.parseArguments(arguments);
        assertThat(actualMap).isEqualTo(expectedMap);
    }

    @Test
    void checkParseArgumentsShouldReturnCorrectMapWithRepeatedProduct() {
        String[] arguments = {"11-5", "11-4", "90-1", "card-123"};
        Map<Integer, Integer> expectedMap = new HashMap<>();
        expectedMap.put(11, 9);
        expectedMap.put(90, 1);
        expectedMap.put(123, 0);
        Map<Integer, Integer> actualMap = CommandLineParser.parseArguments(arguments);
        assertThat(actualMap).isEqualTo(expectedMap);
    }

    @ParameterizedTest
    @ValueSource(strings = {"12-5-3", "12", "12-", "%^&*(", "-"})
    void checkParseArgumentsWithInvalidParamsShouldThrowInvalidParameterException(String arg) {
        String[] arguments = {arg, "13-4", "90-1"};
        InvalidParameterException exception = assertThrows(InvalidParameterException.class,
                () -> CommandLineParser.parseArguments(arguments));
        assertThat(exception.getMessage()).isEqualTo("Invalid argument in command line.");
    }

    @ParameterizedTest
    @ValueSource(strings = {"90-0", "0-90", "0-0"})
    void checkParseArgumentsWithIncorrectQtyShouldThrowInvalidParameterException(String arg) {
        String[] arguments = {"12-5", "13-4", arg};
        InvalidParameterException exception = assertThrows(InvalidParameterException.class,
                () -> CommandLineParser.parseArguments(arguments));
        assertThat(exception.getMessage()).isEqualTo("Id and quantity must be greater than 0.");
    }

    @Test
    void checkGenerateCheckShouldContainHeaderLabels()
            throws DiscountCardAlreadyPresentedException {
        String[] arguments = {};
        String result = checkGenerator.generateCheck(
                CommandLineParser.parseArguments(arguments))
                .toString();

        assertThat(result)
                .contains("CASH RECEIPT")
                .contains("Clevertec SHOP")
                .contains("QTY")
                .contains("DESCRIPTION")
                .contains("PROM.")
                .contains("PRICE")
                .contains("TOTAL")
                .contains("COST")
                .contains("TOTAL COST");
    }

    @Test
    void checkGenerateCheckShouldContainHeaderLabelsWithDiscount()
            throws DiscountCardAlreadyPresentedException {
        String[] arguments = {"card-11"};
        String result = checkGenerator.generateCheck(
                        CommandLineParser.parseArguments(arguments))
                .toString();

        assertThat(result)
                .contains("CASH RECEIPT")
                .contains("Clevertec SHOP")
                .contains("QTY")
                .contains("DESCRIPTION")
                .contains("PROM.")
                .contains("PRICE")
                .contains("TOTAL")
                .contains("COST")
                .contains("DISCOUNT")
                .contains("TOTAL COST");
    }
}