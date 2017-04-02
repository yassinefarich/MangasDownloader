package com.yfarich.mangasdownloader.functions.downloadfunction;

import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.inject.Inject;
import com.yfarich.mangasdownloader.functions.downloadfunction.http.HTTPDownloadResponse;
import com.yfarich.mangasdownloader.functions.downloadfunction.http.HTTPResponse;
import com.yfarich.mangasdownloader.functions.downloadfunction.http.HTTPUnitRequest;
import com.yfarich.mangasdownloader.shared.RunningParameters;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;
import org.htmlcleaner.XPatherException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static com.yfarich.mangasdownloader.shared.Constants.PARSER_XPATH;

/**
 * Created by FARICH on 02/04/2017.
 */
public class CloudFlareDownload implements DownloadStrategy {


    @Inject
    private RunningParameters runningParameters;

    @Inject
    private SynchronizedList synchronizedList;

    private HTTPUnitRequest httpUnitRequest = new HTTPUnitRequest();
    private Map<String, String> cookies = new HashMap<>();
    private Map<String, String> params = new HashMap<>();

    private static final Logger LOGGER = LogManager.getLogger(CloudFlareDownload.class);

    public CloudFlareDownload() {
        httpUnitRequest.initialize();
    }

    @Override
    public Optional<String> findXPATHExpressionInPage(String pageURL) {
        Preconditions.checkArgument(!Strings.isNullOrEmpty(pageURL));

        try {

            HTTPResponse response = httpUnitRequest.get(pageURL, cookies, params);
            TagNode tagNode = new HtmlCleaner().clean(response.getHtml());

            Optional<String> fullDownlodPath = Optional.fromJavaUtil(Lists.newArrayList(tagNode
                    .evaluateXPath(runningParameters.getProperty(PARSER_XPATH).get()))
                    .stream()
                    .map(Object::toString)
                    .findFirst());
            return fullDownlodPath;

        } catch (XPatherException e) {
            e.printStackTrace();
        }
        return Optional.absent();

    }

    @Override
    public Boolean downloadFile(String url, File outputFileName) {

        HTTPDownloadResponse response = httpUnitRequest.downloadFile(url, cookies);

        try (FileOutputStream downloadedFileOS = new FileOutputStream(outputFileName)) {
            IOUtils.copy(response.getStream(), downloadedFileOS);
            return true;
        } catch (FileNotFoundException ddn) {
            LOGGER.error("404 URL non valide" + ddn.getMessage());
            return false;
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
            return false;
        }

    }

}
