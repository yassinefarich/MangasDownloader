package org.bitbucket.yassinefarich.mgdown.shared;


public class Constants {

    //# URL expression parsing properties :
    public static String EXPRESSION_PATTERN = "\\[[0-9]+\\:[0-9]+\\|[0-9]+\\]";
    public static String START_PATTERN = "([0-9]+\\:)";
    public static String END_PATTERN = "(\\:[0-9]+)";
    public static String NUMBER_OF_DIGIT_PATTERN = "(\\|[0-9])";

    public static final String DEFAULT_IMAGE_EXTENSION = ".jpg";
    public static final String DOWNLOAD_PATH = "DOWNLOAD_PATH";
    public static final String DOWNLOAD_URL = "DOWNLOAD_URL";
    public static final String DOWNLOAD_URL_MODIF = "DOWNLOAD_URL_MODIF";
    public static final int MAX_DOWNLOAD_ATTEMPTS = 10;
    public static final String NUMBER_OF_THREADS = "NUMBER_OF_THREADS";
    public static final String PARSER_XPATH = "PARSER_XPATH";
    public static final int STRING_LEFT_PAD = 2;
    public static final String USE_SCRAPPING = "USE_SCRAPPING";
    public static final String DEFAULT_ENCODING = "UTF-8";
    public static final String CONFIGURAION_FILE = "config.properties";

    private Constants() {
    }

}
