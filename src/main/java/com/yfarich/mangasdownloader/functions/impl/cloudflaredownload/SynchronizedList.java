package com.yfarich.mangasdownloader.functions.impl.cloudflaredownload;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;


class SynchronizedList {

    private List<String> mainSynchronizedList = Collections
            .synchronizedList(new LinkedList<String>());

    synchronized boolean hasBeenProcessed(final String md5Hash) {
        if (!mainSynchronizedList.contains(md5Hash)) {
            mainSynchronizedList.add(md5Hash);
            return false;
        }
        return true;
    }

}
