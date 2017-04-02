package com.yfarich.mangasdownloader.functions.downloadfunction;

import com.google.common.base.Optional;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import static com.yfarich.mangasdownloader.shared.Constants.DEFAULT_ENCODING;

/**
 * Created by FARICH on 02/04/2017.
 */
public interface DownloadStrategy {

    Optional<String> findXPATHExpressionInPage(String pageURL);

    Boolean downloadFile(String url, File outputFileName);

    default String decodeURL(String stringURL) {

        try {
            return URLDecoder.decode(stringURL, DEFAULT_ENCODING);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return stringURL;
    }


}
