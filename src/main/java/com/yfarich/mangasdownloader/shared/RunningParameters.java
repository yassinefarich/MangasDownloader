package com.yfarich.mangasdownloader.shared;

import com.google.common.base.Optional;
import com.google.common.base.Preconditions;

/**
 * Created by FARICH on 01/04/2017.
 */
public interface RunningParameters {

    String DOWNLOAD_PATH = "DOWNLOAD_PATH";
    String DOWNLOAD_URL_MODIF = "DOWNLOAD_URL_MODIF";
    String PARSER_XPATH = "PARSER_XPATH";
    String NUMBER_OF_THREADS = "NUMBER_OF_THREADS";
    String USE_SCRAPPING = "USE_SCRAPPING";
    String DOWNLOAD_URL = "DOWNLOAD_URL";
    Optional<String> getProperty(String propertyName);

}

