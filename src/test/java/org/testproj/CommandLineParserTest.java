package org.testproj;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.testproj.Check.CheckGenerator;
import org.testproj.Exceptions.DiscountCardAlreadyPresentedException;
import org.testproj.Models.Product;

import java.io.IOException;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
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