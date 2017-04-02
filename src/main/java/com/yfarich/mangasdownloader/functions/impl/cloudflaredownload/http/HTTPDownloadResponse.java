package com.yfarich.mangasdownloader.functions.impl.cloudflaredownload.http;

import java.io.InputStream;
import java.net.URL;

/**
 * Credits :
 *      The source code of this class has been adapted from https://github.com/Maximvdw/SpigotSite ,
 *      I am grateful to these developers :)
 */

public class HTTPDownloadResponse {
    private InputStream stream = null;
    private URL url = null;

    public HTTPDownloadResponse(InputStream stream, URL url) {
        setStream(stream);
        setUrl(url);
    }

    public InputStream getStream() {
        return stream;
    }

    public void setStream(InputStream stream) {
        this.stream = stream;
    }

    public URL getUrl() {
        return url;
    }

    public void setUrl(URL url) {
        this.url = url;
    }
}
