package com.yfarich.mangasdownloader.functions;

import com.yfarich.mangasdownloader.url.MangaPage;

/**
 * Created by farich on 24/03/17.
 */
public interface WorkerFunction {

    default void apply(MangaPage data) {
        System.out.println("## Default WorkerFunction / This class need to be derived ");
    }

}