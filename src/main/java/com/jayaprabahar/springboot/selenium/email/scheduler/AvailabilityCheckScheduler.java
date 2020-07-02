package com.jayaprabahar.springboot.selenium.email.scheduler;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.jayaprabahar.springboot.selenium.email.check.ApplicationAvailabilityChecker;

import lombok.extern.slf4j.Slf4j;

/**
 * <p> Project : springboot-selenium-scheduler-email </p>
 * <p> Title : AvailabilityCheckScheduler.java </p>
 * <p> Description: AvailabilityCheckScheduler </p>
 * <p> Created: Jul 1, 2020</p>
 * 
 * @version 1.0.0
 * @author <a href="mailto:jpofficial@gmail.com">Jayaprabahar</a>
 * 
 */
@Component
@Slf4j
public class AvailabilityCheckScheduler {

	private ApplicationAvailabilityChecker jobService;

	/**
	 * 
	 */
	public AvailabilityCheckScheduler(ApplicationAvailabilityChecker jobService) {
		this.jobService = jobService;
	}

	@Scheduled(fixedRateString = "${scheduler.TimeInSeconds:300}000")
	public void execute() {
		log.info("-----------------Scheduler Started --------------");
		jobService.executeProcess();
		log.info("-----------------Scheduler Ended --------------");
	}

}
