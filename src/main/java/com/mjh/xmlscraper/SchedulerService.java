package com.mjh.xmlscraper;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class SchedulerService {
	private ScheduledExecutorService scheduler;
	
	public SchedulerService() {
		this.scheduler = Executors.newScheduledThreadPool(3);
	}
	
	public void scheduleForFiveMinutes(Runnable runnable) {
		this.scheduler.scheduleAtFixedRate(runnable, 2, 5, TimeUnit.MINUTES);
	}
	
	public void scheduleForHour(Runnable runnable) {
		this.scheduler.scheduleAtFixedRate(runnable, 58, 60, TimeUnit.MINUTES);
	}
	
	public void scheduleForDaily(Runnable runnable) {
		this.scheduler.scheduleAtFixedRate(runnable, 1439, 1440, TimeUnit.MINUTES);
	}
}
