package com.yfarich.mangasdownloader;

import java.util.List;
import java.util.function.Consumer;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.yfarich.mangasdownloader.shared.ApplicationParameters;
import com.yfarich.mangasdownloader.thread.WorkersPoll;
import com.yfarich.mangasdownloader.url.MangaPage;
import com.yfarich.mangasdownloader.url.generator.URLsGenerator;

@Component
public class AppStarter {

	private static final Logger LOGGER = LogManager.getLogger(AppStarter.class.getName());

	@Autowired
	private ApplicationParameters applicationParameters;

	@Autowired
	private Class<? extends Consumer<MangaPage>> workerFunction;

	@Autowired
	private ApplicationContext appContext;

	public void startProcessing() {

		try {
			List<MangaPage> urlsList = new URLsGenerator().generateUrlsFromExpression(getDownloadUrl());

			appContext.getBean(WorkersPoll.class).withNumberOfThreads(getNumberOfThreads()).withDataToProcess(urlsList)
					.withFunctionToApply(workerFunction).work();

		} catch (Exception e) {
			LOGGER.error("Exception " + e.getMessage());
			e.printStackTrace();
		}

	}

	private String getNumberOfThreads() {
		return applicationParameters.getProperty(ApplicationParameters.NUMBER_OF_THREADS).get();
	}

	private String getDownloadUrl() {
		return applicationParameters.getProperty(ApplicationParameters.DOWNLOAD_URL).get();
	}

}
