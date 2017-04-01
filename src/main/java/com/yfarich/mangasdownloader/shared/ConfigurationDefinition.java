package com.yfarich.mangasdownloader.shared;

import com.google.common.base.Optional;

/**
 * Created by FARICH on 01/04/2017.
 */
public interface ConfigurationDefinition {

    public Optional<String> getProperty(String key);
}
