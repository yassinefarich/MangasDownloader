package org.bitbucket.yassinefarich.mgdown.thread;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.bitbucket.yassinefarich.mgdown.functions.WorkerFunction;
import org.bitbucket.yassinefarich.mgdown.url.MangaPage;

import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.function.Function;

public class WorkersPoll {

    public static final int DEFAULT_THREADS_NUMBER = 30;

    private final Logger LOGGER = LogManager.getLogger(WorkersPoll.class.getName());

    private int threadsNumber = DEFAULT_THREADS_NUMBER;
    private List<MangaPage> processingData;
    private Class<? extends WorkerFunction> functionToApply;
    private ConcurrentLinkedQueue<MangaPage> dataQueue;

    public WorkersPoll() {
    }

    public WorkersPoll withNumberOfThreads(String stringMaxThreads) {
        try {
            this.threadsNumber = Integer.parseInt(stringMaxThreads);
        } catch (NumberFormatException numberFormatException) {
            LOGGER.error(numberFormatException.getMessage());
        }
        return this;
    }

    public WorkersPoll withDataToProcess(List<MangaPage> processingData) {
        this.processingData = processingData;
        return this;
    }

    public WorkersPoll withFunctionToApply(Class<? extends WorkerFunction> functionToApply) {
        this.functionToApply = functionToApply;
        return this;
    }

    public void work() throws InstantiationException, IllegalAccessException {
        dataQueue = new ConcurrentLinkedQueue<>(this.processingData);

        LOGGER.info("StartThreadProcessing ");
        for (int i = 0; i < threadsNumber; i++) {
            new Worker().withThreadName("#" + i).withDataQueue(dataQueue).withFunctionToApply(functionToApply).start();
        }

    }

}