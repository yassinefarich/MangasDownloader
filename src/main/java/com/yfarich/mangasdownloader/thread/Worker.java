
package com.yfarich.mangasdownloader.thread;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.function.Consumer;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import com.google.common.base.Preconditions;
import com.yfarich.mangasdownloader.url.MangaPage;

public class Worker extends Thread {

	private static final Logger LOGGER = LogManager.getLogger(Worker.class.getName());
	private Consumer<MangaPage> functionToApply;
	private ConcurrentLinkedQueue<MangaPage> dataQueue;
	private String threadName;

	@Autowired
	private ApplicationContext appContext;

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

	public Worker withFunctionToApply(Class<? extends Consumer<MangaPage>> functionToApply) {
		Preconditions.checkNotNull(functionToApply);
		this.functionToApply = appContext.getBean(functionToApply);
		return this;
	}

	@Override
	public final void run() {

		try {

			while (!dataQueue.isEmpty()) {
				MangaPage data = dataQueue.poll();
				LOGGER.info("This is Thread " + threadName);

				try {
					functionToApply.accept(data);

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