package org.bitbucket.yassinefarich.mgdown.shared;


public class Constants {

    //# URL expression parsing properties :
    public static String EXPRESSION_PATTERN = "\\[[0-9]+\\:[0-9]+\\|[0-9]+\\]";
    public static String START_PATTERN = "([0-9]+\\:)";
    public static String END_PATTERN = "(\\:[0-9]+)";
    public static String NUMBER_OF_DIGIT_PATTERN = "(\\|[0-9])";

    private Constants() {
    }

}
