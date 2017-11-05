package com.yfarich.mangasdownloader.download;

import com.google.common.base.Optional;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import org.springframework.stereotype.Component;

import static com.yfarich.mangasdownloader.shared.Constants.DEFAULT_ENCODING;

/**
 * Created by FARICH on 02/04/2017.
 */
@Component
public interface DownloadManagerStrategy {

    public static final String DEFAULT_USER_AGENT = "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.4; en-US; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2";
    public static final String DOWNLOAD_URL_KEY = "[DOWNLOAD_URL]";
    public static final String DEFAULT_FOLDER_SEPARATOR = "/";
    public static final int MAX_DOWNLOAD_ATTEMPTS = 10;
    
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
