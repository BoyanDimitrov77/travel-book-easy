package com.travel.book.easy.travelbookeasy.services.impl;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import javax.mail.internet.InternetAddress;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.travel.book.easy.travelbookeasy.services.interfaces.MailService;

import it.ozimov.springboot.mail.model.Email;
import it.ozimov.springboot.mail.model.defaultimpl.DefaultEmail;
import it.ozimov.springboot.mail.service.EmailService;
import it.ozimov.springboot.mail.service.exception.CannotSendEmailException;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class MailServiceImpl implements MailService{

	
	private static final String CONFIRMATION_EMAIL_TEMPATE = "confirmation_email_template.ftl";

	private static final String RESET_PASSWORD_TEMPLATE = "reset_password_email_template.ftl";

	@Autowired
	public EmailService emailService;

	@Value("${spring.mail.username}")
	String flyEasyEmail;

	@Override
	public void sendEmailConfirmation(String userEmail, String url) throws CannotSendEmailException {
		Map<String, Object> model = new HashMap<>();
		model.put("url", url);

		sendEmailTempate(userEmail, "Account confirmation", model, CONFIRMATION_EMAIL_TEMPATE);

	}

	private void sendEmailTempate(String emailTo, String subject, Map<String, Object> model, String template)
			throws CannotSendEmailException {
		Email email = null;

		try {
			email = DefaultEmail.builder().from(new InternetAddress(flyEasyEmail, "FlyEasyApp"))
					.to(Lists.newArrayList(new InternetAddress(emailTo, ""))).subject(subject).body("")
					.encoding("UTF-8").build();
		} catch (UnsupportedEncodingException e) {
			log.error("UTF-8 NOT SUPPORTED!", e);
		}

		emailService.send(email, template, model);

	}

	@Override
	public void sendEmailResetPassord(String userEmail, String url) throws CannotSendEmailException {
		Map<String, Object> model = new HashMap<>();
		model.put("url", url);

		sendEmailTempate(userEmail, "Reset Password", model, RESET_PASSWORD_TEMPLATE);
	}
	
}
