package org.bitbucket.yassinefarich.mgdown.functions.impl;

import org.bitbucket.yassinefarich.mgdown.functions.WorkerFunction;
import org.bitbucket.yassinefarich.mgdown.url.MangaPage;

/**
 * Created by FARICH on 25/03/2017.
 */
public class SleepFunction implements WorkerFunction {
    @Override
    public void apply(MangaPage data) {

        try {
            System.out.println("This is a sleep function >Start");
            Thread.sleep(1000);
            System.out.println("This is a sleep function >End");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
