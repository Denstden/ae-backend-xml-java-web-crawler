package com.agileengine;

import com.agileengine.crawler.SimpleWebCrawler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.Optional;

/*
    Program should pass 2 or 3 program arguments: <input_origin_file_path> <input_other_sample_file_path> [<target_element_id>]
 */
public class WebCrawlerApplication {
    private static final Logger LOGGER = LoggerFactory.getLogger(WebCrawlerApplication.class);
    private static final String DEFAULT_TARGET_ELEMENT_ID = "make-everything-ok-button";

    public static void main(String[] args) {

        if (args == null || args.length < 2) {
            LOGGER.error("Program should pass at least 2 program arguments: <input_origin_file_path> <input_other_sample_file_path> [<target_element_id>]");
        } else {
            String targetElementId = DEFAULT_TARGET_ELEMENT_ID;
            if (args.length == 3) {
                targetElementId = args[2];
            }

            runCrawler(args[0], args[1], targetElementId);
        }
    }

    private static void runCrawler(String originFilePath, String testFilePath, String targetElementId) {
        File originalFile = new File(originFilePath);
        File testFile = new File(testFilePath);

        SimpleWebCrawler webCrawler = createWebCrawler();

        Optional<String> answer = webCrawler.findElementWithIdInChangedFile(targetElementId, originalFile, testFile);

        if (answer.isPresent()) {
            System.out.println(answer.get());
        } else {
            LOGGER.error("Can't find element with id {} in file {}", targetElementId, testFilePath);
        }
    }

    private static SimpleWebCrawler createWebCrawler() {
        return new SimpleWebCrawler();
    }
}