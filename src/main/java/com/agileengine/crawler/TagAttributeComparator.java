package com.agileengine.crawler;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static com.agileengine.crawler.extractor.TagPathExtractor.TAG_SEPARATOR;

public class TagAttributeComparator {
    public static final String PATH_ATTRIBUTE_KEY = "path";

    private static final String ATTRIBUTE_ID_KEY = "id";
    // id uniquely identifies the object
    private static final Integer ATTRIBUTE_ID_SOLVE_FUNCTION_VALUE = 100;

    private static final String ATTRIBUTE_CLASS_KEY = "class";

    private static final String CLASS_ATTRIBUTE_SEPARATOR = "\\s+";
    private static final Integer DEFAULT_ATTRIBUTE_SOLVE_FUNCTION_VALUE = 1;

    Integer compareAttribute(String key, String value, String elementAttributeValue) {
        // todo Factory
        switch (key) {
            case ATTRIBUTE_ID_KEY:
                return compareAsId(value, elementAttributeValue);
            case ATTRIBUTE_CLASS_KEY:
                return compareAsClass(value, elementAttributeValue);
            case PATH_ATTRIBUTE_KEY:
                return compareAsPath(value, elementAttributeValue);
            default:
                return compareSimple(elementAttributeValue, value);
        }
    }

    private Integer compareAsId(String value, String elementAttributeValue) {
        return elementAttributeValue.equals(value) ? ATTRIBUTE_ID_SOLVE_FUNCTION_VALUE : 0;
    }

    // comparing each class
    private Integer compareAsClass(String value, String elementAttributeValue) {
        String[] currentElementClasses = value.split(CLASS_ATTRIBUTE_SEPARATOR);
        String[] searchElementClasses = elementAttributeValue.split(CLASS_ATTRIBUTE_SEPARATOR);

        Integer equalElementsCount = getEqualElementsCount(currentElementClasses, searchElementClasses);

        return equalElementsCount * DEFAULT_ATTRIBUTE_SOLVE_FUNCTION_VALUE;
    }

    private Integer getEqualElementsCount(String[] currentElementClasses, String[] searchElementClasses) {
        Integer countEquals = 0;

        Set<String> currentElementClassesWithoutDuplicates = new HashSet<>(Arrays.asList(currentElementClasses));
        Set<String> searchElementClassesSet = new HashSet<>(Arrays.asList(searchElementClasses));

        for (String clazz : currentElementClassesWithoutDuplicates) {
            if (searchElementClassesSet.contains(clazz)) {
                countEquals++;
            }
        }

        return countEquals;
    }

    // comparing each tag
    private Integer compareAsPath(String value, String elementAttributeValue) {
        String commonPath = findLongestCommonPrefix(value, elementAttributeValue);

        String normalized = normalize(commonPath);

        return normalized.split(TAG_SEPARATOR).length;
    }

    private String findLongestCommonPrefix(String str1, String str2) {
        int minLength = Math.min(str1.length(), str2.length());
        for (int i = 0; i < minLength; i++) {
            if (str1.charAt(i) != str2.charAt(i)) {
                return str1.substring(0, i);
            }
        }
        return str1.substring(0, minLength);
    }

    private String normalize(String str) {
        if (str.endsWith("[")) {
            int indexOfLastSeparator = str.lastIndexOf(TAG_SEPARATOR);
            if (indexOfLastSeparator > 0) {
                return str.substring(0, indexOfLastSeparator);
            }
        }

        return str;
    }

    private Integer compareSimple(String elementAttributeValue, String value) {
        return elementAttributeValue.equals(value) ? DEFAULT_ATTRIBUTE_SOLVE_FUNCTION_VALUE : 0;
    }
}
