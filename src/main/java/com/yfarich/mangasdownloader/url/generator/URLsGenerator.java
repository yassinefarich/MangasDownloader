package com.yfarich.mangasdownloader.url.generator;

import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.primitives.Ints;
import com.yfarich.mangasdownloader.url.MangaPage;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class URLsGenerator {


    public List<MangaPage> generateUrlsFromExpression(String urlIncludingExpressions) {
        Preconditions.checkArgument(!Strings.isNullOrEmpty(urlIncludingExpressions));

        List<URLRange> ranges = extractRanges(urlIncludingExpressions);
        String urlWithNumericIdentifiers = replaceRangesWithNumericIdentifier(ranges, urlIncludingExpressions);

        return new SequencedStringsGenerator()
                .withRanges(ranges)
                .withUrlWithNumericIdentifiers(urlWithNumericIdentifiers)
                .generate();
    }

    private List<URLRange> extractRanges(String exPression) {

        Matcher m = Pattern.compile(EXPRESSION_PATTERN).matcher(exPression);

        List<URLRange> listSequences = Lists.newArrayList();
        while (m.find()) {

            String fullExpression = m.group();
            String minRangeValue = extractUsingRegex(fullExpression, START_PATTERN).get();
            String maxRangeValue = extractUsingRegex(fullExpression, END_PATTERN).get();
            String numberOfDigits = extractUsingRegex(fullExpression, NUMBER_OF_DIGIT_PATTERN).get();
            listSequences.add(new URLRange(fullExpression, Ints.tryParse(minRangeValue), Ints.tryParse(maxRangeValue), Ints.tryParse(numberOfDigits)));

        }

        return listSequences;
    }

    private String replaceRangesWithNumericIdentifier(List<URLRange> listeSequences, String exPression) {

        int index = 0;
        for (URLRange range : listeSequences) {
            exPression = range.replaceMeIn(exPression, identifierFromInteger(index++));
        }
        return exPression;
    }

    private String identifierFromInteger(Integer i) {
        return "[[#" + (i.toString()) + "#]]";
    }


    private Optional<String> extractUsingRegex(String data, String regex) {

        Pattern r = Pattern.compile(regex);
        Matcher m = r.matcher(data);

        if (m.find()) {
            return Optional.fromNullable(replaceNonNumeric(m.group()));
        }

        return Optional.absent();
    }

    private String replaceNonNumeric(String number) {
        return number.replaceAll("[^0-9.]", "");
    }


}
