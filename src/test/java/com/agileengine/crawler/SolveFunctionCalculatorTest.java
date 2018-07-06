package com.agileengine.crawler;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class SolveFunctionCalculatorTest {

    private final SolveFunctionCalculator calculator = new SolveFunctionCalculator();

    @Test
    public void shouldCalculateSolveFunctionCorrectly() {
        // Given
        Map<String, String> currentElementParameters = new HashMap<String, String>() {{
            put("id", "123321");
            put("class", "class1 class2 class3 class1");
            put("path", "html > body > div > div > div[2] > div[1] > div > div[1] > a");
        }};

        Map<String, String> searchElementParameters = new HashMap<String, String>() {{
            put("id", "123321");
            put("class", "class1 class3 class9 class8 class3");
            put("path", "html > body > div > div > div[2] > div[0] > div > div[1] > a");
        }};

        // When
        Integer solveFunction = calculator.calculateSolveFunction(currentElementParameters, searchElementParameters);

        // Then
        Integer expectedSolveFunctionValue = 107;
        assertEquals(expectedSolveFunctionValue, solveFunction);
    }
}