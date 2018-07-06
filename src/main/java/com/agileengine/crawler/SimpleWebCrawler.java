package com.agileengine.crawler;

import com.agileengine.crawler.extractor.TagAttributesExtractor;
import com.agileengine.parser.HtmlFileParser;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class SimpleWebCrawler {
    private static final Logger LOGGER = LoggerFactory.getLogger(SimpleWebCrawler.class);

    private final TagAttributesExtractor tagAttributesExtractor = new TagAttributesExtractor();
    private final SolveFunctionCalculator solveFunctionCalculator = new SolveFunctionCalculator();

    private final Map<String, Integer> solveFunctions = new HashMap<>();

    public Optional<String> findElementWithIdInChangedFile(String elementId, File originalFile, File testFile) {
        Optional<Element> elementToFind = findElementById(originalFile, elementId);

        if (!elementToFind.isPresent()) {
            LOGGER.error("Can't find element with id {} in file {}", elementId, originalFile.getName());
            return Optional.empty();
        }

        elementToFind.ifPresent(element -> {
            Map<String, String> elementAttributes = tagAttributesExtractor.extractSignificantAttributes(element);
            LOGGER.info("Searching element with attributes {} in file {}", elementAttributes, testFile.getName());

            Map<String, Map<String, String>> allElementsWithAttributes = tagAttributesExtractor.getAllElementsWithAttributes(testFile);

            processAllElements(elementAttributes, allElementsWithAttributes);
        });

        return getKeyWithMaxSolveFunctionValue();
    }

    private Optional<Element> findElementById(File htmlFile, String targetElementId) {
        Optional<Document> documentOpt = HtmlFileParser.parse(htmlFile);

        return documentOpt.map(document -> document.getElementById(targetElementId));
    }

    private void processAllElements(Map<String, String> elementAttributes,
                                    Map<String, Map<String, String>> allElementsWithAttributes) {

        allElementsWithAttributes.forEach((path, attrs) -> {
            Integer solveFunction = solveFunctionCalculator.calculateSolveFunction(attrs, elementAttributes);
            LOGGER.debug("Solve function for element {} is {}", path, solveFunction);
            solveFunctions.put(path, solveFunction);
        });
    }


    private Optional<String> getKeyWithMaxSolveFunctionValue() {
        if (solveFunctions.isEmpty()) {
            LOGGER.warn("Solve functions map is empty.");
            return Optional.empty();
        }

        return solveFunctions.entrySet().stream()
                .max((entry1, entry2) -> entry1.getValue() > entry2.getValue() ? 1 : -1)
                .map(Map.Entry::getKey);
    }
}
