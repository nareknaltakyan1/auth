package com.naltakyan.auth.security;

public class HashGenerationException extends RuntimeException
{
	public HashGenerationException(final String msg, final Exception ex)
	{
		super(msg, ex);
	}
}
