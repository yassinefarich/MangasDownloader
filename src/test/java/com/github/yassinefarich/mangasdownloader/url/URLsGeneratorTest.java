package com.github.yassinefarich.mangasdownloader.url;

import com.github.yassinefarich.mangasdownloader.url.generator.URLsGenerator;
import org.junit.Test;

/**
 * Created by FARICH on 25/03/2017.
 */
public class URLsGeneratorTest {

    private static final String TEST_STRING = "http://www.lirescan.net_[1:3|1]/aiki-s-lecture-en-ligne/[1:3|1]/[1:30|1]";

    @Test
    public void generateUrlsFromExpression() throws Exception {

        new URLsGenerator().generateUrlsFromExpression(TEST_STRING).forEach(System.out::println);


    }

}