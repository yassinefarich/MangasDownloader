package org.bitbucket.yassinefarich.mgdown.functions;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.bitbucket.yassinefarich.mgdown.functions.impl.SynchronizedList;
import org.bitbucket.yassinefarich.mgdown.shared.ApplicationProperties;
import org.bitbucket.yassinefarich.mgdown.url.MangaPage;
import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;
import org.htmlcleaner.XPatherException;
import org.springframework.util.CollectionUtils;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;

public class DownloadManager {

    public static final String DEFAULT_USER_AGENT = "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.4; en-US; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2";
    private ApplicationProperties applicationProperties = ApplicationProperties.getInstance();

    private SynchronizedList synchronizedList;

    private static final Logger LOGGER = LogManager.getLogger(DownloadManager.class.getName());

    public boolean download(final MangaPage mangaPage) {

        LOGGER.info("## URL scrapping ");
        String urlAfterScrapping = scrapePage(mangaPage.toString());

        LOGGER.info("Scarpping URL[" + urlAfterScrapping + "]");
        String fullDownloadURL = applicationProperties.getProperty(ApplicationProperties.DOWNLOAD_URL_MODIF).replace(
                "[DOWNLOAD_URL]", urlAfterScrapping);
        LOGGER.info("Full download URL[" + fullDownloadURL + "]");

        if ("".equalsIgnoreCase(urlAfterScrapping)) {
            return false;
        }

        StringBuilder diskDownloadPath = new StringBuilder(
                applicationProperties.getProperty(ApplicationProperties.DOWNLOAD_PATH));

        for (Integer folderHiarchy : dataProcess.getValue0()) {
            diskDownloadPath.append("/"
                    + StringUtils.leftPad(folderHiarchy.toString(), ApplicationProperties.STRING_LEFT_PAD, '0'));
        }

        diskDownloadPath.append(ApplicationProperties.DEFAULT_IMAGE_EXTENSION);

        LOGGER.info("Full download Path [" + diskDownloadPath.toString() + "]");

        File fileDiskDownloadPath = new File(diskDownloadPath.toString());

        if (!fileDiskDownloadPath.getParentFile().exists()) {
            fileDiskDownloadPath.getParentFile().mkdirs();
        }

        if ("".equals(fileDiskDownloadPath.toString())) {
            return false;
        }

        if (synchronizedList.hasBeenProcessed(MD5(fullDownloadURL))) {
            LOGGER.error("This file is been downloded for second time !!  ");
            return false;
        }

        boolean downloadState = false;

        int downloadAttempt = 0;

        while (!downloadState && downloadAttempt < ApplicationProperties.MAX_DOWNLOAD_ATTEMPTS) {
            downloadAttempt++;
            downloadState = downloadFile(fullDownloadURL, fileDiskDownloadPath.toString());
        }

        return downloadState;
    }


    private String findXPATHExpressionInPage(String pageURL) throws IOException, XPatherException {


        Preconditions.checkArgument(!StringUtils.isEmpty(pageURL));

        URLConnection urlConnection = new URL(decodeURL(pageURL))
                .openConnection();

        urlConnection.setRequestProperty("User-Agent", DEFAULT_USER_AGENT);
        TagNode tagNode = new HtmlCleaner().clean(new InputStreamReader(urlConnection.getInputStream()));

        CollectionUtils.arrayToList(tagNode
                .evaluateXPath(applicationProperties.getProperty(ApplicationProperties.PARSER_XPATH).get()))
                .stream().findFirst();
                ;

        String fullDownlodPath = "";
        for (int i = 0; i < info_nodes.length; i++) {
            fullDownlodPath = info_nodes[i].toString();
        }

        return fullDownlodPath;

    }

    private String decodeURL(String stringURL) {

        try {
            return URLDecoder.decode(stringURL, ApplicationProperties.DEFAULT_ENCODING);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return stringURL;
    }

    /**
     * Download file.
     *
     * @param url            the url
     * @param outputFileName the output file name
     * @return true, if successful
     */
    public boolean downloadFile(String url, final String outputFileName) {

        url = decodeURL(url);

        URL downloadURL = null;
        FileOutputStream downloadedFileOS = null;
        try {

            downloadURL = new URL(url);
            URLConnection downloadUrlConnexion = downloadURL.openConnection();
            downloadUrlConnexion.setRequestProperty("User-Agent",
                    "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.4; en-US; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2");
            downloadedFileOS = new FileOutputStream(outputFileName);

            IOUtils.copy(downloadUrlConnexion.getInputStream(), downloadedFileOS);
            IOUtils.closeQuietly(downloadedFileOS);
            return true;
        } catch (java.io.FileNotFoundException ddn) {
            LOGGER.error("404 URL non valide" + ddn.getMessage());
            return false;
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
            return false;
        }

    }

    /**
     * Md5.
     *
     * @param md5 the md 5
     * @return the string
     */
    public String MD5(final String md5) {
        try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
            byte[] array = md.digest(md5.getBytes());
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < array.length; ++i) {
                sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1, 3));
            }
            return sb.toString();
        } catch (java.security.NoSuchAlgorithmException e) {
        }
        return null;
    }

}
