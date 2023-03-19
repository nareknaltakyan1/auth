package com.naltakyan.auth.security;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class PasswordBasedKeyPasswordEncoder implements PasswordEncoder
{

	@Override
	public String encode(final CharSequence rawPassword)
	{
		return HashUtils.createHash(rawPassword.toString());
	}

	@Override
	public boolean matches(final CharSequence rawPassword, final String encodedPassword)
	{
		return HashUtils.validatePassword(rawPassword.toString().toCharArray(), encodedPassword);
	}

}
