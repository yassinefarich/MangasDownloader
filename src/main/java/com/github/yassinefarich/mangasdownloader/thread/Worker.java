
package com.github.yassinefarich.mangasdownloader.thread;

import java.util.concurrent.ConcurrentLinkedQueue;

import com.github.yassinefarich.mangasdownloader.functions.WorkerFunction;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import com.github.yassinefarich.mangasdownloader.url.MangaPage;

import com.google.common.base.Preconditions;

public class Worker extends Thread {

    private static final Logger LOGGER = LogManager.getLogger(Worker.class.getName());
    private WorkerFunction functionToApply;
    private ConcurrentLinkedQueue<MangaPage> dataQueue;
    private String threadName;

    public Worker() {
    }

    public Worker withDataQueue(ConcurrentLinkedQueue<MangaPage> dataQueue) {
        Preconditions.checkNotNull(dataQueue);
        this.dataQueue = dataQueue;
        return this;
    }

    public Worker withThreadName(String threadName) {
        Preconditions.checkNotNull(threadName);
        this.threadName = threadName;
        return this;

    }

    public Worker withFunctionToApply(Class<? extends WorkerFunction> functionToApply) {
        Preconditions.checkNotNull(functionToApply);
        try {
            this.functionToApply = functionToApply.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return this;
    }

    @Override
    public final void run() {

        try {

            while (!dataQueue.isEmpty()) {
                MangaPage data = dataQueue.poll();
                LOGGER.info("This is Thread " + threadName);

                try {
                    functionToApply.apply(data);

                } catch (Exception e) {
                    LOGGER.error(e.getMessage(), e);
                }

            }

        } catch (Exception e) {
            LOGGER.error("And error occured for Thread " + this.threadName + e.getMessage(), e);
        } finally {
            LOGGER.info("Thread " + this.threadName + " stopping");
        }
    }
}