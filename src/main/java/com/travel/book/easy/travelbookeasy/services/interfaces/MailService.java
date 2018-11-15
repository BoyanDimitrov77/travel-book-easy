package com.travel.book.easy.travelbookeasy.services.interfaces;

import it.ozimov.springboot.mail.service.exception.CannotSendEmailException;

public interface MailService {

	void sendEmailConfirmation(String userEmail, String url) throws CannotSendEmailException;

	void sendEmailResetPassord(String userEmail, String url) throws CannotSendEmailException;
}
