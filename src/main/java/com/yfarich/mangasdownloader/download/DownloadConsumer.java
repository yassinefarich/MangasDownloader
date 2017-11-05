package com.yfarich.mangasdownloader.download;

import java.util.function.Consumer;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.yfarich.mangasdownloader.url.MangaPage;

/**
 * Created by FARICH on 25/03/2017.
 */
@Component
public class DownloadConsumer implements Consumer<MangaPage> {

	private static final Logger LOGGER = LogManager.getLogger(DownloadConsumer.class.getName());

	@Autowired
	private DownloadManager downloadManager;

	@Override
	public void accept(MangaPage t) {
		try {
			LOGGER.debug("Calling DowloadFunction class");
			downloadManager.download(t);
		} catch (Throwable throwable) {
			LOGGER.error(throwable.getMessage(), throwable);
		}

	}
}
