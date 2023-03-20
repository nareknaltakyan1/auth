package com.naltakyan.auth.domain.users.model;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum UserAccountState
{
	CREATED, ACTIVE, DEACTIVATED, LOCKED;

	public static final Set<UserAccountState> ACTIVE_STATES;

	static
	{
		ACTIVE_STATES = Stream.of(ACTIVE).collect(Collectors.toUnmodifiableSet());
	}
}
