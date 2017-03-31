package com.github.yassinefarich.mangasdownloader.functions.impl.downloadfunction;

import com.github.yassinefarich.mangasdownloader.functions.WorkerFunction;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import com.github.yassinefarich.mangasdownloader.url.MangaPage;

/**
 * Created by FARICH on 25/03/2017.
 */
public class DownloadFunction implements WorkerFunction {

    private static final Logger LOGGER = LogManager.getLogger(DownloadFunction.class.getName());
    private DownloadManager downloadManager = new DownloadManager();

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
