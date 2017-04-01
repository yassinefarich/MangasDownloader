
package com.yfarich.mangasdownloader.thread;

import java.util.concurrent.ConcurrentLinkedQueue;

import com.yfarich.mangasdownloader.functions.WorkerFunction;
import com.google.inject.Inject;
import com.google.inject.Injector;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import com.yfarich.mangasdownloader.url.MangaPage;

import com.google.common.base.Preconditions;

public class Worker extends Thread {

    private static final Logger LOGGER = LogManager.getLogger(Worker.class.getName());
    private WorkerFunction functionToApply;
    private ConcurrentLinkedQueue<MangaPage> dataQueue;
    private String threadName;

    @Inject
    Injector m_injector;

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
        this.functionToApply = m_injector.getInstance(functionToApply);
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