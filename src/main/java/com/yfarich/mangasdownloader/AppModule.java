package com.yfarich.mangasdownloader;

import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.yfarich.mangasdownloader.functions.WorkerFunction;
import com.yfarich.mangasdownloader.functions.downloadfunction.*;
import com.yfarich.mangasdownloader.shared.ConfigurationImplementation;
import com.yfarich.mangasdownloader.shared.Constants;
import com.yfarich.mangasdownloader.shared.RunningParameters;

import java.io.File;

/**
 * Created by FARICH on 01/04/2017.
 */
public class AppModule extends AbstractModule {

    private String propertiesFileName;
    private String configurationFileName;
    private RunningParameters runningParameters;

    public AppModule withRunningParametersFile(String propertiesFile) {
        Preconditions.checkNotNull(propertiesFile);
        propertiesFileName = propertiesFile;

        File propertyFile = new File(propertiesFile);
        Preconditions.checkState(propertyFile.exists());

        initPropertyFile(propertyFile);
        return this;
    }

    private void initPropertyFile(File propertiesFileName)
    {
        runningParameters = new ConfigurationImplementation(propertiesFileName);
    }

    public AppModule withApplicationConfigurationFile(String configFileName) {
        configurationFileName = configFileName;
        return this;
    }

    @Override
    protected void configure() {
        bind(SynchronizedList.class).in(Singleton.class);
        bind(DownloadStrategy.class).to(retrieveDownloadStrategy());
    }

    private Class<? extends DownloadStrategy> retrieveDownloadStrategy() {
        Optional<String> downloadStrategy = runningParameters.getProperty(Constants.DOWNLOAD_STRATEGY);
        return !downloadStrategy.isPresent() || !Constants.CLOUD_FLARE_STRATEGY.equalsIgnoreCase(downloadStrategy.get()) ?
                SimpleDownload.class : CloudFlareDownload.class ;

    }

    @Provides
    @Singleton
    private RunningParameters applicationProperties() {
        return new ConfigurationImplementation(new File(propertiesFileName));
    }

    @Provides
    private Class<? extends WorkerFunction> workerFunction() {
        return DownloadFunction.class;

      /*  try {
            return (Class<? extends WorkerFunction>) Class.forName("DownloadFunction");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return Sleep.class;*/
    }
}
