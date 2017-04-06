package com.yfarich.mangasdownloader;

import com.google.common.base.Preconditions;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.yfarich.mangasdownloader.functions.WorkerFunction;
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
    private RunningParameters runningParameters;

    @Inject
    private Injector objectsMaker;

    @Inject
    private Class<? extends WorkerFunction> workerFunction;

    public static void main(final String[] args) {
        Preconditions.checkArgument(args.length > 0, "You need to specify a Running parameters File ");
        Injector injector = Guice.createInjector(new AppModule().withRunningParametersFile(args[0]));
        AppStarter application = injector.getInstance(AppStarter.class);
        application.startProcessing();
    }

    public void startProcessing() {

        try {
            List<MangaPage> urlsList = new URLsGenerator().generateUrlsFromExpression(getDownloadUrl());

            objectsMaker.getInstance(WorkersPoll.class)
                    .withNumberOfThreads(getNumberOfThreads())
                    .withDataToProcess(urlsList)
                    .withFunctionToApply(workerFunction)
                    .work();

        } catch (Exception e) {
            LOGGER.error("Exception " + e.getMessage());
            e.printStackTrace();
        }

    }

    private String getNumberOfThreads() {
        return runningParameters.getProperty(RunningParameters.NUMBER_OF_THREADS).get();
    }

    private String getDownloadUrl() {
        return runningParameters.getProperty(RunningParameters.DOWNLOAD_URL).get();
    }

}
