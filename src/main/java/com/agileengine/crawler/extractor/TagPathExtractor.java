package com.agileengine.crawler.extractor;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.Optional;

public class TagPathExtractor {
    public static final String TAG_SEPARATOR = " > ";

    String extractPath(Element element) {
        StringBuilder path = new StringBuilder();

        if (!element.hasParent()) {
            return element.tagName();
        }

        while (element.hasParent()) {
            path.insert(0, TAG_SEPARATOR + extract(element));
            element = element.parent();
        }

        return path.substring(TAG_SEPARATOR.length());
    }

    private String extract(Element element) {
        if (element.hasParent()) {
            Element parent = element.parent();

            Elements elementsByTag = findChildrenByTag(parent, element.tagName());

            Optional<Integer> indexOfElement = findIndexOfElement(elementsByTag, element);
            return indexOfElement.map(integer -> element.tagName() + "[" + integer + "]").orElseGet(element::tagName);
        }

        return element.tagName();
    }

    private Elements findChildrenByTag(Element parent, String tagName) {
        Elements children = parent.children();

        children.removeIf(child -> !tagName.equals(child.tagName()));

        return children;
    }

    private Optional<Integer> findIndexOfElement(Elements elementsByTag, Element element) {
        if (elementsByTag.size() > 1) {
            int i = 0;
            for (Element byTag : elementsByTag) {
                if (byTag.equals(element)) {
                    return Optional.of(i);
                }
                i++;
            }
        }

        return Optional.empty();
    }
}
