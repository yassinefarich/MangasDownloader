package org.bitbucket.yassinefarich.mgdown.functions;

import org.bitbucket.yassinefarich.mgdown.functions.impl.downloadfunction.DownloadFunction;
import org.bitbucket.yassinefarich.mgdown.url.MangaPage;

/**
 * Created by farich on 24/03/17.
 */
public interface WorkerFunction {

    default void apply(MangaPage data) {
        System.out.println("## Default WorkerFunction / This class need to be derived ");
    }

}
