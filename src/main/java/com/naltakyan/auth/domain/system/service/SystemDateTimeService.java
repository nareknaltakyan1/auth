package com.naltakyan.auth.domain.system.service;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class SystemDateTimeService
{

	public LocalDateTime getCurrentDateTime()
	{
		return LocalDateTime.now();
	}
}
