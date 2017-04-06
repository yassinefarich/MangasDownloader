package com.yfarich.mangasdownloader.functions.downloadfunction;

import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.inject.Inject;
import com.yfarich.mangasdownloader.shared.RunningParameters;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;
import org.htmlcleaner.XPatherException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import static com.yfarich.mangasdownloader.functions.downloadfunction.Constants.DEFAULT_USER_AGENT;
import static com.yfarich.mangasdownloader.shared.RunningParameters.PARSER_XPATH;

/**
 * Created by FARICH on 02/04/2017.
 */
public class SimpleDownload implements DownloadStrategy {

    private static final Logger LOGGER = LogManager.getLogger(SimpleDownload.class);

    @Inject
    private RunningParameters runningParameters;

    @Override
    public Boolean downloadFile(String url, File outputFileName) {

        try (FileOutputStream downloadedFileOS = new FileOutputStream(outputFileName)) {
            URL downloadURL = new URL(url);
            URLConnection downloadUrlConnexion = downloadURL.openConnection();
            downloadUrlConnexion.setRequestProperty("User-Agent", DEFAULT_USER_AGENT);
            IOUtils.copy(downloadUrlConnexion.getInputStream(), downloadedFileOS);
            return true;

        } catch (java.io.FileNotFoundException ddn) {
            LOGGER.error("404 URL non valide" + ddn.getMessage());
            return false;
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
            return false;
        }

    }

    @Override
    public Optional<String> findXPATHExpressionInPage(String pageURL) {

        Preconditions.checkArgument(!StringUtils.isEmpty(pageURL));
        try {
            URLConnection urlConnection = new URL(decodeURL(pageURL))
                    .openConnection();

            urlConnection.setRequestProperty("User-Agent", DEFAULT_USER_AGENT);
            TagNode tagNode = new HtmlCleaner().clean(new InputStreamReader(urlConnection.getInputStream()));

            Optional<String> fullDownloadPath = Optional.fromJavaUtil(
                    Lists.newArrayList(tagNode.evaluateXPath(runningParameters.getProperty(PARSER_XPATH).get()))
                            .stream()
                            .map(Object::toString)
                            .findFirst()

            );


            return fullDownloadPath;

        } catch (IOException e) {
            e.printStackTrace();
        } catch (XPatherException e) {
            e.printStackTrace();
        }
        return Optional.absent();

    }
}
