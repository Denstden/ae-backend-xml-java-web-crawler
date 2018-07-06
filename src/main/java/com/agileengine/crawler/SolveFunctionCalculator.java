package com.agileengine.crawler;

import java.util.Map;

public class SolveFunctionCalculator {

    private final TagAttributeComparator tagAttributeComparator = new TagAttributeComparator();

    public Integer calculateSolveFunction(Map<String, String> attrs, Map<String, String> searchElementAttributes) {
        Integer elementSolveFunctionValue = 0;

        for (Map.Entry<String, String> entry : attrs.entrySet()) {
            Integer attributeSolveFunction = calculateSolveFunctionForAttribute(entry.getKey(), entry.getValue(), searchElementAttributes);
            elementSolveFunctionValue += attributeSolveFunction;
        }

        return elementSolveFunctionValue;
    }

    private Integer calculateSolveFunctionForAttribute(String key, String value, Map<String, String> elementAttributes) {
        String elementAttributeValue = elementAttributes.get(key);

        if (elementAttributeValue == null) {
            return 0;
        }

        return tagAttributeComparator.compareAttribute(key, value, elementAttributeValue);
    }


}
