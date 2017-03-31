package com.github.yassinefarich.mangasdownloader.url.generator;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.github.yassinefarich.mangasdownloader.url.MangaPage;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * Created by farich on 25/03/17.
 */
public class SequencedStringsGenerator {

    private List<URLRange> ranges;
    private List<MangaPage> pagesBufferAccumulator = Lists.newArrayList();
    private String urlWithNumericIdentifiers;


    public SequencedStringsGenerator withRanges(List<URLRange> ranges) {
        Preconditions.checkArgument(!CollectionUtils.isEmpty(ranges));
        this.ranges = ranges;
        return this;

    }

    public SequencedStringsGenerator withUrlWithNumericIdentifiers(String urlWithNumericIdentifiers) {
        Preconditions.checkArgument(!Strings.isNullOrEmpty(urlWithNumericIdentifiers));

        this.urlWithNumericIdentifiers = urlWithNumericIdentifiers;
        return this;
    }

    public List<MangaPage> generate() {
        generateTuples(0, Lists.newArrayList(), urlWithNumericIdentifiers);
        return pagesBufferAccumulator;
    }


    private void generateTuples(int currentLevel, List<Integer> values, String exPression) {

        int maxLevel = ranges.size();

        if (currentLevel < maxLevel) {

            ranges.get(currentLevel).loopOverRange(i ->
                    {
                        List<Integer> replacementList = Lists.newArrayList(values);
                        replacementList.add(i);
                        generateTuples(currentLevel + 1, replacementList, exPression);
                    }
            );

        } else {
            pagesBufferAccumulator.add(new MangaPage(values, fillIdentifierWithValues(values, exPression)));
        }

    }

    private String fillIdentifierWithValues(
            List<Integer> values, String exPression) {

        String newExpression = exPression;

        int index = 0;
        for (Integer val : values) {
            String stringValue = ranges.get(index).setMyDigitsNumberTo(val);
            newExpression = newExpression.replace(identifierFromInteger(index++), stringValue);
        }

        return newExpression;
    }

    private String identifierFromInteger(Integer i) {

        return "[[#" + (i.toString()) + "#]]";
    }


}
