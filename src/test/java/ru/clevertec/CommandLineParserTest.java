package ru.clevertec;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.clevertec.Check.CheckGenerator;
import ru.clevertec.Exception.DiscountCardAlreadyPresentedException;

import java.io.IOException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CommandLineParserTest {

    @Mock
    private CheckGenerator checkGenerator;

    @InjectMocks
    private CommandLineParser commandLineParser;

    @Test
    void checkRunShouldCallParseArgumentsOnce()
            throws DiscountCardAlreadyPresentedException, IOException {
        commandLineParser.run("");
        verify(checkGenerator).parseArguments(any());
        verify(checkGenerator).getProductsFromDb(any());
        verify(checkGenerator).generateCheck(any());
        verify(checkGenerator).saveCheckToFile("check.txt");
    }
}