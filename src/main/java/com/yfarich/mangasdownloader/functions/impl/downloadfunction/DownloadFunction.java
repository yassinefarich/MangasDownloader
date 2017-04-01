package com.yfarich.mangasdownloader.functions.impl.downloadfunction;

import com.yfarich.mangasdownloader.functions.WorkerFunction;
import com.google.inject.Inject;
import com.yfarich.mangasdownloader.url.MangaPage;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * Created by FARICH on 25/03/2017.
 */
public class DownloadFunction implements WorkerFunction {

    private static final Logger LOGGER = LogManager.getLogger(DownloadFunction.class.getName());

    @Inject
    private DownloadManager downloadManager ;

    @Override
    public void apply(MangaPage data) {
        try {
            LOGGER.debug("Calling DowloadFunction class");
            downloadManager.download(data);
        } catch (Throwable throwable) {
            LOGGER.error(throwable.getMessage(), throwable);
        }
    }
}
