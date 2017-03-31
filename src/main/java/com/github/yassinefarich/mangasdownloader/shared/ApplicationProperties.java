/*
 * 
 */
package com.github.yassinefarich.mangasdownloader.shared;

import com.google.common.base.Optional;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import static com.github.yassinefarich.mangasdownloader.shared.Constants.CONFIGURAION_FILE;

public class ApplicationProperties {

    private static final Logger LOGGER = LogManager.getLogger(Properties.class.getName());

    private static ApplicationProperties applicationProperties;

    private Properties properties = null;

    private ApplicationProperties() {
    }

    public static ApplicationProperties getInstance() {
        if (null == applicationProperties) applicationProperties = new ApplicationProperties();
        return applicationProperties;
    }

    public final Optional<String> getProperty(final String propertyName) {
        return Optional.fromNullable(getProperties().getProperty(propertyName));
    }


    private final Properties getProperties() {
        if (null == properties) {
            return initProperties();
        }
        return properties;
    }



    private final Properties initProperties() {

        properties = new Properties();
        try (InputStream propertiesFilesInputStream = new FileInputStream(CONFIGURAION_FILE)) {
            LOGGER.info(new StringBuilder("Loading Properties from FILE ").append(CONFIGURAION_FILE).toString());
            properties.load(propertiesFilesInputStream);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties;
    }

}
