/*
 * 
 */
package com.yfarich.mangasdownloader.shared;

import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.*;
import java.util.Properties;

public class ApplicationParameters {

	public static final String DOWNLOAD_PATH = "DOWNLOAD_PATH";
	public static final String DOWNLOAD_URL = "DOWNLOAD_URL";
	public static final String DOWNLOAD_URL_MODIF = "DOWNLOAD_URL_MODIF";
	public static final String NUMBER_OF_THREADS = "NUMBER_OF_THREADS";
	public static final String PARSER_XPATH = "PARSER_XPATH";
	public static final String USE_SCRAPPING = "USE_SCRAPPING";
	public static final String DOWNLOAD_STRATEGY = "DOWNLOAD_STRATEGY";
	public static final String CLOUD_FLARE_STRATEGY = "CLOUD_FLARE_STRATEGY";

	private static final Logger LOGGER = LogManager.getLogger(ApplicationParameters.class.getName());

	private Properties properties;

	public ApplicationParameters(File propertiesFile) {
		Preconditions.checkNotNull(propertiesFile);
		initProperties(propertiesFile);
	}

	public final Optional<String> getProperty(final String propertyName) {
		Preconditions.checkNotNull(propertyName);
		return Optional.fromNullable(properties.getProperty(propertyName));
	}

	private final Properties initProperties(File propertiesFile) {

		properties = new Properties();
		try (InputStream propertiesFilesInputStream = new FileInputStream(propertiesFile)) {
			LOGGER.info(new StringBuilder("Loading Properties from FILE ").append(propertiesFile.getName()).toString());
			properties.load(propertiesFilesInputStream);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return properties;
	}

}
