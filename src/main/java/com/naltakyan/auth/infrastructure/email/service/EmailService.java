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

	public void sendEmail()
	{
		SimpleMailMessage message = new SimpleMailMessage();

		message.setFrom("nareknaltakyan1@gmail.com");
		message.setTo("nareknaltakyan1@gmail.com");
		message.setText("nareknaltakyan1@gmail.com");
		message.setSubject("nareknaltakyan1@gmail.com");

		mailSender.send(message);
		System.out.println("Email done");

	}

}
