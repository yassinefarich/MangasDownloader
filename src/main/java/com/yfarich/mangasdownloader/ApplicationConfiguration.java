package com.yfarich.mangasdownloader;

import java.util.function.Consumer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import com.yfarich.mangasdownloader.download.DownloadConsumer;
import com.yfarich.mangasdownloader.download.DownloadManagerStrategy;
import com.yfarich.mangasdownloader.download.downloadstrategy.CloudFlareDownload;
import com.yfarich.mangasdownloader.download.downloadstrategy.SimpleDownload;
import com.yfarich.mangasdownloader.shared.ApplicationParameters;
import com.yfarich.mangasdownloader.thread.Worker;
import com.yfarich.mangasdownloader.url.MangaPage;

@Configuration
@ComponentScan(basePackages = "com.yfarich")
public class ApplicationConfiguration {

	@Bean
	public ApplicationParameters applicationProperties() {
		return new ApplicationParameters(Main.getParametersFile());
	}

	@Bean
	public Class<? extends Consumer<MangaPage>> workerFunction() {
		return DownloadConsumer.class;

	}

	@Bean
	public Consumer<MangaPage> sleep() {
		return x -> {
			try {
				System.out.println("This is a sleep function >Start");
				Thread.sleep(1000);
				System.out.println("This is a sleep function >End");
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			;
		};
	}

	@Bean
	public DownloadManagerStrategy downloadManagerStrategy(ApplicationParameters parameters) {

		return parameters.getProperty(ApplicationParameters.DOWNLOAD_STRATEGY).get()
				.equals(ApplicationParameters.CLOUD_FLARE_STRATEGY) ? new CloudFlareDownload() : new SimpleDownload();

	}

	@Bean
	@Scope("prototype")
	public Worker worker() {
		return new Worker();
	}

}
