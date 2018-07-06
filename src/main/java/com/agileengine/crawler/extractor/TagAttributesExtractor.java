package com.agileengine.crawler.extractor;

import com.agileengine.parser.HtmlFileParser;
import org.jsoup.nodes.Attribute;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.agileengine.crawler.TagAttributeComparator.PATH_ATTRIBUTE_KEY;

public class TagAttributesExtractor {
    private static final Logger LOGGER = LoggerFactory.getLogger(TagAttributesExtractor.class);

    private final TagPathExtractor tagPathExtractor = new TagPathExtractor();

    public Map<String, String> extractSignificantAttributes(Element element) {
        Map<String, String> attributes = element.attributes().asList().stream()
                .collect(Collectors.toMap(Attribute::getKey, Attribute::getValue));

        String path = tagPathExtractor.extractPath(element);
        attributes.put(PATH_ATTRIBUTE_KEY, path);

        LOGGER.trace("Extracted attributes [{}]", attributes);
        return attributes;
    }

    public Map<String, Map<String, String>> getAllElementsWithAttributes(File htmlFile) {
        Map<String, Map<String, String>> attributes = new HashMap<>();

        Optional<Document> documentOpt = HtmlFileParser.parse(htmlFile);
        documentOpt.ifPresent(document -> {
            Element root = document.body();
            extractAttributes(attributes, root);
        });

        return attributes;
    }

    private void extractAttributes(Map<String, Map<String, String>> result, Element root) {
        Map<String, String> attributes = extractSignificantAttributes(root);
        result.put(attributes.get(PATH_ATTRIBUTE_KEY), attributes);

        root.children().forEach(element -> extractAttributes(result, element));
    }

}
