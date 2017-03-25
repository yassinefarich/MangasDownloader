package org.bitbucket.yassinefarich.mgdown;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.bitbucket.yassinefarich.mgdown.functions.impl.SleepFunction;
import org.bitbucket.yassinefarich.mgdown.shared.ApplicationProperties;
import org.bitbucket.yassinefarich.mgdown.thread.WorkersPoll;
import org.bitbucket.yassinefarich.mgdown.url.MangaPage;
import org.bitbucket.yassinefarich.mgdown.url.generator.URLsGenerator;

import java.util.List;

public class AppStarter {

    private final Logger LOGGER = LogManager.getLogger(AppStarter.class.getName());

    public static void main(final String[] args) {
        new AppStarter().startProcessing();
    }

    public void startProcessing() {

        try {
            List<MangaPage> urlsList = new URLsGenerator().generateUrlsFromExpression(getDownloadUrl());

            new WorkersPoll()
                    .withNumberOfThreads(getNumberOfThreads())
                    .withDataToProcess(urlsList)
                    .withFunctionToApply(SleepFunction.class)
                    .work();

        } catch (Exception e) {
            LOGGER.error("Exception " + e.getMessage());
            e.printStackTrace();
        }

    }

    private String getNumberOfThreads() {
        return ApplicationProperties.getInstance().getProperty(ApplicationProperties.NUMBER_OF_THREADS).get();
    }

    private String getDownloadUrl() {
        return ApplicationProperties.getInstance().getProperty(ApplicationProperties.DOWNLOAD_URL).get();
    }

}
