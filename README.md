# Simple web crawler

Implementation of simple web crawler that locates a user-selected element on a web site with frequently changing information.

# How to run
java -cp ae-backend-xml-java-snippets-0.0.1.jar <input_origin_file_path> <input_other_sample_file_path> [<target_element_id>]

# Implementation details:
For each xml element(tag) in the input file algorithm calculating solve functions based on element attributes. Also takes into account XPath of element. Element with the largest solve function value will be result.

Solve function for element is sum of solve functions of each attribute. 

For common attributes(excludes id, class, path) solve function is equal 1 if attributes are equal and 0 - else.

For "id" attribute solve function is equal 100 if attributes are equal and 0 else.

For "class" attribute solve function is calculating for every class(if 5 classes are equals - solve function will be 5).

For "path" pseudo-attribute solve function is calculating for every equal tag. For example, for pathes "html > body > div > div > div[2] > div[1] > div > div[1] > a" and "html > body > div > div > div[2] > div[0] > div > div[1] > a" solve function will be 5(5 first tags matches).