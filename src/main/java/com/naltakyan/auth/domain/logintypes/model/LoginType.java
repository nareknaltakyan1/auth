package com.naltakyan.auth.domain.logintypes.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum LoginType
{
	EMAIL(false, 10), SMS(false, 10), TG_MESSAGE(false, 10), QUESTION(true, null), PIN(true, null);

	private final boolean isStatic;
	private final Integer expirationMinutes;
}
