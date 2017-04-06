package com.yfarich.mangasdownloader;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.yfarich.mangasdownloader.functions.WorkerFunction;
import com.yfarich.mangasdownloader.functions.downloadfunction.CloudFlareDownload;
import com.yfarich.mangasdownloader.functions.downloadfunction.DownloadFunction;
import com.yfarich.mangasdownloader.functions.downloadfunction.DownloadStrategy;
import com.yfarich.mangasdownloader.functions.downloadfunction.SynchronizedList;
import com.yfarich.mangasdownloader.shared.ConfigurationImplementation;
import com.yfarich.mangasdownloader.shared.RunningParameters;

import java.io.File;

/**
 * Created by FARICH on 01/04/2017.
 */
public class AppModule extends AbstractModule {

    private String propertiesFileName;
    private String configurationFileName;


    public AppModule withRunningParametersFile(String propertiesFile) {
        propertiesFileName = propertiesFile;
        return this;
    }

    public AppModule withApplicationConfigurationFile(String configFileName) {
        configurationFileName = configFileName;
        return this;
    }

    @Override
    protected void configure() {
        bind(SynchronizedList.class).in(Singleton.class);
        bind(DownloadStrategy.class).to(CloudFlareDownload.class);
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
