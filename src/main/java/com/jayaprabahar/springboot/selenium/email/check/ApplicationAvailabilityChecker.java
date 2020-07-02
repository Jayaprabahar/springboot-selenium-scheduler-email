/**
 * 
 */
package com.jayaprabahar.springboot.selenium.email.check;

import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.jayaprabahar.springboot.selenium.email.service.MailSenderService;

import io.github.bonigarcia.wdm.WebDriverManager;
import lombok.extern.slf4j.Slf4j;

/**
 * <p> Project : springboot-selenium-scheduler-email </p>
 * <p> Title : ApplicationAvailabilityChecker.java </p>
 * <p> Description: Checks the availability of bamboo and notifies the person via configured emails</p>
 * <p> Created: Jul 1, 2020</p>
 * 
 * @version 1.0.0
 * @author <a href="mailto:jpofficial@gmail.com">Jayaprabahar</a>
 * 
 */
@Component
public class ApplicationAvailabilityChecker {

	@Value("${application.url}")
	private String bambooUrl;

	@Value("#{new Integer('${selenium.loadTimeoutInSeconds}')}")
	public Integer loadTimeout;

	@Value("${selenium.headlessRun:true}")
	private boolean headlessRun;

	@Value("${email.sendMailWhenServerIsDown:true}")
	private boolean sendMailWhenServerIsDown;

	@Value("${email.sendMailWhenServerIsUp:true}")
	private boolean sendMailWhenServerIsUp;

	private MailSenderService mailSenderService;

	private WebDriver webDriver;

	/**
	 * 
	 */
	public ApplicationAvailabilityChecker(MailSenderService mailSenderService) {
		this.mailSenderService = mailSenderService;
	}

	/**
	 * Initializes the driver. Suitable for Springboot where chomedriver exe can't be placed inside the SpringBoot jar
	 */
	@PostConstruct
	private void initDriverPath() {
		WebDriverManager.chromedriver().setup();
	}

	/**
	 * Steps to be executed
	 */
	public void executeProcess() {
		invokeWebDriver();
		openLoginPage();
		triggerMail(isApplicationRunning());
		tearDown();
	}

	/**
	 * Invokes the bonigarcia webdriver
	 */
	private void invokeWebDriver() {
		ChromeOptions options = new ChromeOptions();

		if (headlessRun)
			options.addArguments("headless");

		webDriver = new ChromeDriver(options);
		webDriver.manage().deleteAllCookies();
		webDriver.manage().timeouts().pageLoadTimeout(loadTimeout, TimeUnit.SECONDS);
	}

	/**
	 * Webdriver opens the application
	 */
	private void openLoginPage() {
		webDriver.get(bambooUrl);
	}

	/**
	 * Checks whether the application is running or not.
	 * It varies according to your application
	 * 
	 * TODO - Logic varies according to the application for chich you want to check the avaialbility
	 * 
	 * Sample scenario i used here is to check the status of Bamboo
	 * 
	 * @return
	 */
	public boolean isApplicationRunning() {
		return !StringUtils.equalsIgnoreCase(webDriver.findElement(By.xpath("//*[@id=\"system-state-banner-info\"]")).getText(), "The server is pausing.");
	}

	/**
	 * Triggers email service based on the server status and flag for sending email
	 * 
	 * @param isApplicationRunning
	 */
	private void triggerMail(boolean isApplicationRunning) {
		if (isApplicationRunning && sendMailWhenServerIsUp) {
			mailSenderService.sendEmail(bambooUrl + " Server is UP. Stop the monitoring application");
		} else if (!isApplicationRunning && sendMailWhenServerIsDown) {
			mailSenderService.sendEmail(bambooUrl + " Server is STILL DOWN");
		}
	}

	/**
	 * Kill the webdriver
	 */
	public void tearDown() {
		if (webDriver != null) {
			webDriver.quit();
		}
	}

}
