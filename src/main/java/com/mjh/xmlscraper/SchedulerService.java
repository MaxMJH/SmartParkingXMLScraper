package com.mjh.xmlscraper;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Class which sets up the schedulers.
 * 
 * As there are 3 schedules which need to run at all times, 
 * each are specified in this class. The schedules each have
 * a delayed time so that they run when needed. 
 * 
 * @author MaxHarrisMJH@gmail.com
 */
public class SchedulerService {
	/*---- Properties ----*/
	/**
	 * Property which stores an instance of a ScheduledExecutorService so that threads can run. 
	 */
	private ScheduledExecutorService scheduler;
	
	/*---- Constructor ----*/
	/**
	 * Constructor that initialises all of the class' properties. 3 threads are kept in a pool
	 * so that only 3 can be run at a time.
	 */
	public SchedulerService() {
		this.scheduler = Executors.newScheduledThreadPool(3);
	}
	
	/*---- Methods ----*/
	/**
	 * Schedules a thread to run every 5 minutes after an initial 2 minute delay.
	 * This is usually used for the FiveMinutes section of the XML Scraper.
	 * 
	 * @param runnable A thread to be run.
	 */
	public void scheduleForFiveMinutes(Runnable runnable) {
		this.scheduler.scheduleAtFixedRate(runnable, 2, 5, TimeUnit.MINUTES);
	}
	
	/**
	 * Schedules a thread to run every 60 minutes after an initial 58 minute delay.
	 * This is usually used for the Hourly section of the XML Scraper.
	 * 
	 * @param runnable A thread to be run.
	 */
	public void scheduleForHour(Runnable runnable) {
		this.scheduler.scheduleAtFixedRate(runnable, 58, 60, TimeUnit.MINUTES);
	}
	
	/**
	 * Schedules a thread to run every 1439 minutes after an initial 1440 minute delay.
	 * This is usually used for the Daily section of the XML Scraper.
	 * 
	 * @param runnable A thread to be run.
	 */
	public void scheduleForDaily(Runnable runnable) {
		this.scheduler.scheduleAtFixedRate(runnable, 1439, 1440, TimeUnit.MINUTES);
	}
}
