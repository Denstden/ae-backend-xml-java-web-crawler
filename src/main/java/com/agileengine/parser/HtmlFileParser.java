package com.agileengine.parser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

public class HtmlFileParser {
    private static final Logger LOGGER = LoggerFactory.getLogger(HtmlFileParser.class);
    private static final String CHARSET_NAME = "utf8";

    public static Optional<Document> parse(File htmlFile) {
        try {
            return Optional.of(Jsoup.parse(
                    htmlFile,
                    CHARSET_NAME,
                    htmlFile.getAbsolutePath()));
        } catch (IOException e) {
            LOGGER.error("Error reading [{}] file", htmlFile.getAbsolutePath(), e);
            return Optional.empty();
        }
    }
}
