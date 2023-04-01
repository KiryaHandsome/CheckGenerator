package ru.clevertec.Check;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.clevertec.CommandLineParser;
import ru.clevertec.Exception.DiscountCardAlreadyPresentedException;
import ru.clevertec.Model.DiscountCard;
import ru.clevertec.Model.Product;
import ru.clevertec.Service.Implementation.DiscountCardService;
import ru.clevertec.Service.Implementation.ProductService;

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
    void checkGenerateCheckShouldContainInfoAboutProduct() throws DiscountCardAlreadyPresentedException {
        Map<Integer, Integer> inputMap = new HashMap<>();
        inputMap.put(11, 11);
        when(productServiceMock.find(11)).thenReturn(new Product(11, "ProdName", 1, true));
        String[] result = checkGenerator.generateCheck(inputMap).toString().split("\n");

        assertThat(Arrays.stream(result).filter(s -> s.contains("ProdName")
                && s.contains("1")
                && s.contains(" y ")
                && s.contains("11"))
                .count()).isEqualTo(1);
    }
}
