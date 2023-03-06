package ru.clevertec.Check;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.clevertec.Exceptions.DiscountCardAlreadyPresentedException;
import ru.clevertec.Models.DiscountCard;
import ru.clevertec.Models.Product;
import ru.clevertec.Services.Implementations.DiscountCardService;
import ru.clevertec.Services.Implementations.ProductService;

import java.security.InvalidParameterException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CheckGeneratorTest {

    @Mock
    private DiscountCardService cardServiceMock;

    @Mock
    private ProductService productServiceMock;

    @InjectMocks
    private CheckGenerator checkGenerator;


    @BeforeEach
    void setup() {
        checkGenerator = new CheckGenerator(productServiceMock, cardServiceMock);
    }

    @Test
    void checkParseArgumentsShouldReturnCorrectMap()
            throws DiscountCardAlreadyPresentedException {
        String[] arguments = {"12-5", "13-4", "90-1"};
        Map<Integer, Integer> expectedMap = new HashMap<>();
        expectedMap.put(12, 5);
        expectedMap.put(13, 4);
        expectedMap.put(90, 1);
        Map<Integer, Integer> actualMap = checkGenerator.parseArguments(arguments);
        assertThat(actualMap).isEqualTo(expectedMap);
    }

    @Test
    void checkParseArgumentsShouldReturnCorrectMapWithDiscountCard()
            throws DiscountCardAlreadyPresentedException {
        String[] arguments = {"12-5", "13-4", "90-1", "card-123"};
        Map<Integer, Integer> expectedMap = new HashMap<>();
        expectedMap.put(12, 5);
        expectedMap.put(13, 4);
        expectedMap.put(90, 1);
        Map<Integer, Integer> actualMap = checkGenerator.parseArguments(arguments);
        assertThat(actualMap).isEqualTo(expectedMap);
    }

    @Test
    void checkParseArgumentsShouldReturnCorrectMapWithRepeatedProduct()
            throws DiscountCardAlreadyPresentedException {
        String[] arguments = {"11-5", "11-4", "90-1", "card-123"};
        Map<Integer, Integer> expectedMap = new HashMap<>();
        expectedMap.put(11, 9);
        expectedMap.put(90, 1);
        Map<Integer, Integer> actualMap = checkGenerator.parseArguments(arguments);
        assertThat(actualMap).isEqualTo(expectedMap);
    }

    @ParameterizedTest
    @ValueSource(strings = {"12-5-3", "12", "12-", "%^&*(", "-"})
    void checkParseArgumentsWithInvalidParamsShouldThrowInvalidParameterException(String arg) {
        String[] arguments = {arg, "13-4", "90-1"};
        InvalidParameterException exception = assertThrows(InvalidParameterException.class,
                () -> checkGenerator.parseArguments(arguments));
        assertThat(exception.getMessage()).isEqualTo("Invalid argument in command line.");
    }

    @ParameterizedTest
    @ValueSource(strings = {"90-0", "0-90", "0-0"})
    void checkParseArgumentsWithIncorrectQtyShouldThrowInvalidParameterException(String arg) {
        String[] arguments = {"12-5", "13-4", arg};
        InvalidParameterException exception = assertThrows(InvalidParameterException.class,
                () -> checkGenerator.parseArguments(arguments));
        assertThat(exception.getMessage()).isEqualTo("Id and quantity must be greater than 0.");
    }

    @ParameterizedTest
    @ValueSource(strings = {"card-123", "card-11", "card-9"})
    void checkParseArgumentsShouldThrowDiscountCardAlreadyPresentedException(String arg) {
        String[] arguments = {"card-10", "12-5", "13-4", arg};
        when(cardServiceMock.find(anyInt())).thenReturn(new DiscountCard(12, 13));
        assertThrows(DiscountCardAlreadyPresentedException.class,
                () -> checkGenerator.parseArguments(arguments));
        verify(cardServiceMock).find(anyInt());
    }

    @Test
    void checkParseArgumentsShouldCallCardService()
            throws DiscountCardAlreadyPresentedException {
        when(cardServiceMock.find(anyInt())).thenReturn(new DiscountCard(12, 13));
        checkGenerator.parseArguments("11-1", "123-5", "card-123");
        verify(cardServiceMock).find(anyInt());
    }


    @Test
    void checkGetProductsFromDb() throws DiscountCardAlreadyPresentedException {
        Product product = new Product(12, "", 3.1, false);
        when(productServiceMock.find(12)).thenReturn(product);
        Map<Integer, Integer> inputMap = new HashMap<>();
        inputMap.put(12, 12);
        Map<Product, Integer> expectedResult = new HashMap<>();
        expectedResult.put(product, 12);

        Map<Product, Integer> actualResult = checkGenerator.getProductsFromDb(inputMap);

        verify(productServiceMock).find(12);
        assertThat(actualResult).isEqualTo(expectedResult);
    }

    @Test
    void checkGenerateCheckShouldContainHeaderLabels()
            throws DiscountCardAlreadyPresentedException {
        Map<Product, Integer> paramMap = checkGenerator.getProductsFromDb(new HashMap<>());

        String result = checkGenerator.generateCheck(paramMap);

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

    @Test
    void checkGenerateCheckShouldContainInfoAboutProduct() throws DiscountCardAlreadyPresentedException {
        Map<Integer, Integer> inputMap = new HashMap<>();
        inputMap.put(11, 11);
        when(productServiceMock.find(11)).thenReturn(new Product(11, "ProdName", 1, true));
        Map<Product, Integer> paramMap = checkGenerator.getProductsFromDb(inputMap);

        String[] result = checkGenerator.generateCheck(paramMap).split("\n");

        assertThat(Arrays.stream(result).filter(s -> s.contains("ProdName")
                && s.contains("1")
                && s.contains(" y ")
                && s.contains("11"))
                .count()).isEqualTo(1);
    }
}
