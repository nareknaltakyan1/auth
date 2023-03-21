package com.naltakyan.auth.infrastructure.verifier.service;

import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class VerificationCodeGenerator
{

	public String generateVerificationCode()
	{
		int codeLength = 6;
		return generateVerificationCode(codeLength);
	}

	private String generateVerificationCode(int length)
	{
		Random rand = new Random();
		StringBuilder code = new StringBuilder();
		for (int i = 0; i < length; i++)
		{
			code.append(rand.nextInt(10));
		}
		return code.toString();
	}
}
