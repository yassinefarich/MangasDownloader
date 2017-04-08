package com.yfarich.mangasdownloader.url.generator;

import com.google.common.collect.Lists;
import com.yfarich.mangasdownloader.url.MangaPage;
import org.junit.Test;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


/**
 * Created by FARICH on 08/04/2017.
 */
public class URLsGeneratorTest {

    private static final String TEST_STRING = "Seq_[1:3|1]_[1:3|1]_[1:30|3]";

    @Test
    public void generateUrlsFromExpression() throws Exception {

        List<MangaPage> pages = new URLsGenerator().generateUrlsFromExpression(TEST_STRING);
        List<String> pagesNumbers = pages.stream().map(Object::toString).collect(Collectors.toList());

        assertTrue(pagesNumbers.containsAll(Lists.newArrayList("Seq_3_3_030",
                "Seq_3_2_030", "Seq_3_3_001", "Seq_3_1_030", "Seq_2_2_030",
                "Seq_2_3_001", "Seq_1_1_001", "Seq_3_3_030", "Seq_3_2_001")));

        assertEquals(pages.size(), (3 * 3 * 30));

    }
}