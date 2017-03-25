/*
 * 
 */
package org.bitbucket.yassinefarich.mgdown.shared;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import com.google.common.base.Optional;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

/**
 * The Class ApplicationProperties.
 */
@Component
public class ApplicationProperties {

    /** The Constant DEFAULT_IMAGE_EXTENSION. */
    public static final String DEFAULT_IMAGE_EXTENSION = ".jpg";

    /** The Constant DOWNLOAD_PATH. */
    public static final String DOWNLOAD_PATH = "DOWNLOAD_PATH";

    /** The Constant DOWNLOAD_URL. */
    public static final String DOWNLOAD_URL = "DOWNLOAD_URL";

    /** The Constant DOWNLOAD_URL_MODIF. */
    public static final String DOWNLOAD_URL_MODIF = "DOWNLOAD_URL_MODIF";

    /** The Constant MAX_DOWNLOAD_ATTEMPTS. */
    public static final int MAX_DOWNLOAD_ATTEMPTS = 10;

    /** The Constant NUMBER_OF_THREADS. */
    public static final String NUMBER_OF_THREADS = "NUMBER_OF_THREADS";

    /** The Constant PARSER_XPATH. */
    public static final String PARSER_XPATH = "PARSER_XPATH";

    /** The Constant STRING_LEFT_PAD. */
    public static final int STRING_LEFT_PAD = 2;

    /** The Constant USE_SCRAPPING. */
    public static final String USE_SCRAPPING = "USE_SCRAPPING";
    public static final String DEFAULT_ENCODING = "UTF-8";
    /** The configuraion file. */
    private static final String CONFIGURAION_FILE = "config.properties";

    /** The logger. */
    private static final Logger LOGGER = LogManager
            .getLogger(Properties.class.getName());
    private static ApplicationProperties applicationProperties;
    /** The application properties. */
    private Properties properties = null;

    private ApplicationProperties()
    {

    }

    public static ApplicationProperties getInstance() {
        if (null == applicationProperties) applicationProperties = new ApplicationProperties();
        return applicationProperties;
    }

    /**
     * Gets the properties.
     *
     * @return the properties
     */
    public final Properties getProperties() {
        if (null == properties) {
            return initProperies();
        }
        return properties;
    }

    /**
     * Gets the property.
     *
     * @param propertieName
     *            the propertie name
     * @return the property
     */
    public final Optional<String> getProperty(final String propertieName) {
        return Optional.fromNullable(this.getProperties().getProperty(propertieName));
    }

    /**
     * Inits the properies.
     *
     * @return the properties
     */
    public final Properties initProperies() {
        properties = new Properties();

        InputStream propertiesFilesInputStream = null;

        try {
            LOGGER.info("Load Propertie from FILE " + CONFIGURAION_FILE);

            propertiesFilesInputStream = new FileInputStream(
                    CONFIGURAION_FILE);
            properties.load(propertiesFilesInputStream);
            IOUtils.closeQuietly(propertiesFilesInputStream);

        } catch (IOException ex) {
            LOGGER.error("can't load Propertie FILE " + ex.getMessage());

        } finally {
            IOUtils.closeQuietly(propertiesFilesInputStream);
        }

        return properties;
    }

}
