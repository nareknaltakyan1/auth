package com.naltakyan.auth.domain.logintypes.service;

public class InvalidLoginFlow extends RuntimeException
{

	public InvalidLoginFlow()
	{
		super("Invalid Login Flow");
	}

}
