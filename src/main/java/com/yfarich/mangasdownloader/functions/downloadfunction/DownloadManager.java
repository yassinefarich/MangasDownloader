package com.yfarich.mangasdownloader.functions.downloadfunction;

import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.google.common.io.Files;
import com.google.inject.Inject;
import com.yfarich.mangasdownloader.shared.RunningParameters;
import com.yfarich.mangasdownloader.url.MangaPage;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static com.yfarich.mangasdownloader.functions.downloadfunction.Constants.*;
import static com.yfarich.mangasdownloader.shared.RunningParameters.*;
import static com.yfarich.mangasdownloader.shared.Constants.*;

class DownloadManager {

    @Inject
    private RunningParameters runningParameters;

    @Inject
    private SynchronizedList synchronizedList ;

    @Inject
    private DownloadStrategy download;

    private static final Logger LOGGER = LogManager.getLogger(DownloadManager.class.getName());

    boolean download(final MangaPage mangaPage) {
        Preconditions.checkNotNull(mangaPage);
        LOGGER.debug(new StringBuilder("Star getting from url :").append(mangaPage));

        Optional<String> mangasImageURL = download.findXPATHExpressionInPage(mangaPage.getPageUrl());
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
             hasBeenDownloaded = download.downloadFile(mangasImageURL.get(), downloadFile.get());
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

    private String MD5(final String md5) {
        try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
            byte[] array = md.digest(md5.getBytes());
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < array.length; ++i) {
                sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1, 3));
            }
            return sb.toString();
        } catch (java.security.NoSuchAlgorithmException e) {
        }
        return null;
    }

}
