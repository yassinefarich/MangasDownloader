package com.yfarich.mangasdownloader.functions.impl.downloadfunction;

import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.google.common.io.Files;
import com.google.inject.Inject;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import com.yfarich.mangasdownloader.shared.RunningParameters;
import com.yfarich.mangasdownloader.url.MangaPage;
import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;
import org.htmlcleaner.XPatherException;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.util.List;

public class DownloadManager {

    @Inject
    private RunningParameters runningParameters;

    private SynchronizedList synchronizedList = new SynchronizedList();

    private static final Logger LOGGER = LogManager.getLogger(DownloadManager.class.getName());

    public boolean download(final MangaPage mangaPage) {
        Preconditions.checkNotNull(mangaPage);
        LOGGER.debug(new StringBuilder("Star getting from url :").append(mangaPage));

        Optional<String> mangasImageURL = findXPATHExpressionInPage(mangaPage.getPageUrl());
        Preconditions.checkState(mangasImageURL.isPresent());
        Preconditions.checkState(thisUrlHasNotBeenProcessedBefore(mangasImageURL), "This file has been downloader before !");

        mangasImageURL = applyModificationOn(mangasImageURL);
        Preconditions.checkState(mangasImageURL.isPresent());

        Optional<File> downloadFile = makeDownloadDestination(mangaPage.getChaptersPageSequnce());
        Preconditions.checkState(downloadFile.isPresent());

        return downloadImage(mangasImageURL, downloadFile);
    }


    private boolean downloadImage(Optional<String> mangasImageURL, Optional<File> downloadFile) {
        makeDirectoriesIfNotExists(downloadFile);
        LOGGER.debug(new StringBuilder("Downloading file :").append(mangasImageURL.get()).append(" into : ").append(downloadFile.get()).toString());

        boolean hasBeenDownloaded = false;
        int downloadAttempt = 0;

        while (!hasBeenDownloaded && downloadAttempt++ < MAX_DOWNLOAD_ATTEMPTS) {
            hasBeenDownloaded = downloadFile(mangasImageURL.get(), downloadFile.get());
        }

        return hasBeenDownloaded;


    }

    private void makeDirectoriesIfNotExists(Optional<File> downloadFile) {
        try {
            Files.createParentDirs(downloadFile.get());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean thisUrlHasNotBeenProcessedBefore(Optional<String> mangasImageURL) {
        return !synchronizedList.hasBeenProcessed(MD5(mangasImageURL.get()));
    }

    private Optional<File> makeDownloadDestination(List<Integer> chaptersPageSequnce) {

        StringBuilder downloadFolder = new StringBuilder(runningParameters.getProperty(DOWNLOAD_PATH).get());

        chaptersPageSequnce.forEach(
                number ->
                {
                    downloadFolder.append(DEFAULT_FOLDER_SEPARATOR);
                    downloadFolder.append(Strings.padStart(number.toString(), STRING_LEFT_PAD, '0'));
                }
        );

        downloadFolder.append(DEFAULT_IMAGE_EXTENSION);
        return Optional.fromNullable(new File(downloadFolder.toString()));
    }

    private Optional<String> applyModificationOn(Optional<String> mangasImageURL) {
        String modificationTemplate = runningParameters.getProperty(DOWNLOAD_URL_MODIF).get();
        String modifiedDownloadUrl = modificationTemplate.replace(DOWNLOAD_URL_KEY, mangasImageURL.get());
        return !StringUtils.isEmpty(modifiedDownloadUrl) ? Optional.fromNullable(modifiedDownloadUrl) : Optional.absent();
    }


    private Optional<String> findXPATHExpressionInPage(String pageURL) {

        Preconditions.checkArgument(!StringUtils.isEmpty(pageURL));
        try {
            URLConnection urlConnection = new URL(decodeURL(pageURL))
                    .openConnection();

            urlConnection.setRequestProperty("User-Agent", DEFAULT_USER_AGENT);
            TagNode tagNode = new HtmlCleaner().clean(new InputStreamReader(urlConnection.getInputStream()));

            Optional<String> fullDownlodPath = Optional.fromJavaUtil(CollectionUtils.arrayToList(tagNode
                    .evaluateXPath(runningParameters.getProperty(PARSER_XPATH).get()))
                    .stream().findFirst());
            return fullDownlodPath;

        } catch (IOException e) {
            e.printStackTrace();
        } catch (XPatherException e) {
            e.printStackTrace();
        }
        return Optional.absent();

    }

    private String decodeURL(String stringURL) {

        try {
            return URLDecoder.decode(stringURL, DEFAULT_ENCODING);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return stringURL;
    }


    public boolean downloadFile(String url, File outputFileName) {
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
