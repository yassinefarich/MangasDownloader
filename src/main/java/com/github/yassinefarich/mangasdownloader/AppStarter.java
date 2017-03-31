package com.github.yassinefarich.mangasdownloader;

import com.github.yassinefarich.mangasdownloader.functions.impl.downloadfunction.DownloadFunction;
import com.github.yassinefarich.mangasdownloader.shared.ApplicationProperties;
import com.github.yassinefarich.mangasdownloader.url.generator.URLsGenerator;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import com.github.yassinefarich.mangasdownloader.thread.WorkersPoll;
import com.github.yassinefarich.mangasdownloader.url.MangaPage;

import static com.github.yassinefarich.mangasdownloader.shared.Constants.*;

import java.util.List;

public class AppStarter {

    private static final Logger LOGGER = LogManager.getLogger(AppStarter.class.getName());

    public static void main(final String[] args) {
        new AppStarter().startProcessing();
    }

    public void startProcessing() {

        try {
            List<MangaPage> urlsList = new URLsGenerator().generateUrlsFromExpression(getDownloadUrl());

            new WorkersPoll()
                    .withNumberOfThreads(getNumberOfThreads())
                    .withDataToProcess(urlsList)
                    .withFunctionToApply(DownloadFunction.class)
                    .work();

        } catch (Exception e) {
            LOGGER.error("Exception " + e.getMessage());
            e.printStackTrace();
        }

    }

    private String getNumberOfThreads() {
        return ApplicationProperties.getInstance().getProperty(NUMBER_OF_THREADS).get();
    }

    private String getDownloadUrl() {
        return ApplicationProperties.getInstance().getProperty(DOWNLOAD_URL).get();
    }

}
