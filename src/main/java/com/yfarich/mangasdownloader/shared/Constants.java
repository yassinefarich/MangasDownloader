package com.yfarich.mangasdownloader.shared;


public class Constants {

    //# URL expression parsing properties :
    public static final String EXPRESSION_PATTERN = "\\[[0-9]+\\:[0-9]+\\|[0-9]+\\]";
    public static final String START_PATTERN = "([0-9]+\\:)";
    public static final String END_PATTERN = "(\\:[0-9]+)";
    public static final String NUMBER_OF_DIGIT_PATTERN = "(\\|[0-9])";
    public static final String DEFAULT_IMAGE_EXTENSION = ".jpg";
    public static final int STRING_LEFT_PAD = 2;
    public static final String CONFIGURAION_FILE = "config.properties";
    public static final String DEFAULT_ENCODING = "UTF-8";
    public static final String DOWNLOAD_STRATEGY = "DOWNLOAD_STRATEGY";
    public static final String CLOUD_FLARE_STRATEGY = "CLOUD_FLARE_STRATEGY";

    private Constants() {
    }

}
