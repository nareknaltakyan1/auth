package com.naltakyan.auth.infrastructure.email.service;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService
{

	private final JavaMailSender mailSender;

	public void sendEmail(final String setTo, final String text, final String subject)
	{
		SimpleMailMessage message = new SimpleMailMessage();

		message.setFrom("nareknaltakyan1@gmail.com");
		message.setTo(setTo);
		message.setText(text);
		message.setSubject(subject);

		mailSender.send(message);
		System.out.println("Email done");

	}

}
