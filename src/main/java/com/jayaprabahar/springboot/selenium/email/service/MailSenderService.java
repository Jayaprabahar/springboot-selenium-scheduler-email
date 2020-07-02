package com.jayaprabahar.springboot.selenium.email.service;

import javax.mail.internet.InternetAddress;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

/**
 * <p> Project : springboot-selenium-scheduler-email </p>
 * <p> Title : MailSenderService.java </p>
 * <p> Description: Send mail service</p>
 * <p> Created: Jul 1, 2020</p>
 * 
 * @version 1.0.0
 * @author <a href="mailto:jpofficial@gmail.com">Jayaprabahar</a>
 * 
 */
@Component
@Slf4j
public class MailSenderService {

	private JavaMailSender javaMailSender;

	/**
	 * 
	 */
	public MailSenderService(JavaMailSender javaMailSender) {
		this.javaMailSender = javaMailSender;
	}

	@Value("${email.fromAddress}")
	private String fromEmailAddress;

	@Value("${email.replyToAddress}")
	private String replyToEmailAddress;

	@Value("${email.toAddress}")
	private String toEmailAddress;

	/**
	 * This method sends email based on the information of CrewMessageVO object
	 * 
	 * @param crewMessageVO - Bean class contains parsed information from
	 *                      CrewMessage XML
	 */
	public void sendEmail(String content) {
		log.info("Preparing mail for the details");

		MimeMessagePreparator messagePreparator = mimeMessage -> {
			MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);

			messageHelper.setText(content, true);
			messageHelper.setSubject("Automated email:- " + content);

			messageHelper.setFrom(fromEmailAddress);
			messageHelper.setCc(fromEmailAddress);
			messageHelper.setReplyTo(fromEmailAddress);
			messageHelper.setTo(InternetAddress.parse(toEmailAddress));

		};

		try {
			javaMailSender.send(messagePreparator);
			log.info("Mail sent successfully ");
		} catch (MailException e) {
			log.error("Exception", e);
		}
	}
}
