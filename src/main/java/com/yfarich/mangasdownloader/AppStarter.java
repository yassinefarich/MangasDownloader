package com.yfarich.mangasdownloader;

import com.google.common.base.Preconditions;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.yfarich.mangasdownloader.functions.impl.downloadfunction.DownloadFunction;
import com.yfarich.mangasdownloader.shared.ApplicationConfiguration;
import com.yfarich.mangasdownloader.shared.RunningParameters;
import com.yfarich.mangasdownloader.thread.WorkersPoll;
import com.yfarich.mangasdownloader.url.MangaPage;
import com.yfarich.mangasdownloader.url.generator.URLsGenerator;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.util.List;

public class AppStarter {

    private static final Logger LOGGER = LogManager.getLogger(AppStarter.class.getName());

    @Inject
    RunningParameters runningParameters;

    @Inject
    ApplicationConfiguration applicationConfiguration;

    @Inject
    Injector injector;

    public static void main(final String[] args) {

        Preconditions.checkArgument(args.length > 0, "You need to specify a Running parameters File ");
        Injector injector = Guice.createInjector(new AppModule().withRunningParametersFile(args[0]));
        AppStarter application = injector.getInstance(AppStarter.class);
        application.startProcessing();

        //  new AppStarter().startProcessing();
    }

    public void startProcessing() {

        try {
            List<MangaPage> urlsList = new URLsGenerator().generateUrlsFromExpression(getDownloadUrl());

            injector.getInstance(WorkersPoll.class)
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
        return applicationConfiguration.getProperty(ApplicationConfiguration.NUMBER_OF_THREADS).get();
    }

    private String getDownloadUrl() {
        return runningParameters.getProperty(RunningParameters.DOWNLOAD_URL).get();
    }

}
