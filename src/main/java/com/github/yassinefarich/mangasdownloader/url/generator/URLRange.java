package com.github.yassinefarich.mangasdownloader.url.generator;

import com.google.common.base.Strings;

import java.util.function.IntConsumer;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

/**
 * Created by farich on 25/03/17.
 */
public class URLRange {

    private int beginning;
    private int end;
    private int numberOfDigits;
    private String expression;


    public URLRange(String fullExpression, int beginning, int end, int numberOfDigits) {
        this.beginning = beginning;
        this.end = end;
        this.numberOfDigits = numberOfDigits;
        this.expression = fullExpression;
    }

    public String replaceMeIn(String in, String with) {
        return in.replaceFirst(Pattern.quote(expression), with);
    }


    public void loopOverRange(IntConsumer loopFunction) {
        IntStream.rangeClosed(beginning, end).forEach(loopFunction);
    }

    public String setMyDigitsNumberTo(Integer number) {
        return Strings.padStart(String.valueOf(number), numberOfDigits, '0');
    }
}
