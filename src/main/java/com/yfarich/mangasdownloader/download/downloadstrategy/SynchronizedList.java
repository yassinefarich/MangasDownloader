package com.yfarich.mangasdownloader.download.downloadstrategy;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.springframework.stereotype.Component;

/**
 * The Class SynchronizedList.
 */
@Component
public class SynchronizedList {

    /** The main synchronized list. */
    private List<String> mainSynchronizedList = Collections
            .synchronizedList(new LinkedList<String>());

    /**
     * Checks for been processed.
     *
     * @param md5Hash
     *            the md5 hash
     * @return true, if successful
     */
    public synchronized boolean hasBeenProcessed(final String md5Hash) {
        if (!mainSynchronizedList.contains(md5Hash)) {
            mainSynchronizedList.add(md5Hash);
            return false;
        }
        return true;
    }

}
