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

public class ConfigurationImplementation implements ApplicationConfiguration, RunningParameters {

    private static final Logger LOGGER = LogManager.getLogger(ConfigurationImplementation.class.getName());

    private Properties properties;

    public ConfigurationImplementation(File propertiesFile) {
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
